package com.proyecto.vision.servicios.impl.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.cliente.*;
import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.entrega.GuiaRemision;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.venta.FacturaLinea;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.modelos.venta.NotaCredito;
import com.proyecto.vision.modelos.venta.NotaDebito;
import com.proyecto.vision.repositorios.cliente.IClienteBaseRepository;
import com.proyecto.vision.repositorios.venta.IFacturaRepository;
import com.proyecto.vision.servicios.interf.cliente.IClienteService;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.entrega.IGuiaRemisionService;
import com.proyecto.vision.servicios.interf.inventario.ITipoOperacionService;
import com.proyecto.vision.servicios.interf.venta.IFacturaService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import com.proyecto.vision.servicios.interf.venta.INotaCreditoService;
import com.proyecto.vision.servicios.interf.venta.INotaDebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService implements IFacturaService {
    @Autowired
    private IFacturaRepository rep;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private ITipoOperacionService tipoOperacionService;
    @Autowired
    private ISecuencialService secuencialService;
    @Autowired
    private IClienteBaseRepository repClienteBase;
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private INotaDebitoService notaDebitoService;
    @Autowired
    private INotaCreditoService notaCreditoService;
    @Autowired
    private IGuiaRemisionService guiaRemisionService;
    @Value("${facturacion.produccion}")
    private String facturacionProduccion;

    @Override
    public void validar(Factura factura) {
        if(factura.getTipoComprobante().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipo_comprobante);
        if(factura.getEstado().equals(Constantes.estadoAnulada)) throw new EstadoInvalidoException(Constantes.estadoAnulada);
        if(factura.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)) throw new EstadoInvalidoException(Constantes.procesoSRIAutorizada);
        if(factura.getProcesoSRI().equals(Constantes.procesoSRIAnulada)) throw new EstadoInvalidoException(Constantes.procesoSRIAnulada);
        if(factura.getEmpresa().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
        if(factura.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(factura.getCliente().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cliente);
        if(factura.getUsuario().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.usuario);
        if(factura.getFacturaLineas().isEmpty()) throw new DatoInvalidoException(Constantes.factura_detalle);
        Cliente consumidorFinal = clienteService.obtenerPorIdentificacionYEmpresaYEstado(Constantes.identificacion_consumidor_final, factura.getEmpresa().getId(), Constantes.estadoActivo);
        double valorTotal = Constantes.cero;
        for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
            validarLinea(facturaLinea);
            valorTotal += facturaLinea.getTotalLinea();
            if(factura.getCliente().getId() == consumidorFinal.getId() && valorTotal > Constantes.ciencuenta) throw new DatoInvalidoException(Constantes.consumidor_final);
        }
        for(Cheque cheque : factura.getCheques()){
            if(cheque.getNumero().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero);
            if(cheque.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.dato_tipo);
            if(cheque.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(cheque.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(Deposito deposito : factura.getDepositos()){
            if(deposito.getComprobante().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.comprobante);
            if(deposito.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(deposito.getCuentaPropia().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cuenta_propia);
        }
        for(Transferencia transferencia : factura.getTransferencias()){
            if(transferencia.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo_transaccion);
            if(transferencia.getComprobante().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero_transaccion);
            if(transferencia.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(transferencia.getCuentaPropia().getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(TarjetaDebito tarjetaDebito : factura.getTarjetasDebitos()){
            if(tarjetaDebito.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
            if(tarjetaDebito.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre_titular);
            if(tarjetaDebito.getLote().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.lote);
            if(tarjetaDebito.getValor() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valor);
            if(tarjetaDebito.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
            if(tarjetaDebito.getOperadorTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.operador_tarjeta);
            if(tarjetaDebito.getFranquiciaTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.franquicia_tarjeta);
        }
        for(TarjetaCredito tarjetaCredito : factura.getTarjetasCreditos()){
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

    @Override
    public Factura crear(Factura factura) {
        validar(factura);
        Optional<String>codigo = Util.generarCodigoPorEmpresa(factura.getFecha(), Constantes.tabla_factura,factura.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        factura.setCodigo(codigo.get());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(factura.getTipoComprobante().getId(),
                factura.getUsuario().getEstacion().getId(), factura.getEmpresa().getId(), Constantes.estadoActivo);
        factura.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        factura.setNumeroComprobante(factura.getEstablecimiento() + Constantes.guion + factura.getPuntoVenta() + Constantes.guion + factura.getSecuencial());
        factura.setCodigoNumerico(Util.generarCodigoNumerico(secuencial.getNumeroSiguiente()));
        Optional<String> claveAcceso = crearClaveAcceso(factura);
        if (claveAcceso.isEmpty()) {
            throw new ClaveAccesoNoExistenteException();
        }
        factura.setClaveAcceso(claveAcceso.get());
        factura.setEstado(Constantes.estadoEmitida);
        factura.setProcesoSRI(Constantes.procesoSRIPendiente);
        calcular(factura);
        calcularRecaudacion(factura);
        for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
            if(facturaLinea.getPrecio() != null && facturaLinea.getPrecio().getId() == Constantes.ceroId){
                facturaLinea.setPrecio(null);
            }
        }
        Factura res = rep.save(factura);
        crearKardex(factura);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }
    private Optional<String> crearClaveAcceso(Factura factura) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fechaEmision = dateFormat.format(factura.getFecha());
        String tipoComprobante = Constantes.factura_sri;
        String numeroRuc = factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
        String tipoAmbiente = Constantes.vacio;
        if(facturacionProduccion.equals(Constantes.si)){
            tipoAmbiente = Constantes.produccion_sri;
        }
        if(facturacionProduccion.equals(Constantes.no)){
            tipoAmbiente = Constantes.pruebas_sri;
        }
        String serie = factura.getEstablecimiento() + factura.getPuntoVenta();
        String numeroComprobante = factura.getSecuencial();
        String codigoNumerico = factura.getCodigoNumerico();
        String tipoEmision = Constantes.emision_normal_sri;
        String cadenaVerificacion = fechaEmision + tipoComprobante + numeroRuc + tipoAmbiente + serie + numeroComprobante + codigoNumerico + tipoEmision;
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
    private void crearKardex(Factura factura) {
        for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
            if(facturaLinea.getProducto().getCategoriaProducto().getDescripcion().equals(Constantes.bien)) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYFecha(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId(), factura.getFecha());
                double saldo, costoTotal, costoUnitario, costoPromedio;
                if (ultimoKardex != null) {
                    if (ultimoKardex.getSaldo() < facturaLinea.getCantidad()) {
                        throw new DatoInvalidoException(Constantes.kardex);
                    }
                    saldo = ultimoKardex.getSaldo() - facturaLinea.getCantidad();
                    costoTotal = ultimoKardex.getCostoTotal() - (facturaLinea.getCantidad() * ultimoKardex.getCostoPromedio());
                    costoUnitario = ultimoKardex.getCostoPromedio();
                    costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;
                    costoPromedio = costoTotal / saldo;
                    costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
                } else {
                    throw new DatoInvalidoException(Constantes.kardex);
                }
                TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura);
                TipoOperacion tipoOperacion = tipoOperacionService.obtenerPorAbreviaturaYEstado(Constantes.venta, Constantes.estadoActivo);
                Kardex kardex = new Kardex(null, factura.getFecha(), factura.getNumeroComprobante(),
                        facturaLinea.getId(), Constantes.cero, facturaLinea.getCantidad(), saldo, Constantes.cero,
                        costoUnitario, costoPromedio, costoTotal, Constantes.estadoActivo, tipoComprobante, tipoOperacion,
                        facturaLinea.getBodega(), facturaLinea.getProducto());

                kardexService.crear(kardex);
                kardexService.recalcularPorProductoYBodegaYFecha(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId(),factura.getFecha());
            }
        }
    }

    @Override
    public Factura actualizar(Factura factura) {
        validar(factura);
        List<NotaCredito> notasCreditos = notaCreditoService.consultarPorFacturaYEmpresaYEstadoDiferente(factura.getId(), factura.getEmpresa().getId(), Constantes.estadoAnulada);
        if(!notasCreditos.isEmpty()){
            throw new ErrorInternoException(Constantes.mensaje_error_nota_credito_existente);
        }
        List<GuiaRemision> guiasRemisiones = guiaRemisionService.consultarPorFacturaYEmpresaYEstadoDiferente(factura.getId(), factura.getEmpresa().getId(), Constantes.estadoAnulada);
        if(!guiasRemisiones.isEmpty()){
            throw new ErrorInternoException(Constantes.mensaje_error_guia_remision_existente);
        }
        calcular(factura);
        calcularRecaudacion(factura);
        if(factura.getTotalRecaudacion() != factura.getTotal()){
            factura.setEstado(Constantes.estadoEmitida);
        }
        if(factura.getTotalRecaudacion() == factura.getTotal()){
            factura.setEstado(Constantes.estadoRecaudada);
        }
        Factura res = rep.save(factura);
        factura = obtener(factura.getId());
        actualizarKardex(factura);
        eliminarKardex(factura);
        res.normalizar();
        return res;
    }
    private void actualizarKardex(Factura factura) {
        for (FacturaLinea facturaLinea : factura.getFacturaLineas()) {
            if(facturaLinea.getBodega() != null) {
                TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura);
                Kardex ultimoKardex = kardexService.obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYIdLinea(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId(), tipoComprobante.getId(), factura.getNumeroComprobante(), facturaLinea.getId());
                if (ultimoKardex == null) {
                    ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYFecha(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId(), factura.getFecha());
                    double saldo, costoTotal, costoUnitario, costoPromedio;
                    if (ultimoKardex != null) {
                        if (ultimoKardex.getSaldo() < facturaLinea.getCantidad()) {
                            throw new DatoInvalidoException(Constantes.kardex);
                        }
                        saldo = ultimoKardex.getSaldo() - facturaLinea.getCantidad();
                        costoTotal = ultimoKardex.getCostoTotal() - (facturaLinea.getCantidad() * ultimoKardex.getCostoPromedio());
                        costoUnitario = ultimoKardex.getCostoPromedio();
                        costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;
                        costoPromedio = costoTotal / saldo;
                        costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
                    } else {
                        throw new DatoInvalidoException(Constantes.kardex);
                    }
                    TipoOperacion tipoOperacion = tipoOperacionService.obtenerPorAbreviaturaYEstado(Constantes.venta, Constantes.estadoActivo);
                    Kardex kardex = new Kardex(null, factura.getFecha(), factura.getNumeroComprobante(),
                            facturaLinea.getId(), Constantes.cero, facturaLinea.getCantidad(), saldo, Constantes.cero,
                            costoUnitario, costoPromedio, costoTotal, Constantes.estadoActivo, tipoComprobante, tipoOperacion,
                            facturaLinea.getBodega(), facturaLinea.getProducto());
                    kardexService.crear(kardex);
                } else {
                    Kardex penultimoKardex = kardexService.obtenerPenultimoPorProductoYBodegaYMismaFechaYId(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId(), factura.getFecha(), ultimoKardex.getId());
                    if (penultimoKardex == null) {
                        penultimoKardex = kardexService.obtenerPenultimoPorProductoYBodegaYMenorFecha(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId(), factura.getFecha());
                    }
                    double saldo, costoTotal, costoUnitario, costoPromedio;

                    saldo = penultimoKardex.getSaldo() - facturaLinea.getCantidad();
                    costoUnitario = penultimoKardex.getCostoPromedio();
                    costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;
                    costoTotal = penultimoKardex.getCostoTotal() - (costoUnitario * facturaLinea.getCantidad());
                    costoTotal = Math.round(costoTotal * 100.0) / 100.0;
                    costoPromedio = costoTotal / saldo;
                    costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;

                    ultimoKardex.setFecha(factura.getFecha());
                    ultimoKardex.setSalida(facturaLinea.getCantidad());
                    ultimoKardex.setSaldo(saldo);
                    ultimoKardex.setHaber(costoUnitario);
                    ultimoKardex.setCostoPromedio(costoPromedio);
                    ultimoKardex.setCostoTotal(costoTotal);
                    kardexService.actualizar(ultimoKardex);
                }
                Calendar c = Calendar.getInstance();
                c.setTime(factura.getFecha());
                c.add(c.DAY_OF_YEAR, -1);
                kardexService.recalcularPorProductoYBodegaYFecha(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId(), c.getTime());
            }
        }
    }

    private void eliminarKardex(Factura factura) {
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura);
        List<Kardex> kardexs = kardexService.consultarPorTipoComprobanteYReferencia(tipoComprobante.getId(), factura.getNumeroComprobante());
        if (kardexs.isEmpty()) {
            return;
        }
        for (Kardex kardex : kardexs) {
            Boolean lineaEncontrada = false;
            for (FacturaLinea facturaLinea : factura.getFacturaLineas()) {
                if (kardex.getIdLinea() == facturaLinea.getId()) {
                    lineaEncontrada = true;
                }
            }
            if (!lineaEncontrada) {
                long productoEliminadoId = kardex.getProducto().getId();
                kardex.setProducto(null);
                kardexService.actualizar(kardex);
                Calendar c = Calendar.getInstance();
                c.setTime(factura.getFecha());
                c.add(c.DAY_OF_YEAR, -1);
                kardexService.recalcularPorProductoYBodegaYFecha(productoEliminadoId, kardex.getBodega().getId(), c.getTime());
            }
        }
    }

    @Override
    public Factura anular(Factura factura) {
        if(factura.getEstado().equals(Constantes.estadoCerrada)){
            throw new ErrorInternoException(Constantes.mensaje_error_factura_cerrada);
        }
        List<NotaDebito> notasDebitos = notaDebitoService.consultarPorFacturaYEmpresaYEstadoDiferente(factura.getId(), factura.getEmpresa().getId(), Constantes.estadoAnulada);
        if(!notasDebitos.isEmpty()){
            throw new ErrorInternoException(Constantes.mensaje_error_nota_debito_existente);
        }
        List<NotaCredito> notasCreditos = notaCreditoService.consultarPorFacturaYEmpresaYEstadoDiferente(factura.getId(), factura.getEmpresa().getId(), Constantes.estadoAnulada);
        if(!notasCreditos.isEmpty()){
            throw new ErrorInternoException(Constantes.mensaje_error_nota_credito_existente);
        }
        List<GuiaRemision> guiasRemisiones = guiaRemisionService.consultarPorFacturaYEmpresaYEstadoDiferente(factura.getId(), factura.getEmpresa().getId(), Constantes.estadoAnulada);
        if(!guiasRemisiones.isEmpty()){
            throw new ErrorInternoException(Constantes.mensaje_error_guia_remision_existente);
        }
        factura.setEstado(Constantes.estadoAnulada);
        factura.setProcesoSRI(Constantes.procesoSRIAnulada);
        Factura res = rep.save(factura);
        anularKardex(factura);
        res.normalizar();
        return res;
    }

    private void anularKardex(Factura factura) {
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura);
        Calendar c = Calendar.getInstance();
        c.setTime(factura.getFecha());
        c.add(c.DAY_OF_YEAR, -1);
        for (FacturaLinea facturaLinea : factura.getFacturaLineas()) {
            Kardex kardex = kardexService.obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYIdLinea(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId(), tipoComprobante.getId(), factura.getNumeroComprobante(), facturaLinea.getId());
            kardex.setEstado(Constantes.estadoAnulada);
            kardexService.actualizar(kardex);
            kardexService.recalcularPorProductoYBodegaYFecha(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId(), c.getTime());
        }
    }

    @Override
    public Factura obtener(long id) {
        Optional<Factura> factura = rep.findById(id);
        if(factura.isPresent()) {
            Factura res = factura.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.factura);
    }

    @Override
    public Factura recaudar(Factura factura) {
        validar(factura);
        calcular(factura);
        calcularRecaudacion(factura);
        if(factura.getTotalRecaudacion() != factura.getTotal()){
            factura.setEstado(Constantes.estadoEmitida);
        }
        if(factura.getTotalRecaudacion() == factura.getTotal()){
            factura.setEstado(Constantes.estadoRecaudada);
        }
        Factura res = rep.save(factura);
        res.normalizar();
        return res;
    }

    @Override
    public List<Factura> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Factura> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Factura> consultarPorProcesoSRI(String procesoSRI){
        return rep.consultarPorProcesoSRI(procesoSRI);
    }

    @Override
    public List<Factura> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Factura> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public List<Factura> consultarPorCliente(long facturaId) {
        return rep.consultarPorCliente(facturaId);
    }

    @Override
    public List<Factura> consultarPorClienteYEmpresaYEstado(long empresaId, long facturaId, String estado) {
        return rep.consultarPorClienteYEmpresaYEstado(empresaId, facturaId, estado);
    }

    @Override
    public List<Factura> consultarPorClienteYEstado(long facturaId, String estado) {
        return rep.consultarPorClienteYEstado(facturaId, estado);
    }

    @Override
    public List<Factura> consultarPorFechaYEmpresaYEstadoEmitidaYEstadoRecaudada(Date fecha, long facturaId, String estadoEmitida, String estadoRecaudada){
        return rep.consultarPorFechaYEmpresaYEstadoEmitidaYEstadoRecaudada(fecha, facturaId, estadoEmitida, estadoRecaudada);
    }

    @Override
    public Page<Factura> consultarPagina(Pageable pageable){
        return rep.findAll(pageable);
    }

    /*
     * CALCULOS CON FACTURA VENTA LINEAS
     */

    @Override
    public void validarLinea(FacturaLinea facturaLinea) {
        if(facturaLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(facturaLinea.getProducto().getCategoriaProducto().getAbreviatura().equals(Constantes.abreviatura_bien)){
            if(facturaLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        }
        if(facturaLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(facturaLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(facturaLinea.getPrecioUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.precio);
        if(facturaLinea.getValorDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(facturaLinea.getValorDescuentoLinea() > (facturaLinea.getCantidad()*facturaLinea.getPrecioUnitario())) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(facturaLinea.getPorcentajeDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
        if(facturaLinea.getPorcentajeDescuentoLinea() > 100) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
    }
    @Override
    public FacturaLinea calcularLinea(FacturaLinea facturaLinea) {
        validarLinea(facturaLinea);

        double subtotalLineaSinDescuento = facturaLinea.getCantidad() * facturaLinea.getPrecioUnitario();
        subtotalLineaSinDescuento = Math.round(subtotalLineaSinDescuento * 10000.0) / 10000.0;
        facturaLinea.setSubtotalLineaSinDescuento(subtotalLineaSinDescuento);

        double valorPorcentajeDescuentoLinea = (subtotalLineaSinDescuento * facturaLinea.getPorcentajeDescuentoLinea() / 100);
        valorPorcentajeDescuentoLinea = Math.round(valorPorcentajeDescuentoLinea * 10000.0) / 10000.0;
        facturaLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);

        double subtotalLinea = subtotalLineaSinDescuento - facturaLinea.getValorDescuentoLinea() - facturaLinea.getValorPorcentajeDescuentoLinea() -
                facturaLinea.getValorDescuentoTotalLinea() - facturaLinea.getValorPorcentajeDescuentoTotalLinea();
        subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
        facturaLinea.setSubtotalLinea(subtotalLinea);

        double importeIvaLinea = (subtotalLinea * (facturaLinea.getImpuesto().getPorcentaje() / 100));
        importeIvaLinea = Math.round(importeIvaLinea * 10000.0) / 10000.0;
        facturaLinea.setImporteIvaLinea(importeIvaLinea);

        double totalLinea = subtotalLinea + importeIvaLinea;
        totalLinea = Math.round(totalLinea * 100.0) / 100.0;
        facturaLinea.setTotalLinea(totalLinea);

        return facturaLinea;
    }

    public Factura calcular(Factura factura) {
        this.validar(factura);
        if (factura.getValorDescuentoTotal() != Constantes.cero) {
            this.calcularValorDescuentoTotal(factura);
        }
        for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
            calcularLinea(facturaLinea);
        }
        this.calcularTotales(factura);
        if (factura.getPorcentajeDescuentoTotal() != Constantes.cero) {
            this.calcularPorcentajeDescuentoTotal(factura);
            for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
                calcularLinea(facturaLinea);
            }
            this.calcularTotales(factura);
        }
        return factura;
    }

    private void calcularValorDescuentoTotal(Factura factura) {
        for (FacturaLinea facturaLinea : factura.getFacturaLineas()) {
            double ponderacion = facturaLinea.getSubtotalLineaSinDescuento() / factura.getSubtotal();

            double valorDescuentoTotalLinea = (factura.getValorDescuentoTotal() * ponderacion) * 100 / (100 + facturaLinea.getImpuesto().getPorcentaje());
            valorDescuentoTotalLinea = Math.round(valorDescuentoTotalLinea * 100.0) / 100.0;
            facturaLinea.setValorDescuentoTotalLinea(valorDescuentoTotalLinea);
        }
    }

    private void calcularPorcentajeDescuentoTotal(Factura factura) {

        double valorTotalPorcentajeDescuentoTotal = (factura.getTotal() * (factura.getPorcentajeDescuentoTotal() / 100));
        valorTotalPorcentajeDescuentoTotal = Math.round(valorTotalPorcentajeDescuentoTotal * 100.0) / 100.0;
        factura.setValorPorcentajeDescuentoTotal(valorTotalPorcentajeDescuentoTotal);

        for (FacturaLinea facturaLinea : factura.getFacturaLineas()) {
            double ponderacion = facturaLinea.getSubtotalLineaSinDescuento() / factura.getSubtotal();

            double valorPorcentajeDescuentoTotalLinea = (valorTotalPorcentajeDescuentoTotal * ponderacion) * 100 / (100 + facturaLinea.getImpuesto().getPorcentaje());
            valorPorcentajeDescuentoTotalLinea = Math.round(valorPorcentajeDescuentoTotalLinea * 100.0) / 100.0;
            facturaLinea.setValorPorcentajeDescuentoTotalLinea(valorPorcentajeDescuentoTotalLinea);
        }
    }
    private void calcularTotales(Factura factura) {
        double subtotalGravado = Constantes.cero, subtotalNoGravado = Constantes.cero;
        double importeIva = Constantes.cero, descuentoTotal = Constantes.cero;
        for (FacturaLinea facturaLinea : factura.getFacturaLineas()) {
            descuentoTotal += facturaLinea.getValorDescuentoLinea() + facturaLinea.getValorPorcentajeDescuentoLinea() +
                    facturaLinea.getValorDescuentoTotalLinea() + facturaLinea.getValorPorcentajeDescuentoTotalLinea();
            if (facturaLinea.getImpuesto().getPorcentaje() != Constantes.cero) {
                subtotalGravado += facturaLinea.getSubtotalLinea();
            } else {
                subtotalNoGravado += facturaLinea.getSubtotalLinea();
            }
            importeIva += facturaLinea.getImporteIvaLinea();
        }
        descuentoTotal = Math.round(descuentoTotal * 100.0) / 100.0;
        factura.setDescuento(descuentoTotal);

        subtotalGravado = Math.round(subtotalGravado * 100.0) / 100.0;
        factura.setSubtotalGravado(subtotalGravado);

        subtotalNoGravado = Math.round(subtotalNoGravado * 100.0) / 100.0;
        factura.setSubtotalNoGravado(subtotalNoGravado);

        importeIva = Math.round(importeIva * 100.0) / 100.0;
        factura.setImporteIva(importeIva);

        double subtotal = subtotalGravado + subtotalNoGravado;
        subtotal = Math.round(subtotal * 100.0) / 100.0;
        factura.setSubtotal(subtotal);

        double total = subtotalGravado + subtotalNoGravado + importeIva;
        total = Math.round(total * 100.0) / 100.0;
        factura.setTotal(total);
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
                    String nombre = "";
                    if(clienteBase.isPresent()) {
                        nombre = clienteBase.get().getApellidos()+Constantes.espacio+clienteBase.get().getNombres();
                    }
                    return nombre;
                }
                throw new IdentificacionInvalidaException();
            } else if (identificacion.equals(Constantes.identificacion_consumidor_final)) {
                return Constantes.consumidor_final;
            } else {
                throw new IdentificacionInvalidaException();
            }
        }
        throw new IdentificacionInvalidaException();
    }


    private Factura calcularRecaudacion(Factura factura){
        double total = Constantes.cero;
        total = total + factura.getEfectivo();

        double totalCheques = Constantes.cero;
        for(Cheque cheque: factura.getCheques()) {
            totalCheques = totalCheques + cheque.getValor();
            total = total + totalCheques;
        }

        double totalDepositos = Constantes.cero;
        for(Deposito deposito: factura.getDepositos()) {
            totalDepositos = totalDepositos + deposito.getValor();
            total = total + totalDepositos;
        }

        double totalTransferencias = Constantes.cero;
        for(Transferencia transferencia: factura.getTransferencias()) {
            totalTransferencias = totalTransferencias + transferencia.getValor();
            total = total + totalTransferencias;
        }

        double totalTarjetasDebitos = Constantes.cero;
        for(TarjetaDebito tarjetaDebito: factura.getTarjetasDebitos()) {
            totalTarjetasDebitos = totalTarjetasDebitos + tarjetaDebito.getValor();
            total = total + totalTarjetasDebitos;
        }

        double totalTarjetasCreditos = Constantes.cero;
        for(TarjetaCredito tarjetaCredito: factura.getTarjetasCreditos()) {
            totalTarjetasCreditos = totalTarjetasCreditos + tarjetaCredito.getValor();
            total = total + totalTarjetasCreditos;
        }

        total = total + factura.getCredito().getSaldo();
        factura.setTotalCredito(factura.getCredito().getSaldo());
        factura.setTotalCheques(totalCheques);
        factura.setTotalDepositos(totalDepositos);
        factura.setTotalTransferencias(totalTransferencias);
        factura.setTotalTarjetasDebitos(totalTarjetasDebitos);
        factura.setTotalTarjetasCreditos(totalTarjetasCreditos);

        if(total >= factura.getTotal()){
            double cambio = total - factura.getTotal();
            cambio = Math.round(cambio*100.0)/100.0;
            factura.setCambio(cambio);
        } else {
            factura.setCambio(Constantes.cero);
        }
        if(total >= factura.getTotal()){
            total = factura.getTotal();
        }
        double porPagar = factura.getTotal() - total;
        porPagar = Math.round(porPagar*100.0)/100.0;
        if(porPagar < 0) {
            porPagar = 0;
        }
        factura.setPorPagar(porPagar);
        factura.setTotalRecaudacion(total);
        return factura;
    }
}