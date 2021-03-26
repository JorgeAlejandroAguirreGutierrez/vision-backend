package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.repositorios.interf.usuario.ISesionRepository;
import com.proyecto.sicecuador.repositorios.interf.usuario.IUsuarioRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.ISesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class SesionService implements ISesionService {
    @Autowired
    private ISesionRepository rep;
    @Autowired
    private static IParametroRepository parametroRep;
    
    @Autowired
    private IUsuarioRepository rep_usuario;
    @Override
    public Sesion crear(Sesion sesion) {
        Usuario usuario=rep_usuario.findByIdentificacionContrasena(sesion.getUsuario().getIdentificacion(),sesion.getUsuario().getContrasena());
        sesion.setUsuario(usuario);
        //sesion.setFecha_apertura(new Date());
        //sesion.setActiva(true);
        return rep.save(sesion);
    }

    @Override
    public Sesion actualizar(Sesion sesion) {
        return rep.save(sesion);
    }

    @Override
    public Sesion eliminar(Sesion sesion) {
        rep.deleteById(sesion.getId());
        return sesion;
    }

    @Override
    public Optional<Sesion> obtener(Sesion sesion) {
        return rep.findById(sesion.getId());
    }

    @Override
    public List<Sesion> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Sesion> sesiones=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,4);
            for (List<String> datos: info) {
                Sesion sesion = new Sesion(datos);
                sesiones.add(sesion);
            }
            if(sesiones.isEmpty()){
                return false;
            }
            rep.saveAll(sesiones);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Optional<Sesion> verificar(Sesion sesion) {
        Sesion _sesion= rep.findById(sesion.getId()).get();
        if (_sesion.getFechaCierre()==null){
            return Optional.of(_sesion);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Sesion> cerrar(Sesion sesion) {
        Sesion _sesion=rep.findById(sesion.getId()).get();
        sesion.setFechaCierre(new Date());
        sesion.setActiva(false);
        _sesion=rep.save(_sesion);
        return Optional.of(_sesion);
    }
}
