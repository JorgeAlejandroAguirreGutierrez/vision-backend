package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.repositorios.entrega.ITransportistaRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.ITransportistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TransportistaService implements ITransportistaService {
    @Autowired
    private ITransportistaRepository rep;
    
    @Override
    public Transportista crear(Transportista transportista) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_transportista);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	transportista.setCodigo(codigo.get());
    	transportista.setEstado(Constantes.activo);
    	return rep.save(transportista);
    }

    @Override
    public Transportista actualizar(Transportista transportista) {
        return rep.save(transportista);
    }

    @Override
    public Transportista activar(Transportista transportista) {
        transportista.setEstado(Constantes.activo);
        return rep.save(transportista);
    }

    @Override
    public Transportista inactivar(Transportista transportista) {
        transportista.setEstado(Constantes.inactivo);
        return rep.save(transportista);
    }

    @Override
    public Transportista obtener(long id) {
        Optional<Transportista> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.transportista);
        
    }

    @Override
    public List<Transportista> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Transportista> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Transportista> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
