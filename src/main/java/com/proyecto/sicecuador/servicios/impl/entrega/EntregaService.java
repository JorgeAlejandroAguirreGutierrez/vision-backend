package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.entrega.Entrega;
import com.proyecto.sicecuador.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.sicecuador.repositorios.entrega.IEntregaRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.IEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EntregaService implements IEntregaService {
    @Autowired
    private IEntregaRepository rep;
    
    @Autowired
    private IUbicacionRepository repUbicacion;
    
    @Override
    public Entrega crear(Entrega entrega) {
    	if(entrega.getOpcionGuia().equals(Constantes.cliente_direccion)) {
    		if(entrega.getTransportista().getId() == Constantes.cero) {
        		throw new EntidadNoExistenteException(Constantes.transportista);
        	}
    	}
    	if(entrega.getOpcionGuia().equals(Constantes.nueva_direccion)) {
    		if(entrega.getTransportista().getId() == Constantes.cero) {
        		throw new EntidadNoExistenteException(Constantes.transportista);
        	}
    		Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(entrega.getUbicacion().getProvincia(), entrega.getUbicacion().getCanton(), entrega.getUbicacion().getParroquia(), Constantes.activo);
    		if(ubicacion.isEmpty()) {
    			throw new EntidadNoExistenteException(Constantes.ubicacion);
    		}
    		entrega.setUbicacion(ubicacion.get());
    	}
    	if(entrega.getOpcionGuia().equals(Constantes.sin_guia)) {
    		entrega.setDireccion(Constantes.vacio);
    		entrega.setTelefono(Constantes.vacio);
    		entrega.setCelular(Constantes.vacio);
    		entrega.setCorreo(Constantes.vacio);
    		entrega.setReferencia(Constantes.vacio);
    		entrega.setTransportista(null);
    		entrega.setUbicacion(null);
    	}
		Optional<String>codigo=Util.generarCodigo(Constantes.tabla_entrega);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	Optional<String>guiaNumero=Util.generarGuiaNumero(Constantes.tabla_entrega);
    	if (guiaNumero.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	if(!entrega.getOpcionGuia().equals(Constantes.sinGuia)) {
    		entrega.setGuiaNumero(guiaNumero.get());
    	}
        entrega.setEstado(Constantes.entregado);
    	entrega.setCodigo(codigo.get());
    	return rep.save(entrega);
    }

    @Override
    public Entrega actualizar(Entrega entrega) {
        return rep.save(entrega);
    }

    @Override
    public Entrega obtener(long id) {
        Optional<Entrega> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.entrega);
    }
    
    @Override
    public Optional<Entrega> obtenerPorFactura(long facturaId){
    	return rep.obtenerPorFactura(facturaId);
    }

    @Override
    public List<Entrega> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Entrega> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Entrega> guias_remisiones=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,0);
            for (List<String> datos: info) {
                Entrega guia_remision = new Entrega(datos);
                guias_remisiones.add(guia_remision);
            }
            rep.saveAll(guias_remisiones);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
