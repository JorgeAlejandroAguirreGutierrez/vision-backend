package com.proyecto.vision.servicios.impl.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.acceso.*;
import com.proyecto.vision.modelos.cliente.*;
import com.proyecto.vision.modelos.compra.GrupoProveedor;
import com.proyecto.vision.modelos.configuracion.*;
import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.inventario.Medida;
import com.proyecto.vision.repositorios.acceso.*;
import com.proyecto.vision.repositorios.cliente.*;
import com.proyecto.vision.repositorios.compra.IGrupoProveedorRepository;
import com.proyecto.vision.repositorios.configuracion.*;
import com.proyecto.vision.repositorios.contabilidad.ICuentaContableRepository;
import com.proyecto.vision.repositorios.inventario.IBodegaRepository;
import com.proyecto.vision.repositorios.inventario.IMedidaRepository;
import com.proyecto.vision.servicios.interf.acceso.INuevoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class NuevoService implements INuevoService {

    @Autowired
    private INuevoRepository rep;
    @Autowired
    private IEmpresaRepository repEmpresa;
    @Autowired
    private ITipoIdentificacionRepository repTipoIdentificacion;
    @Autowired
    private ITipoContribuyenteRepository repTipoContribuyente;
    @Autowired
    private IBodegaRepository repBodega;
    @Autowired
    private ICuentaContableRepository repCuentaContable;
    @Autowired
    private IRegimenRepository repRegimen;
    @Autowired
    private IUbicacionRepository repUbicacion;
    @Autowired
    private IPerfilRepository repPerfil;
    @Autowired
    private ICalificacionClienteRepository repCalificacionCliente;
    @Autowired
    private IGrupoClienteRepository repGrupoCliente;
    @Autowired
    private IGrupoProveedorRepository repGrupoProveedor;
    @Autowired
    private IMedidaRepository repMedida;
    @Autowired
    private IPlazoCreditoRepository repPlazoCredito;
    @Autowired
    private ISegmentoRepository repSegmento;
    @Autowired
    private IFormaPagoRepository repFormaPago;
    @Autowired
    private IGeneroRepository repGenero;
    @Autowired
    private IEstadoCivilRepository repEstadoCivil;
    @Autowired
    private IOrigenIngresoRepository repOrigenIngreso;
    @Autowired
    private IClienteRepository repCliente;
    @Autowired
    private IEstablecimientoRepository repEstablecimiento;
    @Autowired
    private IEstacionRepository repEstacion;
    @Autowired
    private IUsuarioRepository repUsuario;
    @Autowired
    private ITipoComprobanteRepository repTipoComprobante;
    @Autowired
    private ISecuencialRepository repSecuencial;

    @Override
    public void validar(Nuevo nuevo) {
        if(nuevo.getGenero().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.genero);
        if(nuevo.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(nuevo.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.razonSocial);
        if(nuevo.getNombreComercial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombreComercial);
        if(nuevo.getObligado().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.obligadoContabilidad);
        if(nuevo.getDireccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
        if(nuevo.getEspecial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.especial);
        if(nuevo.getAgenteRetencion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.agenteRetencion);
        if(nuevo.getRegimen().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.regimen);
        if(nuevo.getGranContribuyente().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.granContribuyente);
        if(nuevo.getArtesanoCalificado().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.artesanoCalificado);
        if(nuevo.getFacturacionInterna().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.facturacionInterna);
        if(nuevo.getProvincia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.provincia);
        if(nuevo.getCanton().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.canton);
        if(nuevo.getParroquia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.parroquia);
        if(nuevo.getCelular().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.celular);
        if(nuevo.getCorreo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.correo);
        if(nuevo.getEstablecimientoSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.establecimiento);
        if(nuevo.getEstacionSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.estacion);
        if(nuevo.getContrasenaCertificado().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.contrasenaCertificado);
        if(nuevo.getSecuencialFacturaVenta() < 1) throw new DatoInvalidoException(Constantes.secuenciaFacturaVenta);
        if(nuevo.getSecuencialFacturaInterna() < 1) throw new DatoInvalidoException(Constantes.secuenciaFacturaInterna);
        if(nuevo.getSecuencialRetencion() < 1) throw new DatoInvalidoException(Constantes.secuenciaRetencion);
        if(nuevo.getSecuencialGuiaRemision() < 1) throw new DatoInvalidoException(Constantes.secuenciaGuiaRemision);
        if(nuevo.getSecuencialNotaDebito() < 1) throw new DatoInvalidoException(Constantes.secuenciaNotaDebito);
        if(nuevo.getSecuencialNotaCredito() < 1) throw new DatoInvalidoException(Constantes.secuenciaNotaCredito);
    }

    @Override
    public Nuevo crear(Nuevo nuevo) {
        validar(nuevo);
        Optional<Nuevo> buscarEmpresa = rep.obtenerPorIdentificacion(nuevo.getIdentificacion());
        if(buscarEmpresa.isPresent()) {
          throw new EntidadExistenteException(Constantes.empresa);
        }
        Optional<String>codigo = Util.generarCodigo(Constantes.tabla_nuevo);
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        nuevo.setCodigo(codigo.get());
        Nuevo res = rep.save(nuevo);
        res.normalizar();
        return res;
    }

    @Transactional
    @Override
    public Nuevo ejecutar(Nuevo nuevo) {
        validar(nuevo);
        Optional<Empresa> buscarEmpresa = repEmpresa.obtenerPorIdentificacion(nuevo.getIdentificacion());
        if(buscarEmpresa.isPresent()) {
            throw new EntidadExistenteException(Constantes.empresa);
        }
        Empresa empresa = new Empresa();
        Optional<String> codigoEmpresa = Util.generarCodigo(Constantes.tabla_empresa);
        if (codigoEmpresa.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        empresa.setCodigo(codigoEmpresa.get());
        empresa.setIdentificacion(nuevo.getIdentificacion());
        empresa.setRazonSocial(nuevo.getRazonSocial());
        empresa.setNombreComercial(nuevo.getNombreComercial());
        empresa.setRepresentanteLegal(nuevo.getRazonSocial());
        empresa.setCargoRepresentanteLegal(Constantes.propietario);
        empresa.setLogo(nuevo.getIdentificacion() + Constantes.jpg);
        empresa.setDireccion(nuevo.getDireccion());
        empresa.setObligadoContabilidad(nuevo.getObligado());
        empresa.setEspecial(nuevo.getEspecial());
        empresa.setResolucionEspecial(nuevo.getNumeroResolucionEspecial());
        empresa.setAgenteRetencion(nuevo.getAgenteRetencion());
        empresa.setResolucionAgente(nuevo.getNumeroAgenteRetencion());
        empresa.setEstado(Constantes.estadoActivo);
        empresa.setCertificado(nuevo.getIdentificacion()+Constantes.p12);
        empresa.setContrasena(nuevo.getContrasenaCertificado());
        empresa.setContrasenaSRI(nuevo.getContrasenaSRI());
        empresa.setFacturacionInterna(nuevo.getFacturacionInterna());
        empresa.setGranContribuyente(nuevo.getGranContribuyente());
        empresa.setArtesanoCalificado(nuevo.getArtesanoCalificado());
        Optional<TipoIdentificacion> tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.ruc_codigo_sri);
        if(tipoIdentificacion.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.tipo_identificacion);
        }
        empresa.setTipoIdentificacion(tipoIdentificacion.get());
        empresa = repEmpresa.save(empresa);
        Bodega bodega = new Bodega();
        Optional<String> codigoBodega = Util.generarCodigo(Constantes.tabla_bodega);
        if (codigoBodega.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        bodega.setCodigo(codigoBodega.get());
        bodega.setNombre(Constantes.bodegaNombreDefecto);
        bodega.setAbreviatura(Constantes.bodegaAbreviaturaDefecto);
        bodega.setEmpresa(empresa);
        bodega = repBodega.save(bodega);

        CalificacionCliente calificacionCliente1 = new CalificacionCliente();
        Optional<String> codigoCalificacionCliente1 = Util.generarCodigo(Constantes.tabla_calificacion_cliente);
        if (codigoCalificacionCliente1.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        calificacionCliente1.setCodigo(codigoBodega.get());
        calificacionCliente1.setDescripcion(Constantes.calificacionCliente1DescripcionDefecto);
        calificacionCliente1.setAbreviatura(Constantes.calificacionCliente1AbreviaturaDefecto);
        calificacionCliente1.setEstado(Constantes.estadoActivo);
        calificacionCliente1.setEmpresa(empresa);
        calificacionCliente1 = repCalificacionCliente.save(calificacionCliente1);

        CalificacionCliente calificacionCliente2 = new CalificacionCliente();
        Optional<String> codigoCalificacionCliente2 = Util.generarCodigo(Constantes.tabla_calificacion_cliente);
        if (codigoCalificacionCliente2.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        calificacionCliente2.setCodigo(codigoBodega.get());
        calificacionCliente2.setDescripcion(Constantes.calificacionCliente2DescripcionDefecto);
        calificacionCliente2.setAbreviatura(Constantes.calificacionCliente2AbreviaturaDefecto);
        calificacionCliente2.setEstado(Constantes.estadoActivo);
        calificacionCliente2.setEmpresa(empresa);
        calificacionCliente2 = repCalificacionCliente.save(calificacionCliente2);

        CalificacionCliente calificacionCliente3 = new CalificacionCliente();
        Optional<String> codigoCalificacionCliente3 = Util.generarCodigo(Constantes.tabla_calificacion_cliente);
        if (codigoCalificacionCliente3.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        calificacionCliente3.setCodigo(codigoBodega.get());
        calificacionCliente3.setDescripcion(Constantes.calificacionCliente3DescripcionDefecto);
        calificacionCliente3.setAbreviatura(Constantes.calificacionCliente3AbreviaturaDefecto);
        calificacionCliente3.setEstado(Constantes.estadoActivo);
        calificacionCliente3.setEmpresa(empresa);
        calificacionCliente3 = repCalificacionCliente.save(calificacionCliente3);

        CalificacionCliente calificacionCliente4 = new CalificacionCliente();
        Optional<String> codigoCalificacionCliente4 = Util.generarCodigo(Constantes.tabla_calificacion_cliente);
        if (codigoCalificacionCliente4.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        calificacionCliente4.setCodigo(codigoBodega.get());
        calificacionCliente4.setDescripcion(Constantes.calificacionCliente4DescripcionDefecto);
        calificacionCliente4.setAbreviatura(Constantes.calificacionCliente4AbreviaturaDefecto);
        calificacionCliente4.setEstado(Constantes.estadoActivo);
        calificacionCliente4.setEmpresa(empresa);
        calificacionCliente4 = repCalificacionCliente.save(calificacionCliente4);

        CalificacionCliente calificacionCliente5 = new CalificacionCliente();
        Optional<String> codigoCalificacionCliente5 = Util.generarCodigo(Constantes.tabla_calificacion_cliente);
        if (codigoCalificacionCliente5.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        calificacionCliente5.setCodigo(codigoBodega.get());
        calificacionCliente5.setDescripcion(Constantes.calificacionCliente5DescripcionDefecto);
        calificacionCliente5.setAbreviatura(Constantes.calificacionCliente5AbreviaturaDefecto);
        calificacionCliente5.setEstado(Constantes.estadoActivo);
        calificacionCliente5.setEmpresa(empresa);
        calificacionCliente5 = repCalificacionCliente.save(calificacionCliente5);

        GrupoCliente grupoCliente = new GrupoCliente();
        Optional<String> codigoGrupoCliente = Util.generarCodigo(Constantes.tabla_grupo_cliente);
        if (codigoGrupoCliente.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        grupoCliente.setCodigo(codigoGrupoCliente.get());
        grupoCliente.setDescripcion(Constantes.grupoClienteDescripcionDefecto);
        grupoCliente.setAbreviatura(Constantes.grupoClienteAbreviaturaDefecto);
        grupoCliente.setEstado(Constantes.estadoActivo);
        Optional<CuentaContable> cuentaContable1 = repCuentaContable.findById((long)Constantes.uno);
        if(cuentaContable1.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.cuenta_contable);
        }
        grupoCliente.setCuentaContable(cuentaContable1.get());
        grupoCliente.setEmpresa(empresa);
        grupoCliente = repGrupoCliente.save(grupoCliente);

        GrupoProveedor grupoProveedor = new GrupoProveedor();
        Optional<String> codigoGrupoProveedor = Util.generarCodigo(Constantes.tabla_grupo_proveedor);
        if (codigoGrupoProveedor.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        grupoProveedor.setCodigo(codigoGrupoProveedor.get());
        grupoProveedor.setDescripcion(Constantes.grupoProveedorDescripcionDefecto);
        grupoProveedor.setAbreviatura(Constantes.grupoProveedorAbreviaturaDefecto);
        grupoProveedor.setEstado(Constantes.estadoActivo);
        Optional<CuentaContable> cuentaContable2 = repCuentaContable.findById((long)Constantes.uno);
        if(cuentaContable2.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.cuenta_contable);
        }
        grupoProveedor.setCuentaContable(cuentaContable2.get());
        grupoProveedor.setEmpresa(empresa);
        grupoProveedor = repGrupoProveedor.save(grupoProveedor);

        Medida medidaUnidad1 = new Medida();
        Optional<String> codigoMedidaUnidad1 = Util.generarCodigo(Constantes.tabla_medida);
        if (codigoMedidaUnidad1.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        medidaUnidad1.setCodigo(codigoMedidaUnidad1.get());
        medidaUnidad1.setTipo(Constantes.medidaTipoUnidad);
        medidaUnidad1.setDescripcion(Constantes.medidaDescripcionUnidad);
        medidaUnidad1.setAbreviatura(Constantes.medidaAbreviaturaUnidad);
        medidaUnidad1.setEstado(Constantes.estadoActivo);
        medidaUnidad1.setEmpresa(empresa);
        medidaUnidad1 = repMedida.save(medidaUnidad1);

        Medida medidaUnidad2 = new Medida();
        Optional<String> codigoMedidaUnidad2 = Util.generarCodigo(Constantes.tabla_medida);
        if (codigoMedidaUnidad2.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        medidaUnidad2.setCodigo(codigoMedidaUnidad2.get());
        medidaUnidad2.setTipo(Constantes.medidaTipoUnidad);
        medidaUnidad2.setDescripcion(Constantes.medidaDescripcionMitad);
        medidaUnidad2.setAbreviatura(Constantes.medidaAbreviaturaMitad);
        medidaUnidad2.setEstado(Constantes.estadoActivo);
        medidaUnidad2.setEmpresa(empresa);
        medidaUnidad2 = repMedida.save(medidaUnidad2);

        Medida medidaUnidad3 = new Medida();
        Optional<String> codigoMedidaUnidad3 = Util.generarCodigo(Constantes.tabla_medida);
        if (codigoMedidaUnidad3.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        medidaUnidad3.setCodigo(codigoMedidaUnidad3.get());
        medidaUnidad3.setTipo(Constantes.medidaTipoUnidad);
        medidaUnidad3.setDescripcion(Constantes.medidaDescripcionCuarto);
        medidaUnidad3.setAbreviatura(Constantes.medidaAbreviaturaCuarto);
        medidaUnidad3.setEstado(Constantes.estadoActivo);
        medidaUnidad3.setEmpresa(empresa);
        medidaUnidad3 = repMedida.save(medidaUnidad3);

        Medida medidaPeso1 = new Medida();
        Optional<String> codigoMedidaPeso1 = Util.generarCodigo(Constantes.tabla_medida);
        if (codigoMedidaPeso1.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        medidaPeso1.setCodigo(codigoMedidaPeso1.get());
        medidaPeso1.setTipo(Constantes.medidaTipoPeso);
        medidaPeso1.setDescripcion(Constantes.medidaDescripcionGramo);
        medidaPeso1.setAbreviatura(Constantes.medidaAbreviaturaGramo);
        medidaPeso1.setEstado(Constantes.estadoActivo);
        medidaPeso1.setEmpresa(empresa);
        medidaPeso1 = repMedida.save(medidaPeso1);

        Medida medidaPeso2 = new Medida();
        Optional<String> codigoMedidaPeso2 = Util.generarCodigo(Constantes.tabla_medida);
        if (codigoMedidaPeso2.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        medidaPeso2.setCodigo(codigoMedidaPeso2.get());
        medidaPeso2.setTipo(Constantes.medidaTipoPeso);
        medidaPeso2.setDescripcion(Constantes.medidaDescripcionKilogramo);
        medidaPeso2.setAbreviatura(Constantes.medidaAbreviaturaKilogramo);
        medidaPeso2.setEstado(Constantes.estadoActivo);
        medidaPeso2.setEmpresa(empresa);
        medidaPeso2 = repMedida.save(medidaPeso2);

        Medida medidaPeso3 = new Medida();
        Optional<String> codigoMedidaPeso3 = Util.generarCodigo(Constantes.tabla_medida);
        if (codigoMedidaPeso3.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        medidaPeso3.setCodigo(codigoMedidaPeso3.get());
        medidaPeso3.setTipo(Constantes.medidaTipoPeso);
        medidaPeso3.setDescripcion(Constantes.medidaDescripcionLibra);
        medidaPeso3.setAbreviatura(Constantes.medidaAbreviaturaLibra);
        medidaPeso3.setEstado(Constantes.estadoActivo);
        medidaPeso3.setEmpresa(empresa);
        medidaPeso3 = repMedida.save(medidaPeso3);

        Medida medidaVolumen1 = new Medida();
        Optional<String> codigoMedidaVolumen1 = Util.generarCodigo(Constantes.tabla_medida);
        if (codigoMedidaVolumen1.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        medidaVolumen1.setCodigo(codigoMedidaVolumen1.get());
        medidaVolumen1.setTipo(Constantes.medidaTipoVolumen);
        medidaVolumen1.setDescripcion(Constantes.medidaDescripcionCentimetroCubico);
        medidaVolumen1.setAbreviatura(Constantes.medidaAbreviaturaCentimetroCubico);
        medidaVolumen1.setEstado(Constantes.estadoActivo);
        medidaVolumen1.setEmpresa(empresa);
        medidaVolumen1 = repMedida.save(medidaVolumen1);

        Medida medidaVolumen2 = new Medida();
        Optional<String> codigoMedidaVolumen2 = Util.generarCodigo(Constantes.tabla_medida);
        if (codigoMedidaVolumen2.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        medidaVolumen2.setCodigo(codigoMedidaVolumen2.get());
        medidaVolumen2.setTipo(Constantes.medidaTipoVolumen);
        medidaVolumen2.setDescripcion(Constantes.medidaDescripcionGalon);
        medidaVolumen2.setAbreviatura(Constantes.medidaAbreviaturaGalon);
        medidaVolumen2.setEstado(Constantes.estadoActivo);
        medidaVolumen2.setEmpresa(empresa);
        medidaVolumen2 = repMedida.save(medidaVolumen2);

        Medida medidaVolumen3 = new Medida();
        Optional<String> codigoMedidaVolumen3 = Util.generarCodigo(Constantes.tabla_medida);
        if (codigoMedidaVolumen3.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        medidaVolumen3.setCodigo(codigoMedidaVolumen3.get());
        medidaVolumen3.setTipo(Constantes.medidaTipoVolumen);
        medidaVolumen3.setDescripcion(Constantes.medidaDescripcionLitro);
        medidaVolumen3.setAbreviatura(Constantes.medidaAbreviaturaLitro);
        medidaVolumen3.setEstado(Constantes.estadoActivo);
        medidaVolumen3.setEmpresa(empresa);
        medidaVolumen3 = repMedida.save(medidaVolumen3);

        PlazoCredito plazoCredito = new PlazoCredito();
        Optional<String> codigoPlazoCredito = Util.generarCodigo(Constantes.tabla_plazo_credito);
        if (codigoPlazoCredito.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        plazoCredito.setCodigo(codigoPlazoCredito.get());
        plazoCredito.setDescripcion(Constantes.plazoCreditoDescripcionDefecto);
        plazoCredito.setAbreviatura(Constantes.plazoCreditoAbreviaturaDefecto);
        plazoCredito.setPlazo(Constantes.cero);
        plazoCredito.setEstado(Constantes.estadoActivo);
        plazoCredito.setEmpresa(empresa);
        plazoCredito = repPlazoCredito.save(plazoCredito);

        Segmento segmento = new Segmento();
        Optional<String> codigoSegmento = Util.generarCodigo(Constantes.tabla_segmento);
        if (codigoSegmento.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        segmento.setCodigo(codigoSegmento.get());
        segmento.setDescripcion(Constantes.segmentoDescripcionDefecto);
        segmento.setAbreviatura(Constantes.segmentoAbreviaturaDefecto);
        segmento.setMargenGanancia(Constantes.segmentoMargenGananciaDefecto);
        segmento.setEstado(Constantes.estadoActivo);
        segmento.setEmpresa(empresa);
        segmento = repSegmento.save(segmento);

        Cliente cliente = new Cliente();
        Optional<String> codigoCliente = Util.generarCodigo(Constantes.tabla_cliente);
        if (codigoCliente.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        cliente.setCodigo(codigoCliente.get());
        cliente.setIdentificacion(Constantes.identificacion_consumidor_final);
        cliente.setRazonSocial(Constantes.consumidor_final);
        cliente.setObligadoContabilidad(Constantes.no);
        cliente.setEspecial(Constantes.no);
        cliente.setEstado(Constantes.estadoActivo);
        cliente.setDireccion(Constantes.consumidor_final);
        cliente.setEtiqueta(Constantes.clienteEtiquetaDefecto);
        cliente.setReferencia(Constantes.vacio);
        cliente.setLatitudgeo(Constantes.cero);
        cliente.setLongitudgeo(Constantes.cero);
        cliente.setMontoFinanciamiento(Constantes.cero);
        Optional<TipoIdentificacion> tipoIdentificacionCliente = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.ruc_codigo_sri);
        if(tipoIdentificacionCliente.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.tipo_identificacion);
        }
        cliente.setTipoIdentificacion(tipoIdentificacionCliente.get());
        Optional<TipoContribuyente> tipoContribuyente = repTipoContribuyente.obtenerPorTipoYSubtipo(Constantes.tipo_contribuyente_juridica, Constantes.tipo_contribuyente_publica);
        if(tipoContribuyente.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.tipo_contribuyente);
        }
        cliente.setTipoContribuyente(tipoContribuyente.get());
        cliente.setGrupoCliente(grupoCliente);
        Optional<FormaPago> formaPago = repFormaPago.obtenerPorCodigoSRIYEstado(Constantes.sin_utilizacion_del_sistema_financiero, Constantes.estadoActivo);
        if(formaPago.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.forma_pago);
        }
        cliente.setFormaPago(formaPago.get());
        cliente.setPlazoCredito(plazoCredito);
        Optional<Ubicacion> ubicacion = repUbicacion.obtenerPorProvinciaYCantonYParroquia(nuevo.getProvincia(), nuevo.getCanton(), nuevo.getParroquia(), Constantes.estadoActivo);
        if(ubicacion.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.ubicacion);
        }
        cliente.setUbicacion(ubicacion.get());
        Optional<Genero> genero = repGenero.obtenerPorDescripcionYEstado(Constantes.masculino, Constantes.estadoActivo);
        if(genero.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.genero);
        }
        cliente.setGenero(genero.get());
        Optional<EstadoCivil> estadoCivil = repEstadoCivil.obtenerPorDescripcionYEstado(Constantes.soltero, Constantes.estadoActivo);
        if(estadoCivil.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.estado_civil);
        }
        cliente.setEstadoCivil(estadoCivil.get());
        cliente.setCalificacionCliente(calificacionCliente1);
        Optional<OrigenIngreso> origenIngreso = repOrigenIngreso.obtenerPorDescripcionYEstado(Constantes.salario, Constantes.estadoActivo);
        if(origenIngreso.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.origen_ingreso);
        }
        cliente.setOrigenIngreso(origenIngreso.get());
        cliente.setSegmento(segmento);
        cliente.setEmpresa(empresa);
        cliente.setTelefonos(Collections.emptyList());
        cliente.setCelulares(Collections.emptyList());
        cliente.setCorreos(Collections.emptyList());
        cliente.setRetencionesCliente(Collections.emptyList());
        cliente = repCliente.save(cliente);

        CelularEstablecimiento celularEstablecimiento = new CelularEstablecimiento();
        Optional<String> codigoCelularEstablecimiento = Util.generarCodigo(Constantes.tabla_celular_establecimiento);
        if (codigoCelularEstablecimiento.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        celularEstablecimiento.setCodigo(codigoCelularEstablecimiento.get());
        celularEstablecimiento.setNumero(nuevo.getCelular());

        CorreoEstablecimiento correoEstablecimiento = new CorreoEstablecimiento();
        Optional<String> codigoCorreoEstablecimiento = Util.generarCodigo(Constantes.tabla_correo_establecimiento);
        if (codigoCorreoEstablecimiento.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        correoEstablecimiento.setCodigo(codigoCelularEstablecimiento.get());
        correoEstablecimiento.setEmail(nuevo.getCorreo());

        Establecimiento establecimiento = new Establecimiento();
        Optional<String> codigoEstablecimiento = Util.generarCodigo(Constantes.tabla_establecimiento);
        if (codigoEstablecimiento.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        establecimiento.setCodigo(codigoEstablecimiento.get());
        establecimiento.setCodigoSRI(nuevo.getEstablecimientoSRI());
        establecimiento.setDescripcion(nuevo.getRazonSocial());
        establecimiento.setDireccion(nuevo.getDireccion());
        establecimiento.setLatitudgeo(Constantes.cero);
        establecimiento.setLongitudgeo(Constantes.cero);
        establecimiento.setEstado(Constantes.estadoActivo);
        Optional<Regimen> regimen = repRegimen.obtenerPorAbreviatura(nuevo.getRegimen());
        if(regimen.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.regimen);
        }
        establecimiento.setRegimen(regimen.get());
        establecimiento.setUbicacion(ubicacion.get());
        establecimiento.setEmpresa(empresa);
        establecimiento.setTelefonosEstablecimiento(Collections.emptyList());
        establecimiento.setCelularesEstablecimiento(new ArrayList<>());
        establecimiento.getCelularesEstablecimiento().add(celularEstablecimiento);
        establecimiento.setCorreosEstablecimiento(new ArrayList<>());
        establecimiento.getCorreosEstablecimiento().add(correoEstablecimiento);

        establecimiento = repEstablecimiento.save(establecimiento);

        Estacion estacion = new Estacion();
        Optional<String> codigoEstacion = Util.generarCodigo(Constantes.tabla_estacion);
        if (codigoEstacion.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        estacion.setCodigo(codigoEstacion.get());
        estacion.setCodigoSRI(nuevo.getEstacionSRI());
        estacion.setDescripcion(Constantes.estacionDescripcionDefecto);
        estacion.setDispositivo(Constantes.estacionDispositivoDefecto);
        estacion.setIp(Constantes.estacionIpDefecto);
        estacion.setPuntoVenta(Constantes.si);
        estacion.setEstado(Constantes.estadoActivo);
        estacion.setRegimen(null);
        estacion.setEstablecimiento(establecimiento);
        estacion = repEstacion.save(estacion);

        Secuencial secuencial1 = new Secuencial();
        Optional<String> codigoSecuencial1 = Util.generarCodigo(Constantes.tabla_secuencial);
        if (codigoSecuencial1.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        secuencial1.setCodigo(codigoSecuencial1.get());
        Optional<TipoComprobante> tipoComprobante1 = repTipoComprobante.obtenerPorNombreTabla(Constantes.tabla_factura);
        if(tipoComprobante1.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.tipo_comprobante);
        }
        secuencial1.setTipoComprobante(tipoComprobante1.get());
        secuencial1.setNumeroSiguiente(nuevo.getSecuencialFacturaVenta());
        secuencial1.setEstado(Constantes.estadoActivo);
        secuencial1.setEstacion(estacion);
        secuencial1 = repSecuencial.save(secuencial1);

        Secuencial secuencial2 = new Secuencial();
        Optional<String> codigoSecuencial2 = Util.generarCodigo(Constantes.tabla_secuencial);
        if (codigoSecuencial2.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        secuencial2.setCodigo(codigoSecuencial1.get());
        Optional<TipoComprobante> tipoComprobante2 = repTipoComprobante.obtenerPorNombreTabla(Constantes.tabla_nota_credito);
        if(tipoComprobante2.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.tipo_comprobante);
        }
        secuencial2.setTipoComprobante(tipoComprobante2.get());
        secuencial2.setNumeroSiguiente(nuevo.getSecuencialNotaCredito());
        secuencial2.setEstado(Constantes.estadoActivo);
        secuencial2.setEstacion(estacion);
        secuencial2 = repSecuencial.save(secuencial2);

        Secuencial secuencial3 = new Secuencial();
        Optional<String> codigoSecuencial3 = Util.generarCodigo(Constantes.tabla_secuencial);
        if (codigoSecuencial3.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        secuencial3.setCodigo(codigoSecuencial3.get());
        Optional<TipoComprobante> tipoComprobante3 = repTipoComprobante.obtenerPorNombreTabla(Constantes.tabla_nota_debito);
        if(tipoComprobante3.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.tipo_comprobante);
        }
        secuencial3.setTipoComprobante(tipoComprobante3.get());
        secuencial3.setNumeroSiguiente(nuevo.getSecuencialNotaDebito());
        secuencial3.setEstado(Constantes.estadoActivo);
        secuencial3.setEstacion(estacion);
        secuencial3 = repSecuencial.save(secuencial3);

        Secuencial secuencial4 = new Secuencial();
        Optional<String> codigoSecuencial4 = Util.generarCodigo(Constantes.tabla_secuencial);
        if (codigoSecuencial4.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        secuencial4.setCodigo(codigoSecuencial4.get());
        Optional<TipoComprobante> tipoComprobante4 = repTipoComprobante.obtenerPorNombreTabla(Constantes.tabla_guia_remision);
        if(tipoComprobante4.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.tipo_comprobante);
        }
        secuencial4.setTipoComprobante(tipoComprobante4.get());
        secuencial4.setNumeroSiguiente(nuevo.getSecuencialGuiaRemision());
        secuencial4.setEstado(Constantes.estadoActivo);
        secuencial4.setEstacion(estacion);
        secuencial4 = repSecuencial.save(secuencial4);

        Secuencial secuencial5 = new Secuencial();
        Optional<String> codigoSecuencial5 = Util.generarCodigo(Constantes.tabla_secuencial);
        if (codigoSecuencial5.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        secuencial5.setCodigo(codigoSecuencial5.get());
        Optional<TipoComprobante> tipoComprobante5 = repTipoComprobante.obtenerPorNombreTabla(Constantes.tabla_retencion);
        if(tipoComprobante5.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.tipo_comprobante);
        }
        secuencial5.setTipoComprobante(tipoComprobante5.get());
        secuencial5.setNumeroSiguiente(nuevo.getSecuencialRetencion());
        secuencial5.setEstado(Constantes.estadoActivo);
        secuencial5.setEstacion(estacion);
        secuencial5 = repSecuencial.save(secuencial5);

        Secuencial secuencial6 = new Secuencial();
        Optional<String> codigoSecuencial6 = Util.generarCodigo(Constantes.tabla_secuencial);
        if (codigoSecuencial6.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        secuencial6.setCodigo(codigoSecuencial6.get());
        secuencial6.setNumeroSiguiente(nuevo.getSecuencialFacturaInterna());
        Optional<TipoComprobante> tipoComprobante6 = repTipoComprobante.obtenerPorAbreviaturaYEstado(Constantes.factura_interna, Constantes.estadoActivo);
        if(tipoComprobante6.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.tipo_comprobante);
        }
        secuencial6.setTipoComprobante(tipoComprobante5.get());
        secuencial6.setNumeroSiguiente(nuevo.getSecuencialFacturaInterna());
        secuencial6.setEstado(Constantes.estadoActivo);
        secuencial6.setEstacion(estacion);
        secuencial6 = repSecuencial.save(secuencial6);

        Usuario usuario = new Usuario();
        Optional<String> codigoUsuario = Util.generarCodigo(Constantes.tabla_usuario);
        if (codigoUsuario.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        usuario.setCodigo(codigoUsuario.get());
        usuario.setIdentificacion(nuevo.getIdentificacion().substring(0, nuevo.getIdentificacion().length() - 3));
        usuario.setApodo(nuevo.getApodo());
        usuario.setNombre(nuevo.getRazonSocial());
        usuario.setTelefono(Constantes.vacio);
        usuario.setCelular(nuevo.getCelular());
        usuario.setCorreo(nuevo.getCorreo());
        usuario.setContrasena(Constantes.usuarioContrasenaDefecto);
        usuario.setConfirmarContrasena(Constantes.usuarioContrasenaDefecto);
        if(nuevo.getGenero().equals(Constantes.masculino)){
            usuario.setAvatar(Constantes.avatarMasculino);
        }
        if(nuevo.getGenero().equals(Constantes.femenino)){
            usuario.setAvatar(Constantes.avatarFemenino);
        }
        usuario.setCambiarContrasena(Constantes.no);
        usuario.setPregunta(Constantes.usuarioPreguntaDefecto);
        usuario.setRespuesta(Constantes.usuarioRespuestaDefecto);
        usuario.setEstado(Constantes.estadoActivo);
        Optional<Perfil> perfil = repPerfil.obtenerPorDescripcionYEstado(Constantes.perfilGerencial, Constantes.estadoActivo);
        if(perfil.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.perfil);
        }
        usuario.setPerfil(perfil.get());
        usuario.setEstacion(estacion);
        usuario = repUsuario.save(usuario);
        return nuevo;
    }

    @Override
    public Nuevo actualizar(Nuevo nuevo) {
        validar(nuevo);
        Nuevo res = rep.save(nuevo);
        res.normalizar();
        return res;
    }

    @Override
    public Nuevo obtener(long id) {
        Optional<Nuevo> nuevo = rep.findById(id);
        if(nuevo.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Nuevo res = nuevo.get();
        res.normalizar();
        return res;
    }

    @Override
    public List<Nuevo> consultar() {
        return rep.consultar();
    }

    private TipoIdentificacion validarIdentificacion(String identificacion) {
        if (identificacion!= null) {
            Optional<Nuevo> res = rep.obtenerPorIdentificacion(identificacion);
            if(res.isPresent()) {
                throw new EntidadExistenteException(Constantes.empresa);
            }
            TipoIdentificacion tipoIdentificacion = null;
            if (identificacion.length() != 13) {
                throw new IdentificacionInvalidaException();
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(11,13))) == 1) {
                String cedula = identificacion.substring(0,10);
                boolean bandera = Util.verificarCedula(cedula);
                if (bandera) {
                    tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.ruc_codigo_sri).get();
                    return tipoIdentificacion;
                }
                throw new IdentificacionInvalidaException();
            }
            throw new IdentificacionInvalidaException();
        }
        throw new IdentificacionInvalidaException();
    }
}
