package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.*;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.sicecuador.repositorios.entrega.IGuiaRemisionRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.IGuiaRemisionService;
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

	@Override
	public void validar(GuiaRemision guiaRemision) {
		if(guiaRemision.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
		if(guiaRemision.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
		if(guiaRemision.getTransportista().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.transportista);
		if(guiaRemision.getFactura().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.factura);
		if(guiaRemision.getMotivoTraslado().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.motivoTraslado);
		if(guiaRemision.getRuta().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.ruta);
		if(guiaRemision.getFechaInicioTransporte() == null) throw new DatoInvalidoException(Constantes.fechaInicioTransporte);
		if(guiaRemision.getFechaFinTransporte() == null) throw new DatoInvalidoException(Constantes.fechaFinTransporte);
	}

	private Optional<String> crearClaveAcceso(GuiaRemision guiaRemision) {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String fechaEmision = dateFormat.format(guiaRemision.getFecha());
		String tipoComprobante = Constantes.guia_de_remision_sri;
		String numeroRuc = guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
		String tipoAmbiente = Constantes.pruebas_sri;
		String serie = guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + guiaRemision.getSesion().getUsuario().getEstacion().getCodigoSRI();
		String numeroComprobante = guiaRemision.getSecuencia();
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
		Optional<String>codigo = Util.generarCodigo(Constantes.tabla_guia_remision);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
		guiaRemision.setCodigo(codigo.get());
		Optional<String>secuencia=Util.generarSecuencia(Constantes.tabla_guia_remision);
		if (secuencia.isEmpty()) {
			throw new SecuenciaNoExistenteException();
		}
		guiaRemision.setSecuencia(secuencia.get());
		Optional<String> codigoNumerico = Util.generarCodigoNumerico(Constantes.tabla_guia_remision);
		if (codigoNumerico.isEmpty()) {
			throw new CodigoNumericoNoExistenteException();
		}
		guiaRemision.setCodigoNumerico(codigoNumerico.get());
		Optional<String> claveAcceso = crearClaveAcceso(guiaRemision);
		if (claveAcceso.isEmpty()) {
			throw new ClaveAccesoNoExistenteException();
		}
		guiaRemision.setClaveAcceso(claveAcceso.get());
        guiaRemision.setEstado(Constantes.estadoEmitida);
		GuiaRemision res = rep.save(guiaRemision);
		res.normalizar();
    	return rep.save(guiaRemision);
    }

    @Override
    public GuiaRemision actualizar(GuiaRemision guiaRemision) {
		if(guiaRemision.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        validar(guiaRemision);
		return rep.save(guiaRemision);
    }

	@Override
	public GuiaRemision activar(GuiaRemision guiaRemision) {
		validar(guiaRemision);
		guiaRemision.setEstado(Constantes.activo);
		GuiaRemision res = rep.save(guiaRemision);
		res.normalizar();
		return res;
	}

	@Override
	public GuiaRemision inactivar(GuiaRemision guiaRemision) {
		validar(guiaRemision);
		guiaRemision.setEstado(Constantes.inactivo);
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
        return rep.findAll();
    }

    @Override
    public Page<GuiaRemision> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
