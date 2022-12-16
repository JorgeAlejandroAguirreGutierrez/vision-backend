package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.EstadoCivil;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IEstadoCivilRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IEstadoCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EstadoCivilService implements IEstadoCivilService {
	
    @Autowired
    private IEstadoCivilRepository rep;
    
    @Override
    public EstadoCivil crear(EstadoCivil estadoCivil) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_estado_civil);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	estadoCivil.setCodigo(codigo.get());
    	estadoCivil.setEstado(Constantes.activo);
    	return rep.save(estadoCivil);
    }

    @Override
    public EstadoCivil actualizar(EstadoCivil estadoCivil) {
        return rep.save(estadoCivil);
    }

    @Override
    public EstadoCivil obtener(long id) {
        Optional<EstadoCivil> resp=rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.estado_civil);
    }

    @Override
    public List<EstadoCivil> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<EstadoCivil> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }
    
    @Override
    public Page<EstadoCivil> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    
    @Override
    public EstadoCivil activar(EstadoCivil estadoCivil) {
        estadoCivil.setEstado(Constantes.activo);
        return rep.save(estadoCivil);
    }

    @Override
    public EstadoCivil inactivar(EstadoCivil estadoCivil) {
        estadoCivil.setEstado(Constantes.inactivo);
        return rep.save(estadoCivil);
    }
    
    @Override
    public List<EstadoCivil> buscar(EstadoCivil estadoCivil) {
        return  rep.buscar(estadoCivil.getCodigo(), estadoCivil.getDescripcion(), estadoCivil.getAbreviatura());
    }

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<EstadoCivil> estadosCiviles=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal,8);
            for (List<String> datos: info) {
                EstadoCivil estadoCivil = new EstadoCivil(datos);
                estadosCiviles.add(estadoCivil);
            }
            rep.saveAll(estadosCiviles);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
