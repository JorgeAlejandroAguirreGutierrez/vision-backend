package com.proyecto.vision.servicios.impl.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.repositorios.compra.INotaCreditoCompraRepository;
import com.proyecto.vision.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.vision.servicios.interf.compra.INotaCreditoCompraService;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotaCreditoCompraService implements INotaCreditoCompraService {
    @Autowired
    private INotaCreditoCompraRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IFacturaCompraService facturaCompraService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(NotaCreditoCompra notaCreditoCompra) {
        if(notaCreditoCompra.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaCreditoCompra.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaCreditoCompra.getNotaCreditoCompraLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_credito_compra_linea);
    }

    @Transactional
    @Override
    public NotaCreditoCompra crear(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito_compra);
        notaCreditoCompra.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_nota_credito_compra, notaCreditoCompra.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        notaCreditoCompra.setCodigo(codigo.get());

        notaCreditoCompra.setEstado(Constantes.estadoActivo);
        notaCreditoCompra.setEstadoInterno(Constantes.estadoInternoPorPagar);
        calcular(notaCreditoCompra);
        crearKardex(notaCreditoCompra);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        res.normalizar();
        return res;
    }

    private void crearKardex(NotaCreditoCompra notaCreditoCompra) {
        if(notaCreditoCompra.getEstado().equals(Constantes.estadoInactivo)) throw new DatoInvalidoException(Constantes.estado);
        if(notaCreditoCompra.getEstadoInterno().equals(Constantes.estadoInternoAnulada)) throw new DatoInvalidoException(Constantes.estado);
        List<NotaCreditoCompra> notasCreditoCompraAnt = rep.consultarPorFacturaCompraYEstadoInternoYEstado(notaCreditoCompra.getFacturaCompra().getId(), Constantes.estadoInternoPagada, Constantes.estadoActivo);
        List<Long> cantidadesDevueltas = new ArrayList();
        for(int i = 0; i < notaCreditoCompra.getNotaCreditoCompraLineas().size(); i++ ) {
            cantidadesDevueltas.add(Constantes.ceroId);
        }
        for(int i = 0; i < notasCreditoCompraAnt.size(); i++ ){
            for(int j = 0; j < notasCreditoCompraAnt.get(i).getNotaCreditoCompraLineas().size(); j++){
                cantidadesDevueltas.set(j, cantidadesDevueltas.get(j) + notasCreditoCompraAnt.get(i).getNotaCreditoCompraLineas().get(j).getCantidad());
            }
        }
        for(int i = 0; i<notaCreditoCompra.getNotaCreditoCompraLineas().size(); i++){
            if(notaCreditoCompra.getNotaCreditoCompraLineas().get(i).getCantidad() + cantidadesDevueltas.get(i) > notaCreditoCompra.getNotaCreditoCompraLineas().get(i).getCantidadCompra()){
                throw new DatoInvalidoException(Constantes.devolucion);
            }
        }
        kardexService.eliminar(9, 6, notaCreditoCompra.getSecuencial());
        if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_conjunta)){
            for(NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompraLinea.getProducto().getId());
                if (ultimoKardex != null) {
                    double saldo = ultimoKardex.getSaldo() - notaCreditoCompraLinea.getCantidad();
                    Kardex kardex = new Kardex(null, new Date(),
                            notaCreditoCompra.getSecuencial(), Constantes.cero, notaCreditoCompraLinea.getCantidad(), saldo,
                            Constantes.cero, notaCreditoCompraLinea.getTotalLinea(),
                            notaCreditoCompraLinea.getCostoUnitario(), notaCreditoCompraLinea.getTotalLinea(),
                            new TipoComprobante(9), new TipoOperacion(7), ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
        if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_devolucion)){
            for(NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompraLinea.getProducto().getId());
                if (ultimoKardex != null) {
                    double saldo = ultimoKardex.getSaldo() - notaCreditoCompraLinea.getCantidad();
                    Kardex kardex = new Kardex(null, new Date(),
                            notaCreditoCompra.getSecuencial(), Constantes.cero, notaCreditoCompraLinea.getCantidad(), saldo,
                            Constantes.cero, notaCreditoCompraLinea.getTotalLinea(),
                            ultimoKardex.getCostoPromedio(),notaCreditoCompraLinea.getTotalLinea(),
                            new TipoComprobante(9), new TipoOperacion(7), ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
        if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_descuento)){
            for(NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompraLinea.getProducto().getId());
                if(ultimoKardex != null){
                    Kardex kardex = new Kardex(null, new Date(),
                            notaCreditoCompra.getSecuencial(), Constantes.cero, ultimoKardex.getSalida(), ultimoKardex.getSaldo(),
                            Constantes.cero, notaCreditoCompraLinea.getTotalLinea(),
                            notaCreditoCompraLinea.getCostoUnitario(), notaCreditoCompraLinea.getTotalLinea(),
                            new TipoComprobante(9), new TipoOperacion(7), ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
    }

    @Override
    public NotaCreditoCompra actualizar(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        calcular(notaCreditoCompra);
        crearKardex(notaCreditoCompra);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        res.normalizar();
        return res;
    }

    private void actualizarKardex(NotaCreditoCompra notaCreditoCompra) {

    }

    private void actualizarPrecios(NotaCreditoCompra notaCreditoCompra) {

    }

    @Override
    public NotaCreditoCompra activar(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        notaCreditoCompra.setEstado(Constantes.estadoActivo);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoCompra inactivar(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        notaCreditoCompra.setEstado(Constantes.estadoInactivo);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoCompra obtener(long id) {
        Optional<NotaCreditoCompra> notaCreditoCompra = rep.findById(id);
        if(notaCreditoCompra.isPresent()) {
            NotaCreditoCompra res = notaCreditoCompra.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.factura_compra);
    }

    @Override
    public NotaCreditoCompra obtenerPorFacturaCompra(long facturaCompraId){
        NotaCreditoCompra notaCreditoCompra = new NotaCreditoCompra();
        FacturaCompra facturaCompra = facturaCompraService.obtener(facturaCompraId);
        notaCreditoCompra.setFacturaCompra(facturaCompra);
        notaCreditoCompra.setNotaCreditoCompraLineas(new ArrayList<>());
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
            NotaCreditoCompraLinea notaCreditoCompraLinea = new NotaCreditoCompraLinea();
            notaCreditoCompraLinea.setImpuesto(facturaCompraLinea.getImpuesto());
            notaCreditoCompraLinea.setProducto(facturaCompraLinea.getProducto());
            notaCreditoCompraLinea.setBodega(facturaCompraLinea.getBodega());
            notaCreditoCompraLinea.setCantidadCompra(facturaCompraLinea.getCantidad());

            double costoUnitarioCompra = facturaCompraLinea.getSubtotalConDescuentoLinea() / facturaCompraLinea.getCantidad();
            costoUnitarioCompra = Math.round(costoUnitarioCompra * 100.0) / 100.0;
            notaCreditoCompraLinea.setCostoUnitarioCompra(costoUnitarioCompra);
            notaCreditoCompraLinea.setCostoUnitario(costoUnitarioCompra);

            notaCreditoCompra.getNotaCreditoCompraLineas().add(notaCreditoCompraLinea);
        }
        return notaCreditoCompra;
    }

    @Override
    public List<NotaCreditoCompra> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<NotaCreditoCompra> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<NotaCreditoCompra> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<NotaCreditoCompra> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<NotaCreditoCompra> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    /*
     * CALCULOS CON NOTA CREDITO COMPRA LINEAS
     */
    @Override
    public void validarLinea(NotaCreditoCompraLinea notaCreditoCompraLinea) {
        if(notaCreditoCompraLinea.getCantidadCompra() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaCreditoCompraLinea.getCostoUnitarioCompra() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoCompraLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.devolucion);
        if(notaCreditoCompraLinea.getCostoUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoCompraLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(notaCreditoCompraLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaCreditoCompraLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaCreditoCompraLinea.getCantidad() > notaCreditoCompraLinea.getCantidadCompra()) throw new DatoInvalidoException(Constantes.devolucion);
    }

    @Override
    public NotaCreditoCompra calcular(NotaCreditoCompra notaCreditoCompra) {
        if(notaCreditoCompra.getOperacion() == Constantes.vacio) throw new DatoInvalidoException(Constantes.operacion_devolucion);

        double subtotal = Constantes.cero;
        double subtotalGrabado = Constantes.cero;
        double subtotalNoGrabado = Constantes.cero;
        double importeIvaTotal = Constantes.cero;
        for(NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
            validarLinea(notaCreditoCompraLinea);
            double subtotalLinea = Constantes.cero;
            if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_devolucion)){
                subtotalLinea = notaCreditoCompraLinea.getCantidad() * notaCreditoCompraLinea.getCostoUnitario();
                subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
                notaCreditoCompraLinea.setSubtotalLinea(subtotalLinea);

            }
            if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_descuento)) {
                if(notaCreditoCompra.getDescuento() <= Constantes.cero) throw new DatoInvalidoException(Constantes.operacion_descuento);

                double ponderacion = (notaCreditoCompraLinea.getCantidadCompra() * notaCreditoCompraLinea.getCostoUnitarioCompra()) / notaCreditoCompra.getFacturaCompra().getSubtotalConDescuento();
                subtotalLinea = (notaCreditoCompra.getDescuento() * ponderacion) * 100 / (100 + notaCreditoCompraLinea.getImpuesto().getPorcentaje());
                subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
                notaCreditoCompraLinea.setSubtotalLinea(subtotalLinea);

                double costoUnitario = subtotalLinea / notaCreditoCompraLinea.getCantidad();
                costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;
                notaCreditoCompraLinea.setCostoUnitario(costoUnitario);
            }
            subtotal += subtotalLinea;
            if (notaCreditoCompraLinea.getImpuesto().getPorcentaje() != Constantes.cero){
                subtotalGrabado += subtotalLinea;
            } else {
                subtotalNoGrabado += subtotalLinea;
            }

            double importeIvaLinea = subtotalLinea * notaCreditoCompraLinea.getImpuesto().getPorcentaje() / 100;
            importeIvaLinea = Math.round(importeIvaLinea * 100.0) / 100.0;
            notaCreditoCompraLinea.setImporteIvaLinea(importeIvaLinea);
            importeIvaTotal += importeIvaLinea;

            double totalLinea = subtotalLinea + importeIvaLinea;
            totalLinea = Math.round(totalLinea * 100.0) / 100.0;
            notaCreditoCompraLinea.setTotalLinea(totalLinea);
        }
        subtotal = Math.round(subtotal * 100.0) / 100.0;
        notaCreditoCompra.setSubtotal(subtotal);

        subtotalGrabado = Math.round(subtotalGrabado * 100.0) / 100.0;
        notaCreditoCompra.setSubtotalGrabado(subtotalGrabado);

        subtotalNoGrabado = Math.round(subtotalNoGrabado * 100.0) / 100.0;
        notaCreditoCompra.setSubtotalNoGrabado(subtotalNoGrabado);

        importeIvaTotal = Math.round(importeIvaTotal * 100.0) / 100.0;
        notaCreditoCompra.setImporteIvaTotal(importeIvaTotal);

        double valorTotal = subtotalGrabado + subtotalNoGrabado + importeIvaTotal;
        valorTotal = Math.round(valorTotal * 100.0) / 100.0;
        notaCreditoCompra.setValorTotal(valorTotal);

        return notaCreditoCompra;
    }
}
