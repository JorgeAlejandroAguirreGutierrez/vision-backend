package com.proyecto.vision.servicios.impl.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.EstadoInvalidoException;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.compra.*;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.repositorios.compra.INotaDebitoCompraRepository;
import com.proyecto.vision.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.vision.servicios.interf.compra.INotaDebitoCompraService;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import com.proyecto.vision.servicios.interf.inventario.ITipoOperacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotaDebitoCompraService implements INotaDebitoCompraService {
    @Autowired
    private INotaDebitoCompraRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private ITipoOperacionService tipoOperacionService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IFacturaCompraService facturaCompraService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(NotaDebitoCompra notaDebitoCompra) {
        if(notaDebitoCompra.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaDebitoCompra.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaDebitoCompra.getNotaDebitoCompraLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_debito_compra_linea);
    }

    private void facturar(NotaDebitoCompra notaDebitoCompra) {
        if(notaDebitoCompra.getProceso().equals(Constantes.procesoPagada)) throw new EstadoInvalidoException(Constantes.procesoPagada);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_debito_compra);
        TipoOperacion tipoOperacion = tipoOperacionService.obtenerPorAbreviaturaYEstado(Constantes.dev_compra, Constantes.estadoActivo);
        kardexService.eliminar(tipoComprobante.getId(), tipoOperacion.getId(), notaDebitoCompra.getSecuencial());
        for(NotaDebitoCompraLinea notaDebitoCompraLinea : notaDebitoCompra.getNotaDebitoCompraLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodega(notaDebitoCompraLinea.getProducto().getId(), notaDebitoCompraLinea.getBodega().getId());
            if (ultimoKardex != null) {
                double saldo = ultimoKardex.getSaldo() - notaDebitoCompraLinea.getCantidad();
                Kardex kardex = new Kardex(null, new Date(),
                        notaDebitoCompra.getSecuencial(), notaDebitoCompraLinea.getCantidad(), Constantes.cero, saldo,
                        notaDebitoCompraLinea.getTotalLinea(), Constantes.cero,
                        notaDebitoCompraLinea.getCostoUnitario(), notaDebitoCompraLinea.getTotalLinea(),
                        new TipoComprobante(tipoComprobante.getId()), tipoOperacion, ultimoKardex.getBodega(), ultimoKardex.getProducto());
                kardexService.crear(kardex);
            }
        }
    }

    @Transactional
    @Override
    public NotaDebitoCompra crear(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_debito_compra);
        notaDebitoCompra.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_nota_debito_compra, notaDebitoCompra.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        notaDebitoCompra.setCodigo(codigo.get());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(notaDebitoCompra.getTipoComprobante().getId(),
                notaDebitoCompra.getSesion().getUsuario().getEstacion().getId(), notaDebitoCompra.getSesion().getEmpresa().getId(), Constantes.estadoActivo);
        notaDebitoCompra.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        notaDebitoCompra.setProceso(Constantes.procesoPorPagar);
        calcular(notaDebitoCompra);
        facturar(notaDebitoCompra);
        NotaDebitoCompra res = rep.save(notaDebitoCompra);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    @Override
    public NotaDebitoCompra actualizar(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        calcular(notaDebitoCompra);
        facturar(notaDebitoCompra);
        NotaDebitoCompra res = rep.save(notaDebitoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoCompra anular(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        notaDebitoCompra.setProceso(Constantes.procesoAnulada);
        NotaDebitoCompra res = rep.save(notaDebitoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoCompra obtener(long id) {
        Optional<NotaDebitoCompra> notaDebitoCompra = rep.findById(id);
        if(notaDebitoCompra.isPresent()) {
            NotaDebitoCompra res = notaDebitoCompra.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.nota_debito_compra);
    }

    @Override
    public List<NotaDebitoCompra> consultar() {
        return rep.consultar();
    }

    @Override
    public List<NotaDebitoCompra> consultarPorProceso(String proceso){
        return rep.consultarPorProceso(proceso);
    }

    @Override
    public Page<NotaDebitoCompra> consultarPagina(Pageable pageable){
        return rep.findAll(pageable);
    }

    @Override
    public NotaDebitoCompra calcular(NotaDebitoCompra notaDebitoCompra) {
        this.calcularIvaLinea(notaDebitoCompra);
        this.calcularSubtotalLinea(notaDebitoCompra);
        this.calcularTotalLinea(notaDebitoCompra);
        this.calcularSubtotalGravado(notaDebitoCompra);
        this.calcularSubtotalNoGravado(notaDebitoCompra);
        this.calcularImporteIva(notaDebitoCompra);
        this.calcularTotal(notaDebitoCompra);
        return notaDebitoCompra;
    }
    /*
     * CALCULOS CON NOTA DEBITO COMPRA LINEA
     */
    @Override
    public NotaDebitoCompraLinea calcularLinea(NotaDebitoCompraLinea notaDebitoCompraLinea) {
        validarLinea(notaDebitoCompraLinea);
        double subtotalLinea = notaDebitoCompraLinea.getCantidad() * notaDebitoCompraLinea.getCostoUnitario();
        double impuesto = notaDebitoCompraLinea.getCantidad() * notaDebitoCompraLinea.getCostoUnitario() * notaDebitoCompraLinea.getImpuesto().getPorcentaje() / 100;
        double totalLinea = notaDebitoCompraLinea.getCantidad() * notaDebitoCompraLinea.getCostoUnitario() + impuesto - notaDebitoCompraLinea.getDescuento();
        subtotalLinea = Math.round(subtotalLinea * 100.0)/100.0;
        notaDebitoCompraLinea.setSubtotalLinea(subtotalLinea);
        totalLinea = Math.round(totalLinea * 100.0)/100.0;
        notaDebitoCompraLinea.setTotalLinea(totalLinea);
        return notaDebitoCompraLinea;
    }
    /*
     * CALCULOS CON NOTA DEBITO COMPRA LINEA
     */
    private void calcularIvaLinea(NotaDebitoCompra notaDebitoCompra) {
        for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()) {
            validarLinea(notaDebitoCompraLinea);
            double ivaLinea = notaDebitoCompraLinea.getCantidad() * notaDebitoCompraLinea.getCostoUnitario() * notaDebitoCompraLinea.getImpuesto().getPorcentaje() / 100;
            ivaLinea = Math.round(ivaLinea * 100.0)/100.0;
            notaDebitoCompraLinea.setIvaLinea(ivaLinea);
        }
    }
    private void calcularSubtotalLinea(NotaDebitoCompra notaDebitoCompra){
        for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()) {
            validarLinea(notaDebitoCompraLinea);
            double subtotalLinea = notaDebitoCompraLinea.getCantidad() * notaDebitoCompraLinea.getCostoUnitario();
            subtotalLinea = Math.round(subtotalLinea * 100.0)/100.0;
            notaDebitoCompraLinea.setTotalLinea(subtotalLinea);
        }
    }
    private void calcularTotalLinea(NotaDebitoCompra notaDebitoCompra) {
        for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()) {
            validarLinea(notaDebitoCompraLinea);
            double totalLinea = notaDebitoCompraLinea.getCantidad() * notaDebitoCompraLinea.getCostoUnitario() + notaDebitoCompraLinea.getIvaLinea() - notaDebitoCompraLinea.getDescuento();
            totalLinea=Math.round(totalLinea*100.0)/100.0;
            notaDebitoCompraLinea.setTotalLinea(totalLinea);
        }
    }
    /*
     * FIN CALCULO NOTA DEBITO COMPRA LINEAS
     */

    /*
     * CALCULOS CON NOTA DEBITO COMPRA
     */

    private void calcularSubtotalGravado(NotaDebitoCompra notaDebitoCompra) {
        double subtotalGravado = Constantes.cero;
        for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()){
            if (notaDebitoCompraLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva12){
                subtotalGravado += notaDebitoCompraLinea.getSubtotalLinea();
            }
        }
        subtotalGravado = Math.round(subtotalGravado*100.0)/100.0;
        notaDebitoCompra.setSubtotalGravado(subtotalGravado);
    }

    private void calcularSubtotalNoGravado(NotaDebitoCompra notaDebitoCompra) {
        double subtotalNoGravado = Constantes.cero;
        for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()){
            if (notaDebitoCompraLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva0){
                subtotalNoGravado += notaDebitoCompraLinea.getSubtotalLinea();
            }
        }
        subtotalNoGravado = Math.round(subtotalNoGravado*100.0)/100.0;
        notaDebitoCompra.setSubtotalNoGravado(subtotalNoGravado);
    }

    private void calcularImporteIva(NotaDebitoCompra notaDebitoCompra){
        double iva = (notaDebitoCompra.getSubtotalGravado() * Constantes.iva12) / 100;
        iva = Math.round(iva*100.0)/100.0;
        notaDebitoCompra.setImporteIva(iva);
    }

    private void calcularTotal(NotaDebitoCompra notaDebitoCompra){
        double total = notaDebitoCompra.getSubtotalGravado() + notaDebitoCompra.getSubtotalNoGravado() + notaDebitoCompra.getImporteIva() - notaDebitoCompra.getDescuento();
        total = Math.round(total*100.0)/100.0;
        notaDebitoCompra.setTotal(total);
    }

    @Override
    public void validarLinea(NotaDebitoCompraLinea notaDebitoCompraLinea) {
        if(notaDebitoCompraLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaDebitoCompraLinea.getCostoUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaDebitoCompraLinea.getDescuento() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoCompraLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaDebitoCompraLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
    }

    @Override
    public NotaDebitoCompra obtenerPorFacturaCompra(long facturaCompraId){
        NotaDebitoCompra notaDebitoCompra = new NotaDebitoCompra();
        FacturaCompra facturaCompra = facturaCompraService.obtener(facturaCompraId);
        notaDebitoCompra.setFacturaCompra(facturaCompra);
        return notaDebitoCompra;
    }

    @Override
    public List<NotaDebitoCompra> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<NotaDebitoCompra> consultarPorEmpresaYProceso(long empresaId, String proceso){
        return rep.consultarPorEmpresaYProceso(empresaId, proceso);
    }
}