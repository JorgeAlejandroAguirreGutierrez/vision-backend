package com.proyecto.vision.servicios.impl.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.usuario.Perfil;
import com.proyecto.vision.modelos.usuario.Permiso;
import com.proyecto.vision.repositorios.usuario.IPermisoRepository;
import com.proyecto.vision.servicios.interf.usuario.IPermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PermisoService implements IPermisoService {
    @Autowired
    private IPermisoRepository rep;
    
    @Override
    public Permiso crear(Permiso permiso) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_permiso);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	permiso.setCodigo(codigo.get());
    	return rep.save(permiso);
    }

    @Override
    public Permiso actualizar(Permiso permiso) {
        return rep.save(permiso);
    }

    @Override
    public Permiso obtener(long id) {
        Optional<Permiso> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.permiso);
    }

    @Override
    public Permiso activar(Permiso permiso) {
        permiso.setEstado(Constantes.activo);
        Permiso res = rep.save(permiso);
        return res;
    }

    @Override
    public Permiso inactivar(Permiso permiso) {
        permiso.setEstado(Constantes.inactivo);
        Permiso res = rep.save(permiso);
        return res;
    }

    @Override
    public List<Permiso> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Permiso> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<Permiso> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}