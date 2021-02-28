package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.repositorios.interf.usuario.IPerfilRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return rep.save(perfil);
    }

    @Override
    public Perfil actualizar(Perfil perfil) {
        return rep.save(perfil);
    }

    @Override
    public Perfil eliminar(Perfil perfil) {
        rep.deleteById(perfil.getId());
        return perfil;
    }

    @Override
    public Optional<Perfil> obtener(Perfil perfil) {
        return rep.findById(perfil.getId());
    }

    @Override
    public List<Perfil> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Perfil> perfiles=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,1);
            for (List<String> datos: info) {
                Perfil perfil = new Perfil(datos);
                perfiles.add(perfil);
            }
            if(perfiles.isEmpty()){
                return false;
            }
            rep.saveAll(perfiles);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
