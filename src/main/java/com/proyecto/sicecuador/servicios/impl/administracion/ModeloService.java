package com.proyecto.sicecuador.servicios.impl.administracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.administracion.Modelo;
import com.proyecto.sicecuador.repositorios.administracion.IModeloRepository;
import com.proyecto.sicecuador.servicios.interf.administracion.IModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class ModeloService implements IModeloService {
    @Autowired
    private IModeloRepository rep;
    @Override
    public Modelo crear(Modelo modelo) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_modelo);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	modelo.setCodigo(codigo.get());
    	modelo.setEstado(Constantes.activo);
        return rep.save(modelo);
    }

    @Override
    public Modelo actualizar(Modelo modelo) {
        return rep.save(modelo);
    }
    
    @Override
    public Modelo activar(Modelo modelo) {
        modelo.setEstado(Constantes.activo);
        return rep.save(modelo);
    }

    @Override
    public Modelo inactivar(Modelo modelo) {
        modelo.setEstado(Constantes.inactivo);
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
        return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Modelo> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

}
