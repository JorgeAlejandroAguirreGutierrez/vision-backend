package com.proyecto.vision.servicios.impl.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.modelos.venta.*;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.repositorios.venta.INotaDebitoVentaRepository;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.inventario.ITipoOperacionService;
import com.proyecto.vision.servicios.interf.venta.IFacturaService;
import com.proyecto.vision.servicios.interf.venta.INotaDebitoVentaService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private ITipoOperacionService tipoOperacionService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IFacturaService facturaService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(NotaDebitoVenta notaDebitoVenta) {
        if(notaDebitoVenta.getEstado().equals(Constantes.estadoInactivo)) throw new DatoInvalidoException(Constantes.estado);
        if(notaDebitoVenta.getEstadoInterno().equals(Constantes.estadoInternoAnulada)) throw new DatoInvalidoException(Constantes.estado);
        if(notaDebitoVenta.getEstadoSri().equals(Constantes.estadoSriAutorizada)) throw new DatoInvalidoException(Constantes.estado);
        if(notaDebitoVenta.getEstadoSri().equals(Constantes.estadoSriAnulada)) throw new DatoInvalidoException(Constantes.estado);
        if(notaDebitoVenta.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaDebitoVenta.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaDebitoVenta.getNotaDebitoVentaLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_debito_venta_linea);

        for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()){
            validarLinea(notaDebitoVentaLinea);
        }

        for(NotaDebitoVentaCheque cheque : notaDebitoVenta.getCheques()){
            if(cheque.getNumero().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero);
            if(cheque.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.dato_tipo);
            if(cheque.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(cheque.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(NotaDebitoVentaDeposito deposito : notaDebitoVenta.getDepositos()){
            if(deposito.getComprobante().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.comprobante);
            if(deposito.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(deposito.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
            if(deposito.getCuentaPropia().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cuenta_propia);
        }
        for(NotaDebitoVentaTransferencia transferencia : notaDebitoVenta.getTransferencias()){
            if(transferencia.getTipoTransaccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo_transaccion);
            if(transferencia.getNumeroTransaccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero_transaccion);
            if(transferencia.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(transferencia.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(NotaDebitoVentaTarjetaDebito tarjetaDebito : notaDebitoVenta.getTarjetasDebitos()){
            if(tarjetaDebito.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
            if(tarjetaDebito.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre_titular);
            if(tarjetaDebito.getLote().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.lote);
            if(tarjetaDebito.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(tarjetaDebito.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
            if(tarjetaDebito.getOperadorTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.operador_tarjeta);
            if(tarjetaDebito.getFranquiciaTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.franquicia_tarjeta);
        }
        for(NotaDebitoVentaTarjetaCredito tarjetaCredito : notaDebitoVenta.getTarjetasCreditos()){
            if(tarjetaCredito.getDiferido().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.diferido);
            if(tarjetaCredito.getTitular().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.titular);
            if(tarjetaCredito.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
            if(tarjetaCredito.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre_titular);
            if(tarjetaCredito.getLote().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.lote);
            if(tarjetaCredito.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(tarjetaCredito.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
            if(tarjetaCredito.getOperadorTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.operador_tarjeta);
            if(tarjetaCredito.getFranquiciaTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.franquicia_tarjeta);
        }
    }

    private Optional<String> crearClaveAcceso(NotaDebitoVenta notaDebitoVenta) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fechaEmision = dateFormat.format(notaDebitoVenta.getFecha());
        String tipoComprobante = Constantes.nota_de_debito_sri;
        String numeroRuc = notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
        String tipoAmbiente=Constantes.pruebas_sri;
        String serie = notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + notaDebitoVenta.getSesion().getUsuario().getEstacion().getCodigoSRI();
        String numeroComprobante = notaDebitoVenta.getSecuencial();
        String codigoNumerico = notaDebitoVenta.getCodigoNumerico();
        String tipoEmision = Constantes.emision_normal_sri;
        String cadenaVerificacion=fechaEmision+tipoComprobante+numeroRuc+tipoAmbiente+serie+numeroComprobante+codigoNumerico+tipoEmision;
        int[] arreglo=new int[cadenaVerificacion.length()];
        for(int i=0; i<cadenaVerificacion.length(); i++) {
            arreglo[i]= Integer.parseInt(cadenaVerificacion.charAt(i)+Constantes.vacio);
        }
        int factor=Constantes.dos;
        int suma=0;
        for(int i=arreglo.length-1; i>=0; i--) {
            suma=suma+arreglo[i]*factor;
            if(factor==Constantes.siete) {
                factor=Constantes.dos;
            } else {
                factor++;
            }
        }
        int digitoVerificador = Constantes.once - (suma % Constantes.once);
        if(digitoVerificador == Constantes.diez) {
            digitoVerificador = 1;
        }
        if(digitoVerificador == Constantes.once) {
            digitoVerificador = 0;
        }
        String claveAcceso=cadenaVerificacion+digitoVerificador;
        return Optional.of(claveAcceso);
    }

    @Transactional
    @Override
    public NotaDebitoVenta crear(NotaDebitoVenta notaDebitoVenta) {
        validar(notaDebitoVenta);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_debito_venta);
        notaDebitoVenta.setTipoComprobante(tipoComprobante);
        Optional<String> codigo = Util.generarCodigoPorEmpresa(Constantes.tabla_nota_debito_venta, notaDebitoVenta.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        notaDebitoVenta.setCodigo(codigo.get());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(notaDebitoVenta.getTipoComprobante().getId(),
                notaDebitoVenta.getSesion().getUsuario().getEstacion().getId(), notaDebitoVenta.getSesion().getEmpresa().getId(), Constantes.estadoActivo);
        notaDebitoVenta.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        notaDebitoVenta.setNumeroComprobante(notaDebitoVenta.getEstablecimiento() + Constantes.guion + notaDebitoVenta.getPuntoVenta() + Constantes.guion + notaDebitoVenta.getSecuencial());
        notaDebitoVenta.setCodigoNumerico(Util.generarCodigoNumerico(secuencial.getNumeroSiguiente()));
        Optional<String> claveAcceso = crearClaveAcceso(notaDebitoVenta);
        if (claveAcceso.isEmpty()) {
            throw new ClaveAccesoNoExistenteException();
        }
        notaDebitoVenta.setClaveAcceso(claveAcceso.get());
        notaDebitoVenta.setEstadoSri(Constantes.estadoSriPendiente);
        notaDebitoVenta.setEstadoInterno(Constantes.estadoInternoEmitida);
        notaDebitoVenta.setEstado(Constantes.estadoActivo);
        calcular(notaDebitoVenta);
        calcularRecaudacion(notaDebitoVenta);
        NotaDebitoVenta res = rep.save(notaDebitoVenta);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    @Override
    public NotaDebitoVenta actualizar(NotaDebitoVenta notaDebitoVenta) {
        validar(notaDebitoVenta);
        calcular(notaDebitoVenta);
        calcularRecaudacion(notaDebitoVenta);
        NotaDebitoVenta res = rep.save(notaDebitoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoVenta activar(NotaDebitoVenta notaDebitoVenta) {
        validar(notaDebitoVenta);
        notaDebitoVenta.setEstado(Constantes.estadoActivo);
        NotaDebitoVenta res = rep.save(notaDebitoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoVenta inactivar(NotaDebitoVenta notaDebitoVenta) {
        validar(notaDebitoVenta);
        notaDebitoVenta.setEstado(Constantes.estadoInactivo);
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
        return rep.consultar();
    }

    @Override
    public List<NotaDebitoVenta> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<NotaDebitoVenta> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<NotaDebitoVenta> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<NotaDebitoVenta> consultarPagina(Pageable pageable){
        return rep.findAll(pageable);
    }

    @Override
    public NotaDebitoVenta calcularRecaudacion(NotaDebitoVenta notaDebitoVenta){
        double total = Constantes.cero;
        total = total + notaDebitoVenta.getEfectivo();
        double totalCheques = Constantes.cero;
        for(NotaDebitoVentaCheque cheque: notaDebitoVenta.getCheques()) {
            totalCheques=totalCheques+cheque.getValor();
            total=total+totalCheques;
        }
        double totalDepositos = Constantes.cero;
        for(NotaDebitoVentaDeposito deposito: notaDebitoVenta.getDepositos()) {
            totalDepositos = totalDepositos + deposito.getValor();
            total = total + totalDepositos;
        }
        double totalTransferencias = Constantes.cero;
        for(NotaDebitoVentaTransferencia transferencia: notaDebitoVenta.getTransferencias()) {
            totalTransferencias = totalTransferencias+transferencia.getValor();
            total = total + totalTransferencias;
        }
        double totalTarjetasDebitos = Constantes.cero;
        for(NotaDebitoVentaTarjetaDebito tarjetaDebito: notaDebitoVenta.getTarjetasDebitos()) {
            totalTarjetasDebitos=totalTarjetasDebitos+tarjetaDebito.getValor();
            total=total+totalTarjetasDebitos;
        }
        double totalTarjetasCreditos = Constantes.cero;
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
        if(total >= notaDebitoVenta.getTotal()){
            double cambio = total - notaDebitoVenta.getTotal();
            cambio = Math.round(cambio*100.0)/100.0;
            notaDebitoVenta.setCambio(cambio);
        } else {
            notaDebitoVenta.setCambio(Constantes.cero);
        }
        if(total >= notaDebitoVenta.getTotal()){
            total = notaDebitoVenta.getTotal();
        }
        double porPagar = notaDebitoVenta.getTotal() - total;
        porPagar = Math.round(porPagar*100.0)/100.0;
        if(porPagar < 0) {
            porPagar = 0;
        }
        notaDebitoVenta.setPorPagar(porPagar);
        notaDebitoVenta.setTotalRecaudacion(total);
        return notaDebitoVenta;
    }

    /*
     * CALCULOS CON NOTA DEBITO VENTA LINEA
     */
    @Override
    public NotaDebitoVentaLinea calcularLinea(NotaDebitoVentaLinea notaDebitoVentaLinea) {
        validarLinea(notaDebitoVentaLinea);

        double valorPorcentajeDescuentoLinea = (notaDebitoVentaLinea.getPrecioUnitario() * notaDebitoVentaLinea.getPorcentajeDescuentoLinea() / 100);
        valorPorcentajeDescuentoLinea = Math.round(valorPorcentajeDescuentoLinea * 10000.0) / 10000.0;
        notaDebitoVentaLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);

        double subtotalLinea = (notaDebitoVentaLinea.getPrecioUnitario() - notaDebitoVentaLinea.getValorDescuentoLinea() - valorPorcentajeDescuentoLinea) * notaDebitoVentaLinea.getCantidad();
        subtotalLinea = Math.round(subtotalLinea * 10000.0) / 10000.0;
        notaDebitoVentaLinea.setSubtotalLinea(subtotalLinea);

        double valorIvaLinea = (subtotalLinea * (notaDebitoVentaLinea.getImpuesto().getPorcentaje() / 100));
        valorIvaLinea = Math.round(valorIvaLinea * 100.0) / 100.0;
        notaDebitoVentaLinea.setImporteIvaLinea(valorIvaLinea);

        double totalLinea = subtotalLinea + valorIvaLinea;
        totalLinea = Math.round(totalLinea * 100.0) / 100.0;
        notaDebitoVentaLinea.setTotalLinea(totalLinea);

        return notaDebitoVentaLinea;
    }
    /*
     * FIN CALCULO CON NOTA DEBITO VENTA LINEA
     */

    public NotaDebitoVenta calcular(NotaDebitoVenta notaDebitoVenta) {
        this.validar(notaDebitoVenta);
        for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()){
            calcularLinea(notaDebitoVentaLinea);
        }
        this.calcularSubtotal(notaDebitoVenta);
        this.calcularDescuento(notaDebitoVenta);
        this.calcularTotales(notaDebitoVenta);
        return notaDebitoVenta;
    }

    /*
     * CALCULOS TOTALES FACTURA DE VENTA
     */
    private void calcularSubtotal(NotaDebitoVenta notaDebitoVenta) {
        double subtotal = Constantes.cero;
        for(NotaDebitoVentaLinea notaDebitoVentaLinea : notaDebitoVenta.getNotaDebitoVentaLineas()){
            subtotal += notaDebitoVentaLinea.getSubtotalLinea();
        }
        subtotal = Math.round(subtotal * 10000.0) / 10000.0;
        notaDebitoVenta.setSubtotal(subtotal);
    }

    private void calcularDescuento(NotaDebitoVenta notaDebitoVenta) {
        double descuento = Constantes.cero;
        for (NotaDebitoVentaLinea notaDebitoVentaLinea : notaDebitoVenta.getNotaDebitoVentaLineas()) {
            descuento += notaDebitoVentaLinea.getValorDescuentoLinea() + notaDebitoVentaLinea.getPorcentajeDescuentoLinea();
        }
        descuento = Math.round(descuento * 100.0) / 100.0;
        notaDebitoVenta.setDescuento(descuento);
    }

    private void calcularTotales(NotaDebitoVenta notaDebitoVenta) {
        double subtotalGravado = Constantes.cero;
        double subtotalNoGravado = Constantes.cero;
        double importeIva = Constantes.cero;
        double total = Constantes.cero;
        for (NotaDebitoVentaLinea notaDebitoVentaLinea : notaDebitoVenta.getNotaDebitoVentaLineas()) {
            if (notaDebitoVentaLinea.getImpuesto().getPorcentaje() != Constantes.cero) {
                subtotalGravado += notaDebitoVentaLinea.getSubtotalLinea();
            } else {
                subtotalNoGravado += notaDebitoVentaLinea.getSubtotalLinea();
            }
            importeIva += notaDebitoVentaLinea.getImporteIvaLinea();
        }
        subtotalGravado = Math.round(subtotalGravado * 100.0) / 100.0;
        notaDebitoVenta.setSubtotalGravado(subtotalGravado);

        subtotalNoGravado = Math.round(subtotalNoGravado * 100.0) / 100.0;
        notaDebitoVenta.setSubtotalNoGravado(subtotalNoGravado);

        importeIva = Math.round(importeIva * 100.0) / 100.0;
        notaDebitoVenta.setImporteIva(importeIva);

        total = subtotalGravado + subtotalNoGravado + importeIva;
        total = Math.round(total * 100.0) / 100.0;
        notaDebitoVenta.setTotal(total);
    }

    /*
     * FIN CALCULOS TOTALES FACTURA VENTA
     */

    @Override
    public void validarLinea(NotaDebitoVentaLinea notaDebitoVentaLinea) {
        if(notaDebitoVentaLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(notaDebitoVentaLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaDebitoVentaLinea.getPrecio().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.precio);
        if(notaDebitoVentaLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaDebitoVentaLinea.getValorDescuentoLinea() > notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual()) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoVentaLinea.getValorDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoVentaLinea.getPorcentajeDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
        if(notaDebitoVentaLinea.getPorcentajeDescuentoLinea() > 100) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
    }

    @Override
    public NotaDebitoVenta obtenerPorFactura(long facturaId){
        NotaDebitoVenta notaDebitoVenta = new NotaDebitoVenta();
        Factura factura = facturaService.obtener(facturaId);
        notaDebitoVenta.setFactura(factura);
        notaDebitoVenta.normalizar();
        return notaDebitoVenta;
    }
}