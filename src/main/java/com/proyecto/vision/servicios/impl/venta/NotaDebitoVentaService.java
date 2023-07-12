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
            if(cheque.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
            if(cheque.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(NotaDebitoVentaDeposito deposito : notaDebitoVenta.getDepositos()){
            if(deposito.getComprobante().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.comprobante);
            if(deposito.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
            if(deposito.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
            if(deposito.getCuentaPropia().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cuenta_propia);
        }
        for(NotaDebitoVentaTransferencia transferencia : notaDebitoVenta.getTransferencias()){
            if(transferencia.getTipoTransaccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo_transaccion);
            if(transferencia.getNumeroTransaccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero_transaccion);
            if(transferencia.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
            if(transferencia.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(NotaDebitoVentaTarjetaDebito tarjetaDebito : notaDebitoVenta.getTarjetasDebitos()){
            if(tarjetaDebito.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
            if(tarjetaDebito.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre_titular);
            if(tarjetaDebito.getLote().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.lote);
            if(tarjetaDebito.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
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
            if(tarjetaCredito.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
            if(tarjetaCredito.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
            if(tarjetaCredito.getOperadorTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.operador_tarjeta);
            if(tarjetaCredito.getFranquiciaTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.franquicia_tarjeta);
        }
    }

    private void facturar(NotaDebitoVenta notaDebitoVenta) {
        kardexService.eliminar(5, 3, notaDebitoVenta.getSecuencial());
        for(NotaDebitoVentaLinea notaDebitoVentaLinea : notaDebitoVenta.getNotaDebitoVentaLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaDebitoVentaLinea.getBodega().getId(), notaDebitoVentaLinea.getProducto().getId());
            if(ultimoKardex == null){
                throw new DatoInvalidoException(Constantes.kardex);
            }
            if(ultimoKardex.getSaldo() < notaDebitoVentaLinea.getCantidad()){
                throw new DatoInvalidoException(Constantes.kardex);
            }
            double saldo = ultimoKardex.getSaldo() - notaDebitoVentaLinea.getCantidad();
            Kardex kardex = new Kardex(null, new Date(), notaDebitoVenta.getSecuencial(), Constantes.cero, notaDebitoVentaLinea.getCantidad(),
                    saldo, Constantes.cero, notaDebitoVentaLinea.getTotalSinDescuentoLinea(),
                    notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual(), notaDebitoVentaLinea.getTotalSinDescuentoLinea(),
                    new TipoComprobante(5), new TipoOperacion(6), notaDebitoVentaLinea.getBodega(), notaDebitoVentaLinea.getProducto());
            kardexService.crear(kardex);
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
        Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_nota_debito_venta, notaDebitoVenta.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        notaDebitoVenta.setCodigo(codigo.get());
        notaDebitoVenta.setSerie(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + notaDebitoVenta.getSesion().getUsuario().getEstacion().getCodigoSRI());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacion(notaDebitoVenta.getTipoComprobante().getId(), notaDebitoVenta.getSesion().getUsuario().getEstacion().getId());
        notaDebitoVenta.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
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
        facturar(notaDebitoVenta);
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
        facturar(notaDebitoVenta);
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
            double cambio = total - notaDebitoVenta.getTotalConDescuento();
            cambio = Math.round(cambio*100.0)/100.0;
            notaDebitoVenta.setCambio(cambio);
        } else {
            notaDebitoVenta.setCambio(Constantes.cero);
        }
        if(total >= notaDebitoVenta.getTotalConDescuento()){
            total = notaDebitoVenta.getTotalConDescuento();
        }
        double porPagar = notaDebitoVenta.getTotalConDescuento() - total;
        porPagar = Math.round(porPagar*100.0)/100.0;
        if(porPagar < 0) {
            porPagar = 0;
        }
        notaDebitoVenta.setPorPagar(porPagar);
        notaDebitoVenta.setTotalRecaudacion(total);
        return notaDebitoVenta;
    }

    @Override
    public NotaDebitoVenta calcular(NotaDebitoVenta notaDebitoVenta) {
        this.calcularTotalSinDescuentoLinea(notaDebitoVenta);
        this.calcularIvaSinDescuentoLinea(notaDebitoVenta);
        this.calcularSubtotalSinDescuento(notaDebitoVenta);
        this.calcularSubtotalBase12SinDescuento(notaDebitoVenta);
        this.calcularSubtotalBase0SinDescuento(notaDebitoVenta);
        this.calcularIvaSinDescuento(notaDebitoVenta);
        this.calcularDescuentoTotal(notaDebitoVenta);
        this.calcularTotalSinDescuento(notaDebitoVenta);
        this.calcularTotalConDescuento(notaDebitoVenta);
        return notaDebitoVenta;
    }
    /*
     * CALCULOS CON NOTA DEBITO VENTA LINEA
     */
    @Override
    public NotaDebitoVentaLinea calcularLinea(NotaDebitoVentaLinea notaDebitoVentaLinea) {
        validarLinea(notaDebitoVentaLinea);
        double impuesto = notaDebitoVentaLinea.getCantidad() * notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual() * notaDebitoVentaLinea.getImpuesto().getPorcentaje() / 100;
        double totalSinDescuentoLinea = notaDebitoVentaLinea.getCantidad() * notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual() + impuesto;
        totalSinDescuentoLinea = Math.round(totalSinDescuentoLinea*100.0)/100.0;
        notaDebitoVentaLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
        return notaDebitoVentaLinea;
    }
    private void calcularTotalSinDescuentoLinea(NotaDebitoVenta notaDebitoVenta) {
        for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
            validarLinea(notaDebitoVentaLinea);
            double totalSinDescuentoLinea = (notaDebitoVentaLinea.getCantidad()) * notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual();
            totalSinDescuentoLinea=Math.round(totalSinDescuentoLinea*100.0)/100.0;
            notaDebitoVentaLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
        }
    }
    private void calcularIvaSinDescuentoLinea(NotaDebitoVenta notaDebitoVenta) {
        for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
            validarLinea(notaDebitoVentaLinea);
            double ivaSinDescuentoLinea = notaDebitoVentaLinea.getTotalSinDescuentoLinea() * notaDebitoVentaLinea.getImpuesto().getPorcentaje() / 100;
            ivaSinDescuentoLinea = Math.round(ivaSinDescuentoLinea*100.0)/100.0;
            notaDebitoVentaLinea.setIvaSinDescuentoLinea(ivaSinDescuentoLinea);
        }
    }
    /*
     * FIN CALCULO CON NOTA DEBITO VENTA LINEA
     */

    /*
     * CALCULAR DESCUENTOS
     */
    private void calcularDescuentoTotal(NotaDebitoVenta notaDebitoVenta) {
        double totalDescuento = Constantes.cero;
        for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
            double valorDescuentoPorcentajeLinea = (notaDebitoVentaLinea.getTotalSinDescuentoLinea() * notaDebitoVentaLinea.getPorcentajeDescuentoLinea()) / 100;
            totalDescuento = totalDescuento + notaDebitoVentaLinea.getValorDescuentoLinea() + valorDescuentoPorcentajeLinea;
        }
        totalDescuento = Math.round(totalDescuento*100.0)/100.0;
        notaDebitoVenta.setDescuentoTotal(totalDescuento);
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
            subtotalSinDescuento += notaDebitoVentaLinea.getTotalSinDescuentoLinea();
        }
        subtotalSinDescuento = Math.round(subtotalSinDescuento * 100.0) / 100.0;
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

    private void calcularTotalConDescuento(NotaDebitoVenta notaDebitoVenta){
        double totalConDescuento = notaDebitoVenta.getSubtotalBase0SinDescuento() + notaDebitoVenta.getSubtotalBase12SinDescuento() + notaDebitoVenta.getIvaSinDescuento() - notaDebitoVenta.getDescuentoTotal();
        totalConDescuento = Math.round(totalConDescuento*100.0)/100.0;
        notaDebitoVenta.setTotalConDescuento(totalConDescuento);
    }

    @Override
    public void validarLinea(NotaDebitoVentaLinea notaDebitoVentaLinea) {
        if(notaDebitoVentaLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(notaDebitoVentaLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
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