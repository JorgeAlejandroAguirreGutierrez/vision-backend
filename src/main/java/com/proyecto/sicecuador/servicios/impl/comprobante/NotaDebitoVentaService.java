package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.exception.SecuenciaNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.*;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.repositorios.comprobante.INotaDebitoVentaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaService;
import com.proyecto.sicecuador.servicios.interf.comprobante.INotaDebitoVentaService;
import com.proyecto.sicecuador.servicios.interf.comprobante.ITipoComprobanteService;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotaDebitoVentaService implements INotaDebitoVentaService {
    @Autowired
    private INotaDebitoVentaRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IFacturaService facturaService;

    @Override
    public void validar(NotaDebitoVenta notaDebitoVenta) {
        if(notaDebitoVenta.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaDebitoVenta.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaDebitoVenta.getNotaDebitoVentaLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_debito_venta_linea);

        if(notaDebitoVenta.getTotalRecaudacion() == Constantes.cero) throw new DatoInvalidoException(Constantes.total_recaudacion);
        if(notaDebitoVenta.getCredito().getSaldo() == Constantes.cero) notaDebitoVenta.setCredito(null);
    }

    private void facturar(NotaDebitoVenta notaDebitoVenta) {
        if(notaDebitoVenta.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        if(notaDebitoVenta.getEstado().equals(Constantes.estadoAnulada)) throw new DatoInvalidoException(Constantes.estado);

        for(NotaDebitoVentaLinea notaDebitoVentaLinea : notaDebitoVenta.getNotaDebitoVentaLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorFecha(notaDebitoVentaLinea.getBodega().getId(), notaDebitoVentaLinea.getProducto().getId());
            if(ultimoKardex == null){
                throw new DatoInvalidoException(Constantes.kardex);
            }
            if(ultimoKardex.getSaldo() < notaDebitoVentaLinea.getCantidad()){
                throw new DatoInvalidoException(Constantes.kardex);
            }
            double saldo = ultimoKardex.getSaldo() - notaDebitoVentaLinea.getCantidad();
            Kardex kardex = new Kardex(null, new Date(), Constantes.factura, Constantes.operacion_venta,
                    notaDebitoVenta.getSecuencia(), Constantes.cero, notaDebitoVentaLinea.getCantidad(), saldo,
                    Constantes.cero, notaDebitoVentaLinea.getTotalSinDescuentoLinea(),
                    notaDebitoVentaLinea.getCantidad(), notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual(), notaDebitoVentaLinea.getTotalSinDescuentoLinea(),
                    notaDebitoVentaLinea.getBodega(), notaDebitoVentaLinea.getProducto());
            kardexService.crear(kardex);
        }
    }

    @Transactional
    @Override
    public NotaDebitoVenta crear(NotaDebitoVenta notaDebitoVenta) {
        validar(notaDebitoVenta);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_debito_venta);
        notaDebitoVenta.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_nota_debito_venta);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        notaDebitoVenta.setCodigo(codigo.get());
    	Optional<String>secuencia=Util.generarSecuencia(Constantes.tabla_nota_debito_venta);
    	if (secuencia.isEmpty()) {
    		throw new SecuenciaNoExistenteException();
    	}
        notaDebitoVenta.setSecuencia(secuencia.get());
        double diferencia= notaDebitoVenta.getTotalConDescuento() - notaDebitoVenta.getTotalRecaudacion();
        if (diferencia>0){
            notaDebitoVenta.setTotalCredito(diferencia);
            notaDebitoVenta.getCredito().setSaldo(diferencia);
            notaDebitoVenta.setTotalRecaudacion(notaDebitoVenta.getTotalRecaudacion() + diferencia);
        }
        notaDebitoVenta.setEstado(Constantes.recaudada);
        facturar(notaDebitoVenta);
        notaDebitoVenta.setEstado(Constantes.estadoEmitida);
        NotaDebitoVenta res = rep.save(notaDebitoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoVenta actualizar(NotaDebitoVenta notaDebitoVenta) {
        validar(notaDebitoVenta);
        facturar(notaDebitoVenta);
        NotaDebitoVenta res = rep.save(notaDebitoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoVenta activar(NotaDebitoVenta notaDebitoVenta) {
        validar(notaDebitoVenta);
        notaDebitoVenta.setEstado(Constantes.activo);
        NotaDebitoVenta res = rep.save(notaDebitoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoVenta inactivar(NotaDebitoVenta notaDebitoVenta) {
        validar(notaDebitoVenta);
        notaDebitoVenta.setEstado(Constantes.inactivo);
        NotaDebitoVenta res = rep.save(notaDebitoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoVenta obtener(long id) {
        Optional<NotaDebitoVenta> notaDebitoVenta = rep.findById(id);
        if(notaDebitoVenta.isPresent()) {
            NotaDebitoVenta res = notaDebitoVenta.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.nota_debito_venta);
    }

    @Override
    public List<NotaDebitoVenta> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<NotaDebitoVenta> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<NotaDebitoVenta> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public NotaDebitoVenta calcularRecaudacion(NotaDebitoVenta notaDebitoVenta){
        double total = 0;
        total = total + notaDebitoVenta.getEfectivo();
        double totalCheques = 0;
        for(NotaDebitoVentaCheque cheque: notaDebitoVenta.getCheques()) {
            totalCheques=totalCheques+cheque.getValor();
            total=total+totalCheques;
        }
        double totalDepositos = 0;
        for(NotaDebitoVentaDeposito deposito: notaDebitoVenta.getDepositos()) {
            totalDepositos = totalDepositos + deposito.getValor();
            total = total + totalDepositos;
        }
        double totalTransferencias = 0;
        for(NotaDebitoVentaTransferencia transferencia: notaDebitoVenta.getTransferencias()) {
            totalTransferencias = totalTransferencias+transferencia.getValor();
            total = total + totalTransferencias;
        }
        double totalTarjetasDebitos = 0;
        for(NotaDebitoVentaTarjetaDebito tarjetaDebito: notaDebitoVenta.getTarjetasDebitos()) {
            totalTarjetasDebitos=totalTarjetasDebitos+tarjetaDebito.getValor();
            total=total+totalTarjetasDebitos;
        }
        double totalTarjetasCreditos = 0;
        for(NotaDebitoVentaTarjetaCredito tarjetaCredito: notaDebitoVenta.getTarjetasCreditos()) {
            totalTarjetasCreditos = totalTarjetasCreditos + tarjetaCredito.getValor();
            total = total + totalTarjetasCreditos;
        }
        total = total + notaDebitoVenta.getCredito().getSaldo();
        notaDebitoVenta.setTotalCredito(notaDebitoVenta.getCredito().getSaldo());
        notaDebitoVenta.setTotalCheques(totalCheques);
        notaDebitoVenta.setTotalDepositos(totalDepositos);
        notaDebitoVenta.setTotalTransferencias(totalTransferencias);
        notaDebitoVenta.setTotalTarjetasDebitos(totalTarjetasDebitos);
        notaDebitoVenta.setTotalTarjetasCreditos(totalTarjetasCreditos);
        if(total >= notaDebitoVenta.getTotalConDescuento()){
            notaDebitoVenta.setCambio(total - notaDebitoVenta.getTotalConDescuento());
        } else {
            notaDebitoVenta.setCambio(Constantes.cero);
        }
        if(total >= notaDebitoVenta.getTotalConDescuento()){
            total = notaDebitoVenta.getTotalConDescuento();
        }
        double porPagar = notaDebitoVenta.getTotalConDescuento() - total;
        porPagar = Math.round(porPagar*100.0)/100.0;
        if(porPagar<0) {
            porPagar=0;
        }
        notaDebitoVenta.setPorPagar(porPagar);
        notaDebitoVenta.setTotalRecaudacion(total);
        if(porPagar > 0){
            notaDebitoVenta.setEstado(Constantes.noRecaudada);
        } else{
            notaDebitoVenta.setEstado(Constantes.recaudada);
        }
        return notaDebitoVenta;
    }

    @Override
    public NotaDebitoVenta calcular(NotaDebitoVenta notaDebitoVenta) {
        this.calcularTotalSinDescuentoLinea(notaDebitoVenta);
        this.calcularTotaConDescuentoLinea(notaDebitoVenta);
        this.calcularDescuentoTotal(notaDebitoVenta);
        this.calcularSubtotalSinDescuento(notaDebitoVenta);
        this.calcularSubtotalBase12SinDescuento(notaDebitoVenta);
        this.calcularSubtotalBase0SinDescuento(notaDebitoVenta);
        this.calcularIvaSinDescuento(notaDebitoVenta);
        this.calcularDescuentoTotal(notaDebitoVenta);
        this.calcularTotalSinDescuento(notaDebitoVenta);
        return notaDebitoVenta;
    }

    @Override
    public NotaDebitoVentaLinea calcularLinea(NotaDebitoVentaLinea notaDebitoVentaLinea) {
        validarLinea(notaDebitoVentaLinea);
        double totalSinDescuentoLinea = notaDebitoVentaLinea.getCantidad() * notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual();
        notaDebitoVentaLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
        return notaDebitoVentaLinea;
    }
    /*
     * CALCULOS CON NOTA DEBITO VENTA LINEA
     */
    private void calcularTotalSinDescuentoLinea(NotaDebitoVenta notaDebitoVenta) {
    	for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
            validarLinea(notaDebitoVentaLinea);
    		double totalSinDescuentoLinea = (notaDebitoVentaLinea.getCantidad()) * notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual();
        	totalSinDescuentoLinea=Math.round(totalSinDescuentoLinea*100.0)/100.0;
            notaDebitoVentaLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
    	}
    }
    private void calcularTotaConDescuentoLinea(NotaDebitoVenta notaDebitoVenta) {
        for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
            validarLinea(notaDebitoVentaLinea);
            double valorPorcentajeDescuentoLinea = notaDebitoVentaLinea.getCantidad() * notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual() * notaDebitoVentaLinea.getPorcentajeDescuentoLinea() / 100;
            double totalConDescuentoLinea = (notaDebitoVentaLinea.getCantidad() * notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual()) -notaDebitoVentaLinea.getValorDescuentoLinea() - valorPorcentajeDescuentoLinea;
            totalConDescuentoLinea=Math.round(totalConDescuentoLinea*100.0)/100.0;
            notaDebitoVentaLinea.setTotalConDescuentoLinea(totalConDescuentoLinea);
        }
    }
    /*
     * FIN CALCULO CON NOTA DEBITO VENTA LINEA
     */
    
    /*
     * CALCULAR DESCUENTOS
     */
    private void calcularDescuentoTotal(NotaDebitoVenta notaDebitoVenta) {
        double totalValorDescuentoLinea = Constantes.cero;
        for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
            double valorDescuentoPorcentajeLinea = (notaDebitoVentaLinea.getTotalSinDescuentoLinea() * notaDebitoVentaLinea.getPorcentajeDescuentoLinea()) / 100;
            totalValorDescuentoLinea = notaDebitoVentaLinea.getValorDescuentoLinea() + valorDescuentoPorcentajeLinea;
        }
        double valorDescuentoTotalPorcentaje = (notaDebitoVenta.getPorcentajeDescuentoTotal() * notaDebitoVenta.getTotalSinDescuento()) / 100;
        double descuentoTotal = totalValorDescuentoLinea + notaDebitoVenta.getValorDescuentoTotal() + valorDescuentoTotalPorcentaje;
        descuentoTotal = Math.round(descuentoTotal*100.0)/100.0;
        notaDebitoVenta.setDescuentoTotal(descuentoTotal);
    }
    /*
     * FIN CALCULAR DESCUENTOS
     */
    
    /*
     * CALCULOS CON FACTURA
     */
    private void calcularSubtotalSinDescuento(NotaDebitoVenta notaDebitoVenta) {
    	double subtotalSinDescuento = Constantes.cero;
        for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()){
          subtotalSinDescuento+=notaDebitoVentaLinea.getTotalSinDescuentoLinea();
        }
        subtotalSinDescuento=Math.round(subtotalSinDescuento*100.0)/100.0;
        notaDebitoVenta.setSubtotalSinDescuento(subtotalSinDescuento);
    }
    
    private void calcularSubtotalBase12SinDescuento(NotaDebitoVenta notaDebitoVenta) {
    	double subtotalBase12SinDescuento = Constantes.cero;
    	for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()){
          if (notaDebitoVentaLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva12){
            subtotalBase12SinDescuento += notaDebitoVentaLinea.getTotalSinDescuentoLinea();
          }
    	}
        subtotalBase12SinDescuento= Math.round(subtotalBase12SinDescuento*100.0)/100.0;
        notaDebitoVenta.setSubtotalBase12SinDescuento(subtotalBase12SinDescuento);
    }
    
    private void calcularSubtotalBase0SinDescuento(NotaDebitoVenta notaDebitoVenta) {
    	double subtotalBase0SinDescuento = Constantes.cero;
    	for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()){
          if (notaDebitoVentaLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva0){
            subtotalBase0SinDescuento += notaDebitoVentaLinea.getTotalSinDescuentoLinea();
          }
        }
        subtotalBase0SinDescuento = Math.round(subtotalBase0SinDescuento*100.0)/100.0;
        notaDebitoVenta.setSubtotalBase0SinDescuento(subtotalBase0SinDescuento);
    }

    private void calcularIvaSinDescuento(NotaDebitoVenta notaDebitoVenta){
        double ivaSinDescuento=(notaDebitoVenta.getSubtotalBase12SinDescuento() * Constantes.iva12) / 100;
        ivaSinDescuento=Math.round(ivaSinDescuento*100.0)/100.0;
        notaDebitoVenta.setIvaSinDescuento(ivaSinDescuento);
    }

    private void calcularTotalSinDescuento(NotaDebitoVenta notaDebitoVenta){
        double totalSinDescuento = notaDebitoVenta.getSubtotalBase0SinDescuento() + notaDebitoVenta.getSubtotalBase12SinDescuento() + notaDebitoVenta.getIvaSinDescuento();
        totalSinDescuento=Math.round(totalSinDescuento*100.0)/100.0;
        notaDebitoVenta.setTotalSinDescuento(totalSinDescuento);
    }

    @Override
    public void validarLinea(NotaDebitoVentaLinea notaDebitoVentaLinea) {
        if(notaDebitoVentaLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaDebitoVentaLinea.getPrecio().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.precio);
        if(notaDebitoVentaLinea.getValorDescuentoLinea() > notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual()) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoVentaLinea.getValorDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoVentaLinea.getPorcentajeDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
        if(notaDebitoVentaLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaDebitoVentaLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaDebitoVentaLinea.getPorcentajeDescuentoLinea() > 100) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
    }

    @Override
    public NotaDebitoVenta obtenerPorFactura(long facturaId){
        NotaDebitoVenta notaDebitoVenta = new NotaDebitoVenta();
        Factura factura = facturaService.obtener(facturaId);
        notaDebitoVenta.setFactura(factura);
        return notaDebitoVenta;
    }
}
