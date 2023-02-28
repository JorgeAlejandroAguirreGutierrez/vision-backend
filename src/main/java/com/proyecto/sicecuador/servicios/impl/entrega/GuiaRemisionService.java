package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.sicecuador.repositorios.entrega.IGuiaRemisionRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.IGuiaRemisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GuiaRemisionService implements IGuiaRemisionService {
    @Autowired
    private IGuiaRemisionRepository rep;
    
    @Autowired
    private IUbicacionRepository repUbicacion;
    
    @Override
    public GuiaRemision crear(GuiaRemision guiaRemision) {
    	if(guiaRemision.getOpcionGuia().equals(Constantes.cliente_direccion)) {
    		if(guiaRemision.getTransportista().getId() == Constantes.cero) {
        		throw new EntidadNoExistenteException(Constantes.transportista);
        	}
    	}
    	if(guiaRemision.getOpcionGuia().equals(Constantes.nueva_direccion)) {
    		if(guiaRemision.getTransportista().getId() == Constantes.cero) {
        		throw new EntidadNoExistenteException(Constantes.transportista);
        	}
    		Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(guiaRemision.getUbicacion().getProvincia(), guiaRemision.getUbicacion().getCanton(), guiaRemision.getUbicacion().getParroquia(), Constantes.activo);
    		if(ubicacion.isEmpty()) {
    			throw new EntidadNoExistenteException(Constantes.ubicacion);
    		}
    		guiaRemision.setUbicacion(ubicacion.get());
    	}
    	if(guiaRemision.getOpcionGuia().equals(Constantes.sin_guia)) {
    		guiaRemision.setDireccion(Constantes.vacio);
    		guiaRemision.setTelefono(Constantes.vacio);
    		guiaRemision.setCelular(Constantes.vacio);
    		guiaRemision.setCorreo(Constantes.vacio);
    		guiaRemision.setReferencia(Constantes.vacio);
    		guiaRemision.setTransportista(null);
    		guiaRemision.setUbicacion(null);
    	}
		Optional<String>codigo=Util.generarCodigo(Constantes.tabla_guia_remision);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	Optional<String>guiaNumero=Util.generarGuiaNumero(Constantes.tabla_guia_remision);
    	if (guiaNumero.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	if(!guiaRemision.getOpcionGuia().equals(Constantes.sinGuia)) {
    		guiaRemision.setGuiaNumero(guiaNumero.get());
    	}
        guiaRemision.setEstado(Constantes.entregado);
    	guiaRemision.setCodigo(codigo.get());
    	return rep.save(guiaRemision);
    }

    @Override
    public GuiaRemision actualizar(GuiaRemision guiaRemision) {
        return rep.save(guiaRemision);
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
