package com.proyecto.vision.servicios.impl.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.cliente.*;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.venta.FacturaLinea;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.repositorios.cliente.IClienteBaseRepository;
import com.proyecto.vision.repositorios.venta.IFacturaRepository;
import com.proyecto.vision.servicios.interf.cliente.IClienteService;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.venta.IFacturaService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private ISecuencialService secuencialService;
    @Autowired
    private IClienteBaseRepository repClienteBase;
    @Autowired
    private IClienteService clienteService;

    @Override
    public void validar(Factura factura) {
        if(factura.getEstado().equals(Constantes.estadoInactivo)) throw new DatoInvalidoException(Constantes.estado);
        if(factura.getEstadoInterno().equals(Constantes.estadoInternoAnulada)) throw new DatoInvalidoException(Constantes.estado);
        if(factura.getEstadoSri().equals(Constantes.estadoSriAutorizada)) throw new DatoInvalidoException(Constantes.estado);
        if(factura.getEstadoSri().equals(Constantes.estadoSriAnulada)) throw new DatoInvalidoException(Constantes.estado);
        if(factura.getEmpresa().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
        if(factura.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(factura.getCliente().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cliente);
        if(factura.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(factura.getFacturaLineas().isEmpty()) throw new DatoInvalidoException(Constantes.factura_detalle);
        Cliente consumidorFinal = clienteService.obtenerPorIdentificacionYEmpresaYEstado(Constantes.identificacion_consumidor_final, factura.getSesion().getEmpresa().getId(), Constantes.estadoActivo);
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

    private void crearKardex(Factura factura) {
        kardexService.eliminar(2, 2, factura.getSecuencial());
        for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
            if(facturaLinea.getProducto().getCategoriaProducto().getDescripcion().equals(Constantes.bien)) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodega(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId());
                if (ultimoKardex == null) {
                    throw new DatoInvalidoException(Constantes.kardex);
                }
                if (ultimoKardex.getSaldo() < facturaLinea.getCantidad()) {
                    throw new DatoInvalidoException(Constantes.kardex);
                }
                double saldo = ultimoKardex.getSaldo() - facturaLinea.getCantidad();
                double costoTotal = ultimoKardex.getCostoTotal() - (facturaLinea.getCantidad() * ultimoKardex.getCostoPromedio());
                costoTotal = Math.round(costoTotal * 100.0) / 100.0;
                Kardex kardex = new Kardex(null, factura.getFecha(),
                        factura.getNumeroComprobante(), Constantes.cero, facturaLinea.getCantidad(), saldo,
                        Constantes.cero, ultimoKardex.getCostoPromedio(),
                        ultimoKardex.getCostoPromedio(), costoTotal,
                        new TipoComprobante(2), new TipoOperacion(2), facturaLinea.getBodega(), facturaLinea.getProducto());
                kardexService.crear(kardex);
            }
        }
    }

    private Optional<String> crearClaveAcceso(Factura factura) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fechaEmision = dateFormat.format(factura.getFecha());
        String tipoComprobante = Constantes.factura_sri;
        String numeroRuc = factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
        String tipoAmbiente = Constantes.pruebas_sri;
        String serie = factura.getEstablecimiento() + factura.getPuntoVenta();
        String numeroComprobante = factura.getSecuencial();
        String codigoNumerico = factura.getCodigoNumerico();
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
    public Factura crear(Factura factura) {
        validar(factura);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura);
        factura.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_factura,factura.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	factura.setCodigo(codigo.get());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(factura.getTipoComprobante().getId(),
                factura.getSesion().getUsuario().getEstacion().getId(), factura.getSesion().getEmpresa().getId(), Constantes.estadoActivo);
    	factura.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        factura.setNumeroComprobante(factura.getEstablecimiento() + Constantes.guion + factura.getPuntoVenta() + Constantes.guion + factura.getSecuencial());
    	factura.setCodigoNumerico(Util.generarCodigoNumerico(secuencial.getNumeroSiguiente()));
    	Optional<String> claveAcceso = crearClaveAcceso(factura);
    	if (claveAcceso.isEmpty()) {
    		throw new ClaveAccesoNoExistenteException();
    	}
    	factura.setClaveAcceso(claveAcceso.get());
    	factura.setEstado(Constantes.estadoActivo);
    	factura.setEstadoInterno(Constantes.estadoInternoEmitida);
    	factura.setEstadoSri(Constantes.estadoSriPendiente);
        calcular(factura);
        calcularRecaudacion(factura);
        crearKardex(factura);
        Factura res = rep.save(factura);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    @Override
    public Factura actualizar(Factura factura) {
        validar(factura);
        calcular(factura);
        calcularRecaudacion(factura);
        if(factura.getTotalRecaudacion() != factura.getTotal()){
            factura.setEstadoInterno(Constantes.estadoInternoEmitida);
        }
        if(factura.getTotalRecaudacion() == factura.getTotal()){
            factura.setEstadoInterno(Constantes.estadoInternoRecaudada);
        }
        Factura res = rep.save(factura);
        actualizarKardex(factura);
        res.normalizar();
        return res;
    }

    private void actualizarKardex(Factura factura) {
        for (FacturaLinea facturaLinea : factura.getFacturaLineas()) {
            int ultimoIndiceKardex = facturaLinea.getProducto().getKardexs().size() - 1;
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodega(facturaLinea.getProducto().getId(), facturaLinea.getBodega().getId());
            double saldo = Constantes.cero;
            double costoTotal = Constantes.cero;
            double costoUnitario = Constantes.cero;
            double costoPromedio = Constantes.cero;
            if (ultimoIndiceKardex > Constantes.cero) {
                saldo = facturaLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getSaldo() - facturaLinea.getCantidad();
                costoUnitario = facturaLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getCostoPromedio();
                costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;
                costoTotal = facturaLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getCostoTotal() - (costoUnitario * facturaLinea.getCantidad());
                costoTotal = Math.round(costoTotal * 100.0) / 100.0;
                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            ultimoKardex.setFecha(factura.getFecha());
            ultimoKardex.setEntrada(facturaLinea.getCantidad());
            ultimoKardex.setSaldo(saldo);
            ultimoKardex.setDebe(costoUnitario);
            ultimoKardex.setCostoPromedio(costoPromedio);
            ultimoKardex.setCostoTotal(costoTotal);
            kardexService.actualizar(ultimoKardex);
        }
    }

    @Override
    public Factura activar(Factura factura) {
        validar(factura);
        factura.setEstado(Constantes.estadoActivo);
        Factura res = rep.save(factura);
        res.normalizar();
        return res;
    }

    @Override
    public Factura inactivar(Factura factura) {
        validar(factura);
        factura.setEstado(Constantes.estadoInactivo);
        Factura res = rep.save(factura);
        res.normalizar();
        return res;
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
        crearKardex(factura);
        if(factura.getTotalRecaudacion() != factura.getTotal()){
            factura.setEstadoInterno(Constantes.estadoInternoEmitida);
        }
        if(factura.getTotalRecaudacion() == factura.getTotal()){
            factura.setEstadoInterno(Constantes.estadoInternoRecaudada);
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
    public List<Factura> consultarPorClienteYEstado(long facturaId, String estado) {
        return rep.consultarPorClienteYEstado(facturaId, estado);
    }

    @Override
    public List<Factura> consultarPorEmpresaYClienteYEstado(long empresaId, long facturaId, String estado) {
        return rep.consultarPorEmpresaYClienteYEstado(empresaId, facturaId, estado);
    }

    @Override
    public List<Factura> consultarPorClienteYEstadoYEstadoInternoYEstadoSri(long facturaId, String estado, String estadoInterno, String estadoSri) {
        return rep.consultarPorClienteYEstadoYEstadoInternoYEstadoSri(facturaId, estado, estadoInterno, estadoSri);
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
        if(facturaLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(facturaLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(facturaLinea.getPrecio().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.precio);
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

        double valorPorcentajeDescuentoLinea = (facturaLinea.getPrecioUnitario() * facturaLinea.getPorcentajeDescuentoLinea() / 100);
        valorPorcentajeDescuentoLinea = Math.round(valorPorcentajeDescuentoLinea * 10000.0) / 10000.0;
        facturaLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);

        double subtotalLinea = (facturaLinea.getPrecioUnitario() - facturaLinea.getValorDescuentoLinea() - valorPorcentajeDescuentoLinea) * facturaLinea.getCantidad();
        subtotalLinea = Math.round(subtotalLinea * 10000.0) / 10000.0;
        facturaLinea.setSubtotalLinea(subtotalLinea);

        double valorIvaLinea = (subtotalLinea * (facturaLinea.getImpuesto().getPorcentaje() / 100));
        valorIvaLinea = Math.round(valorIvaLinea * 100.0) / 100.0;
        facturaLinea.setImporteIvaLinea(valorIvaLinea);

        double totalLinea = subtotalLinea + valorIvaLinea;
        totalLinea = Math.round(totalLinea * 100.0) / 100.0;
        facturaLinea.setTotalLinea(totalLinea);

        return facturaLinea;
    }

    public Factura calcular(Factura factura) {
        this.validar(factura);
        for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
            calcularLinea(facturaLinea);
        }
        this.calcularSubtotal(factura);
        this.calcularDescuento(factura);
        this.calcularTotales(factura);
        return factura;
    }

    /*
     * CALCULOS TOTALES FACTURA DE VENTA
     */
    private void calcularSubtotal(Factura factura) {
    	double subtotal = Constantes.cero;
        for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
          subtotal += facturaLinea.getSubtotalLinea();
        }
        subtotal = Math.round(subtotal * 10000.0) / 10000.0;
        factura.setSubtotal(subtotal);
    }

    private void calcularDescuento(Factura factura) {
        double descuento = Constantes.cero;

        for (FacturaLinea facturaLinea : factura.getFacturaLineas()) {
            descuento += facturaLinea.getValorDescuentoLinea() + facturaLinea.getPorcentajeDescuentoLinea();
        }
        descuento = Math.round(descuento * 100.0) / 100.0;
        factura.setDescuento(descuento);
    }

    private void calcularTotales(Factura factura) {
        double subtotalGravado = Constantes.cero;
        double subtotalNoGravado = Constantes.cero;
        double importeIva = Constantes.cero;
        double total = Constantes.cero;
        for (FacturaLinea facturaLinea : factura.getFacturaLineas()) {
            if (facturaLinea.getImpuesto().getPorcentaje() != Constantes.cero) {
                subtotalGravado += facturaLinea.getSubtotalLinea();
            } else {
                subtotalNoGravado += facturaLinea.getSubtotalLinea();
            }
            importeIva += facturaLinea.getImporteIvaLinea();
        }
        subtotalGravado = Math.round(subtotalGravado * 100.0) / 100.0;
        factura.setSubtotalGravado(subtotalGravado);

        subtotalNoGravado = Math.round(subtotalNoGravado * 100.0) / 100.0;
        factura.setSubtotalNoGravado(subtotalNoGravado);

        importeIva = Math.round(importeIva * 100.0) / 100.0;
        factura.setImporteIva(importeIva);

        total = subtotalGravado + subtotalNoGravado + importeIva;
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
                return "CONSUMIDOR FINAL";
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
