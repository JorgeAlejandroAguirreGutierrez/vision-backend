package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.repositorios.usuario.IPerfilRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PerfilService implements IPerfilService {
    @Autowired
    private IPerfilRepository rep;
    
    @Override
    public Perfil crear(Perfil perfil) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_perfil);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	perfil.setCodigo(codigo.get());
    	return rep.save(perfil);
    }

    @Override
    public Perfil actualizar(Perfil perfil) {
        return rep.save(perfil);
    }

    @Override
    public Perfil activar(Perfil perfil) {
        perfil.setEstado(Constantes.activo);
        return rep.save(perfil);
    }

    @Override
    public Perfil inactivar(Perfil perfil) {
        perfil.setEstado(Constantes.inactivo);
        return rep.save(perfil);
    }

    @Override
    public Perfil obtener(long id) {
        Optional<Perfil> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.perfil);
    }

    @Override
    public List<Perfil> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Perfil> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Perfil> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Perfil> perfiles=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,1);
            for (List<String> datos: info) {
                Perfil perfil = new Perfil(datos);
                perfiles.add(perfil);
            }
            rep.saveAll(perfiles);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
