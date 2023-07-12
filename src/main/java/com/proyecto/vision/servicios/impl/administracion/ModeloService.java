package com.proyecto.vision.servicios.impl.administracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.administracion.Modelo;
import com.proyecto.vision.repositorios.administracion.IModeloRepository;
import com.proyecto.vision.servicios.interf.administracion.IModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ModeloService implements IModeloService {
    @Autowired
    private IModeloRepository rep;
    @Override
    public Modelo crear(Modelo modelo) {
    	Optional<String>codigo = Util.generarCodigo(Constantes.tabla_modelo);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	modelo.setCodigo(codigo.get());
    	modelo.setEstado(Constantes.estadoActivo);
        return rep.save(modelo);
    }

    @Override
    public Modelo actualizar(Modelo modelo) {
        return rep.save(modelo);
    }
    
    @Override
    public Modelo activar(Modelo modelo) {
        modelo.setEstado(Constantes.estadoActivo);
        return rep.save(modelo);
    }

    @Override
    public Modelo inactivar(Modelo modelo) {
        modelo.setEstado(Constantes.estadoInactivo);
        return rep.save(modelo);
    }

    @Override
    public Modelo obtener(long id) {
        Optional<Modelo> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.modelo);
    }
    
    @Override
    public List<Modelo> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Modelo> consultarActivos() {
        return rep.consultarPorEstado(Constantes.estadoActivo);
    }

    @Override
    public Page<Modelo> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

}
