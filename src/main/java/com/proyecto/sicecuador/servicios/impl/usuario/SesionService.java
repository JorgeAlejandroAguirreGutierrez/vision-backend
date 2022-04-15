package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.ModeloNoExistenteException;
import com.proyecto.sicecuador.exception.SesionInvalidaException;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.usuario.ISesionRepository;
import com.proyecto.sicecuador.repositorios.usuario.IUsuarioRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.ISesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private IUsuarioRepository rep_usuario;
    
    @Override
    public Sesion crear(Sesion sesion) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_sesion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	sesion.setCodigo(codigo.get());
    	Optional<Usuario> usuario=rep_usuario.findByIdentificacionContrasena(sesion.getUsuario().getIdentificacion(),sesion.getUsuario().getContrasena());
    	if(usuario.isPresent()) {
    		sesion.setUsuario(usuario.get());
            sesion.setFechaApertura(new Date());
            sesion.setActiva(true);
            return rep.save(sesion);
    	}
    	throw new ModeloNoExistenteException(); 
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
    public Page<Sesion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    
    /**
     * Valida la sesion del usuario por id
     * @param Sesion
     * @return Sesion
     */
    @Override
    public Optional<Sesion> validar(Sesion _sesion) {
        final Optional<Sesion> sesion= rep.findById(_sesion.getId());
        if (sesion.isPresent()) {
        	long startTime = sesion.get().getFechaApertura().getTime();
			long endTime = new Date().getTime();
			long diffTime = endTime - startTime;
			long diffDays = diffTime / (1000 * 60 * 60 * 24);
			if(diffDays<1) {
				return sesion;
			}
			throw new SesionInvalidaException();
        }
        throw new ModeloNoExistenteException();
    }
    
    @Override
    public Optional<Sesion> cerrar(Sesion sesion) {
        Sesion _sesion=rep.findById(sesion.getId()).get();
        sesion.setFechaCierre(new Date());
        sesion.setActiva(false);
        _sesion=rep.save(_sesion);
        return Optional.of(_sesion);
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
}
