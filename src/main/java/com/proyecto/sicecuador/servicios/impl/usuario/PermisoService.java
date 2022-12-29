package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.Permiso;
import com.proyecto.sicecuador.repositorios.usuario.IPermisoRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IPermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    public List<Permiso> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Permiso> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Permiso> permisos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,2);
            for (List<String> datos: info) {
                Permiso permiso = new Permiso(datos);
                permisos.add(permiso);
            }
            rep.saveAll(permisos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
