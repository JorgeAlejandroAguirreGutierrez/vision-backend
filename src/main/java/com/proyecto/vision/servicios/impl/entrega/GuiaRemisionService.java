package com.proyecto.vision.servicios.impl.entrega;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.entrega.GuiaRemision;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.vision.repositorios.entrega.IGuiaRemisionRepository;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.entrega.IGuiaRemisionService;
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
public class GuiaRemisionService implements IGuiaRemisionService {
	@Autowired
	private IGuiaRemisionRepository rep;
	@Autowired
	private IUbicacionRepository repUbicacion;
	@Autowired
	private ITipoComprobanteService tipoComprobanteService;
	@Autowired
	private ISecuencialService secuencialService;

	@Override
	public void validar(GuiaRemision guiaRemision) {
		if(guiaRemision.getEstado().equals(Constantes.estadoAnulada)) throw new EstadoInvalidoException(Constantes.estadoAnulada);
		if(guiaRemision.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)) throw new EstadoInvalidoException(Constantes.procesoSRIAutorizada);
		if(guiaRemision.getProcesoSRI().equals(Constantes.procesoSRIAnulada)) throw new EstadoInvalidoException(Constantes.procesoSRIAnulada);
		if(guiaRemision.getEmpresa().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
		if(guiaRemision.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
		if(guiaRemision.getUsuario().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
		if(guiaRemision.getTransportista().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.transportista);
		if(guiaRemision.getFactura().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.factura);
		if(guiaRemision.getMotivoTraslado().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.motivoTraslado);
		if(guiaRemision.getRuta().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.ruta);
		if(guiaRemision.getFechaInicioTransporte() == null) throw new DatoInvalidoException(Constantes.fechaInicioTransporte);
		if(guiaRemision.getFechaFinTransporte() == null) throw new DatoInvalidoException(Constantes.fechaFinTransporte);
		if(guiaRemision.getOpcionGuia().equals(Constantes.nueva_direccion)){
			if(guiaRemision.getIdentificacionDestinatario().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
			if(guiaRemision.getRazonSocialDestinatario().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.razonSocial);
			if(guiaRemision.getDireccionDestinatario().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
			if(guiaRemision.getTelefonoDestinatario().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.telefono);
			if(guiaRemision.getCelularDestinatario().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.celular);
			if(guiaRemision.getCorreoDestinatario().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.correo);
		}
	}

	private Optional<String> crearClaveAcceso(GuiaRemision guiaRemision) {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String fechaEmision = dateFormat.format(guiaRemision.getFecha());
		String tipoComprobante = Constantes.guia_de_remision_sri;
		String numeroRuc = guiaRemision.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
		String tipoAmbiente = Constantes.pruebas_sri;
		String serie = guiaRemision.getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + guiaRemision.getUsuario().getEstacion().getCodigoSRI();
		String numeroComprobante = guiaRemision.getSecuencial();
		String codigoNumerico = guiaRemision.getCodigoNumerico();
		String tipoEmision = Constantes.emision_normal_sri;
		String cadenaVerificacion = fechaEmision + tipoComprobante+numeroRuc+tipoAmbiente+serie+numeroComprobante + codigoNumerico + tipoEmision;
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
	public GuiaRemision crear(GuiaRemision guiaRemision) {
		validar(guiaRemision);
		Optional<GuiaRemision> guiaRemisionExiste = rep.obtenerPorFactura(guiaRemision.getFactura().getId());
		if(guiaRemisionExiste.isPresent()){
			throw new EntidadExistenteException(Constantes.guia_remision);
		}
		TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_guia_remision);
		guiaRemision.setTipoComprobante(tipoComprobante);
		Optional<String>codigo = Util.generarCodigoPorEmpresa(guiaRemision.getFecha(), Constantes.tabla_guia_remision, guiaRemision.getEmpresa().getId());
		if (codigo.isEmpty()) {
			throw new CodigoNoExistenteException();
		}
		guiaRemision.setCodigo(codigo.get());
		Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(guiaRemision.getTipoComprobante().getId(),
				guiaRemision.getUsuario().getEstacion().getId(), guiaRemision.getEmpresa().getId(), Constantes.estadoActivo);
		guiaRemision.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
		guiaRemision.setNumeroComprobante(guiaRemision.getEstablecimiento() + Constantes.guion + guiaRemision.getPuntoVenta() + Constantes.guion + guiaRemision.getSecuencial());
		guiaRemision.setCodigoNumerico(Util.generarCodigoNumerico(secuencial.getNumeroSiguiente()));
		Optional<String> claveAcceso = crearClaveAcceso(guiaRemision);
		if (claveAcceso.isEmpty()) {
			throw new ClaveAccesoNoExistenteException();
		}
		guiaRemision.setClaveAcceso(claveAcceso.get());
		guiaRemision.setEstado(Constantes.estadoEmitida);
		guiaRemision.setProcesoSRI(Constantes.procesoSRIPendiente);
		GuiaRemision res = rep.save(guiaRemision);
		res.normalizar();
		secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
		secuencialService.actualizar(secuencial);
		return res;
	}

	@Override
	public GuiaRemision actualizar(GuiaRemision guiaRemision) {
		validar(guiaRemision);
		return rep.save(guiaRemision);
	}

	@Override
	public GuiaRemision anular(GuiaRemision guiaRemision) {
		validar(guiaRemision);
		guiaRemision.setEstado(Constantes.estadoAnulada);
		guiaRemision.setProcesoSRI(Constantes.procesoSRIAnulada);
		GuiaRemision res = rep.save(guiaRemision);
		res.normalizar();
		return res;
	}

	@Override
	public GuiaRemision obtener(long id) {
		Optional<GuiaRemision> res= rep.findById(id);
		if(res.isPresent()) {
			return res.get();
		}
		throw new EntidadNoExistenteException(Constantes.entrega);
	}

	@Override
	public Optional<GuiaRemision> obtenerPorFactura(long facturaId){
		return rep.obtenerPorFactura(facturaId);
	}

	@Override
	public List<GuiaRemision> consultar() {
		return rep.consultar();
	}

	@Override
	public List<GuiaRemision> consultarPorEmpresa(long empresaId){
		return rep.consultarPorEmpresa(empresaId);
	}

	@Override
	public List<GuiaRemision> consultarPorEmpresaYEstado(long empresaId, String estado){
		return rep.consultarPorEmpresaYEstado(empresaId, estado);
	}

	@Override
	public List<GuiaRemision> consultarPorFacturaYEmpresaYEstado(long facturaId, long empresaId, String estado){
		return rep.consultarPorFacturaYEmpresaYEstado(facturaId, empresaId, estado);
	}

	@Override
	public List<GuiaRemision> consultarPorFacturaYEmpresaYEstadoDiferente(long facturaId, long empresaId, String estado){
		return rep.consultarPorFacturaYEmpresaYEstadoDiferente(facturaId, empresaId, estado);
	}

}