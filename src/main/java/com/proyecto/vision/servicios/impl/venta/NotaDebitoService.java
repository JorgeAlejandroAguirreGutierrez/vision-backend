package com.proyecto.vision.servicios.impl.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.modelos.cliente.ClienteBase;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.venta.*;
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.repositorios.cliente.IClienteBaseRepository;
import com.proyecto.vision.repositorios.venta.INotaDebitoRepository;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.venta.IFacturaService;
import com.proyecto.vision.servicios.interf.venta.INotaDebitoService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class NotaDebitoService implements INotaDebitoService {
    @Autowired
    private INotaDebitoRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private ISecuencialService secuencialService;
    @Autowired
    private IClienteBaseRepository repClienteBase;
    @Autowired
    private IFacturaService facturaService;

    @Override
    public void validar(NotaDebito notaDebito) {
        if(notaDebito.getEstado().equals(Constantes.estadoAnulada)) throw new EstadoInvalidoException(Constantes.estadoAnulada);
        if(notaDebito.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)) throw new EstadoInvalidoException(Constantes.procesoSRIAutorizada);
        if(notaDebito.getProcesoSRI().equals(Constantes.procesoSRIAnulada)) throw new EstadoInvalidoException(Constantes.procesoSRIAnulada);
        if(notaDebito.getEmpresa().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
        if(notaDebito.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaDebito.getFactura().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.factura);
        if(notaDebito.getUsuario().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaDebito.getNotaDebitoLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_debito_linea);
        for(NotaDebitoLinea notaDebitoLinea: notaDebito.getNotaDebitoLineas()){
            validarLinea(notaDebitoLinea);
        }

        for(NDCheque cheque : notaDebito.getCheques()){
            if(cheque.getNumero().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero);
            if(cheque.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.dato_tipo);
            if(cheque.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(cheque.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(NDDeposito deposito : notaDebito.getDepositos()){
            if(deposito.getComprobante().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.comprobante);
            if(deposito.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(deposito.getCuentaPropia().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cuenta_propia);
        }
        for(NDTransferencia transferencia : notaDebito.getTransferencias()){
            if(transferencia.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo_transaccion);
            if(transferencia.getComprobante().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero_transaccion);
            if(transferencia.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(transferencia.getCuentaPropia().getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(NDTarjetaDebito tarjetaDebito : notaDebito.getTarjetasDebitos()){
            if(tarjetaDebito.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
            if(tarjetaDebito.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre_titular);
            if(tarjetaDebito.getLote().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.lote);
            if(tarjetaDebito.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(tarjetaDebito.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
            if(tarjetaDebito.getOperadorTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.operador_tarjeta);
            if(tarjetaDebito.getFranquiciaTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.franquicia_tarjeta);
        }
        for(NDTarjetaCredito tarjetaCredito : notaDebito.getTarjetasCreditos()){
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

    private Optional<String> crearClaveAcceso(NotaDebito notaDebito) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fechaEmision = dateFormat.format(notaDebito.getFecha());
        String tipoComprobante = Constantes.factura_sri;
        String numeroRuc = notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
        String tipoAmbiente = Constantes.pruebas_sri;
        String serie = notaDebito.getEstablecimiento() + notaDebito.getPuntoVenta();
        String numeroComprobante = notaDebito.getSecuencial();
        String codigoNumerico = notaDebito.getCodigoNumerico();
        String tipoEmision = Constantes.emision_normal_sri;
        String cadenaVerificacion = fechaEmision + tipoComprobante+numeroRuc + tipoAmbiente + serie + numeroComprobante + codigoNumerico + tipoEmision;
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

    @Override
    public NotaDebito crear(NotaDebito notaDebito) {
        validar(notaDebito);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura);
        notaDebito.setTipoComprobante(tipoComprobante);
        Optional<String>codigo = Util.generarCodigoPorEmpresa(Constantes.tabla_factura,notaDebito.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        notaDebito.setCodigo(codigo.get());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(notaDebito.getTipoComprobante().getId(),
                notaDebito.getUsuario().getEstacion().getId(), notaDebito.getEmpresa().getId(), Constantes.estadoActivo);
        notaDebito.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        notaDebito.setNumeroComprobante(notaDebito.getEstablecimiento() + Constantes.guion + notaDebito.getPuntoVenta() + Constantes.guion + notaDebito.getSecuencial());
        notaDebito.setCodigoNumerico(Util.generarCodigoNumerico(secuencial.getNumeroSiguiente()));
        Optional<String> claveAcceso = crearClaveAcceso(notaDebito);
        if (claveAcceso.isEmpty()) {
            throw new ClaveAccesoNoExistenteException();
        }
        notaDebito.setClaveAcceso(claveAcceso.get());
        notaDebito.setEstado(Constantes.estadoEmitida);
        notaDebito.setProcesoSRI(Constantes.procesoSRIPendiente);
        calcular(notaDebito);
        calcularRecaudacion(notaDebito);
        NotaDebito res = rep.save(notaDebito);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    @Override
    public NotaDebito actualizar(NotaDebito notaDebito) {
        validar(notaDebito);
        calcular(notaDebito);
        calcularRecaudacion(notaDebito);
        if(notaDebito.getTotalRecaudacion() != notaDebito.getTotal()){
            notaDebito.setEstado(Constantes.estadoEmitida);
        }
        if(notaDebito.getTotalRecaudacion() == notaDebito.getTotal()){
            notaDebito.setEstado(Constantes.estadoRecaudada);
        }
        NotaDebito res = rep.save(notaDebito);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebito anular(NotaDebito notaDebito) {
        validar(notaDebito);
        notaDebito.setEstado(Constantes.estadoAnulada);
        notaDebito.setProcesoSRI(Constantes.procesoSRIAnulada);
        NotaDebito res = rep.save(notaDebito);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebito obtener(long id) {
        Optional<NotaDebito> notaDebito = rep.findById(id);
        if(notaDebito.isPresent()) {
            NotaDebito res = notaDebito.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.nota_debito);
    }

    @Override
    public NotaDebito recaudar(NotaDebito notaDebito) {
        validar(notaDebito);
        calcular(notaDebito);
        calcularRecaudacion(notaDebito);
        if(notaDebito.getTotalRecaudacion() != notaDebito.getTotal()){
            notaDebito.setEstado(Constantes.estadoEmitida);
        }
        if(notaDebito.getTotalRecaudacion() == notaDebito.getTotal()){
            notaDebito.setEstado(Constantes.estadoRecaudada);
        }
        NotaDebito res = rep.save(notaDebito);
        res.normalizar();
        return res;
    }

    @Override
    public List<NotaDebito> consultar() {
        return rep.consultar();
    }

    @Override
    public List<NotaDebito> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<NotaDebito> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<NotaDebito> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public List<NotaDebito> consultarPorFacturaYEmpresaYEstadoDiferente(long facturaId, long empresaId, String estado){
        return rep.consultarPorFacturaYEmpresaYEstadoDiferente(facturaId, empresaId, estado);
    }

    @Override
    public Page<NotaDebito> consultarPagina(Pageable pageable){
        return rep.findAll(pageable);
    }

    /*
     * CALCULOS CON FACTURA VENTA LINEAS
     */

    @Override
    public void validarLinea(NotaDebitoLinea notaDebitoLinea) {
        if(notaDebitoLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(notaDebitoLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaDebitoLinea.getPrecio().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.precio);
        if(notaDebitoLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaDebitoLinea.getPrecioUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.precio);
        if(notaDebitoLinea.getValorDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoLinea.getValorDescuentoLinea() > (notaDebitoLinea.getCantidad() * notaDebitoLinea.getPrecioUnitario())) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoLinea.getPorcentajeDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
        if(notaDebitoLinea.getPorcentajeDescuentoLinea() > 100) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
    }
    @Override
    public NotaDebitoLinea calcularLinea(NotaDebitoLinea notaDebitoLinea) {
        validarLinea(notaDebitoLinea);

        double valorPorcentajeDescuentoLinea = (notaDebitoLinea.getPrecioUnitario() * notaDebitoLinea.getPorcentajeDescuentoLinea() / 100);
        valorPorcentajeDescuentoLinea = Math.round(valorPorcentajeDescuentoLinea * 10000.0) / 10000.0;
        notaDebitoLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);

        double subtotalLinea = (notaDebitoLinea.getPrecioUnitario() - notaDebitoLinea.getValorDescuentoLinea() - valorPorcentajeDescuentoLinea) * notaDebitoLinea.getCantidad();
        subtotalLinea = Math.round(subtotalLinea * 10000.0) / 10000.0;
        notaDebitoLinea.setSubtotalLinea(subtotalLinea);

        double importeIvaLinea = (subtotalLinea * (notaDebitoLinea.getImpuesto().getPorcentaje() / 100));
        importeIvaLinea = Math.round(importeIvaLinea * 100.0) / 100.0;
        notaDebitoLinea.setImporteIvaLinea(importeIvaLinea);

        double totalLinea = subtotalLinea + importeIvaLinea;
        totalLinea = Math.round(totalLinea * 100.0) / 100.0;
        notaDebitoLinea.setTotalLinea(totalLinea);

        return notaDebitoLinea;
    }

    public NotaDebito calcular(NotaDebito notaDebito) {
        this.validar(notaDebito);
        for(NotaDebitoLinea notaDebitoLinea: notaDebito.getNotaDebitoLineas()){
            calcularLinea(notaDebitoLinea);
        }
        this.calcularSubtotal(notaDebito);
        this.calcularDescuento(notaDebito);
        this.calcularTotales(notaDebito);
        return notaDebito;
    }

    /*
     * CALCULOS TOTALES FACTURA DE VENTA
     */
    private void calcularSubtotal(NotaDebito notaDebito) {
        double subtotal = Constantes.cero;
        for(NotaDebitoLinea notaDebitoLinea : notaDebito.getNotaDebitoLineas()){
            subtotal += notaDebitoLinea.getSubtotalLinea();
        }
        subtotal = Math.round(subtotal * 10000.0) / 10000.0;
        notaDebito.setSubtotal(subtotal);
    }

    private void calcularDescuento(NotaDebito notaDebito) {
        double descuento = Constantes.cero;
        for (NotaDebitoLinea notaDebitoLinea : notaDebito.getNotaDebitoLineas()) {
            descuento += notaDebitoLinea.getValorDescuentoLinea() + notaDebitoLinea.getValorPorcentajeDescuentoLinea();
        }
        descuento = Math.round(descuento * 100.0) / 100.0;
        notaDebito.setDescuento(descuento);
    }

    private void calcularTotales(NotaDebito notaDebito) {
        double subtotalGravado = Constantes.cero;
        double subtotalNoGravado = Constantes.cero;
        double importeIva = Constantes.cero;
        double total = Constantes.cero;
        for (NotaDebitoLinea notaDebitoLinea : notaDebito.getNotaDebitoLineas()) {
            if (notaDebitoLinea.getImpuesto().getPorcentaje() != Constantes.cero) {
                subtotalGravado += notaDebitoLinea.getSubtotalLinea();
            } else {
                subtotalNoGravado += notaDebitoLinea.getSubtotalLinea();
            }
            importeIva += notaDebitoLinea.getImporteIvaLinea();
        }
        subtotalGravado = Math.round(subtotalGravado * 100.0) / 100.0;
        notaDebito.setSubtotalGravado(subtotalGravado);

        subtotalNoGravado = Math.round(subtotalNoGravado * 100.0) / 100.0;
        notaDebito.setSubtotalNoGravado(subtotalNoGravado);

        importeIva = Math.round(importeIva * 100.0) / 100.0;
        notaDebito.setImporteIva(importeIva);

        total = subtotalGravado + subtotalNoGravado + importeIva;
        total = Math.round(total * 100.0) / 100.0;
        notaDebito.setTotal(total);
    }

    /*
     * FIN CALCULOS TOTALES FACTURA VENTA
     */
    @Override
    public String validarIdentificacion(String identificacion) {
        if (identificacion!= null) {
            if (identificacion.length() == 10 && Integer.parseInt((identificacion.substring(2,3))) != 6 && Integer.parseInt((identificacion.substring(2,3))) != 9) {
                boolean bandera = Util.verificarCedula(identificacion);
                if (bandera) {
                    Optional<ClienteBase> clienteBase = repClienteBase.obtenerPorIdentificacion(identificacion, Constantes.estadoActivo);
                    String nombre = Constantes.vacio;
                    if(clienteBase.isPresent()) {
                        nombre = clienteBase.get().getApellidos()+Constantes.espacio+clienteBase.get().getNombres();
                    }
                    return nombre;
                }
                throw new IdentificacionInvalidaException();
            } else if (identificacion.equals(Constantes.identificacion_consumidor_final)) {
                return "CONSUMIDOR FINAL";
            } else {
                throw new IdentificacionInvalidaException();
            }
        }
        throw new IdentificacionInvalidaException();
    }

    private NotaDebito calcularRecaudacion(NotaDebito notaDebito){
        double total = Constantes.cero;
        total = total + notaDebito.getEfectivo();

        double totalCheques = Constantes.cero;
        for(NDCheque cheque: notaDebito.getCheques()) {
            totalCheques = totalCheques + cheque.getValor();
            total = total + totalCheques;
        }

        double totalDepositos = Constantes.cero;
        for(NDDeposito deposito: notaDebito.getDepositos()) {
            totalDepositos = totalDepositos + deposito.getValor();
            total = total + totalDepositos;
        }

        double totalTransferencias = Constantes.cero;
        for(NDTransferencia transferencia: notaDebito.getTransferencias()) {
            totalTransferencias = totalTransferencias + transferencia.getValor();
            total = total + totalTransferencias;
        }

        double totalTarjetasDebitos = Constantes.cero;
        for(NDTarjetaDebito tarjetaDebito: notaDebito.getTarjetasDebitos()) {
            totalTarjetasDebitos = totalTarjetasDebitos + tarjetaDebito.getValor();
            total = total + totalTarjetasDebitos;
        }

        double totalTarjetasCreditos = Constantes.cero;
        for(NDTarjetaCredito tarjetaCredito: notaDebito.getTarjetasCreditos()) {
            totalTarjetasCreditos = totalTarjetasCreditos + tarjetaCredito.getValor();
            total = total + totalTarjetasCreditos;
        }

        total = total + notaDebito.getCredito().getSaldo();
        notaDebito.setTotalCredito(notaDebito.getCredito().getSaldo());
        notaDebito.setTotalCheques(totalCheques);
        notaDebito.setTotalDepositos(totalDepositos);
        notaDebito.setTotalTransferencias(totalTransferencias);
        notaDebito.setTotalTarjetasDebitos(totalTarjetasDebitos);
        notaDebito.setTotalTarjetasCreditos(totalTarjetasCreditos);

        if(total >= notaDebito.getTotal()){
            double cambio = total - notaDebito.getTotal();
            cambio = Math.round(cambio*100.0)/100.0;
            notaDebito.setCambio(cambio);
        } else {
            notaDebito.setCambio(Constantes.cero);
        }
        if(total >= notaDebito.getTotal()){
            total = notaDebito.getTotal();
        }
        double porPagar = notaDebito.getTotal() - total;
        porPagar = Math.round(porPagar*100.0)/100.0;
        if(porPagar < 0) {
            porPagar = 0;
        }
        notaDebito.setPorPagar(porPagar);
        notaDebito.setTotalRecaudacion(total);
        return notaDebito;
    }

    @Override
    public NotaDebito obtenerPorFactura(long facturaId){
        NotaDebito notaDebito = new NotaDebito();
        Factura factura = facturaService.obtener(facturaId);
        notaDebito.setFactura(factura);
        notaDebito.normalizar();
        return notaDebito;
    }
}