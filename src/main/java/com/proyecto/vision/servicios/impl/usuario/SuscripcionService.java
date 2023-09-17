package com.proyecto.vision.servicios.impl.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.usuario.Suscripcion;
import com.proyecto.vision.repositorios.usuario.ISuscripcionRepository;
import com.proyecto.vision.servicios.interf.usuario.ISuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SuscripcionService implements ISuscripcionService {
    @Autowired
    private ISuscripcionRepository rep;

    @Override
    public void validar(Suscripcion suscripcion) {
        if(suscripcion.getPaquete().getId() ==  Constantes.ceroId) throw new DatoInvalidoException(Constantes.paquete);
        if(suscripcion.getEmpresa().getId() ==  Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
    }

    @Override
    public Suscripcion crear(Suscripcion suscripcion) {
        validar(suscripcion);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_empresa);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        suscripcion.setCodigo(codigo.get());
        suscripcion.setEstado(Constantes.estadoActivo);
    	Suscripcion res = rep.save(suscripcion);
        res.normalizar();
        return res;
    }

    @Override
    public Suscripcion actualizar(Suscripcion suscripcion) {
        validar(suscripcion);
        Suscripcion res = rep.save(suscripcion);
        res.normalizar();
        return res;
    }

    @Override
    public Suscripcion activar(Suscripcion suscripcion) {
        validar(suscripcion);
        suscripcion.setEstado(Constantes.estadoActivo);
        Suscripcion res = rep.save(suscripcion);
        res.normalizar();
        return res;
    }

    @Override
    public Suscripcion inactivar(Suscripcion suscripcion) {
        validar(suscripcion);
        suscripcion.setEstado(Constantes.estadoInactivo);
        Suscripcion res = rep.save(suscripcion);
        res.normalizar();
        return res;
    }

    @Override
    public Suscripcion obtener(long id) {
        Optional<Suscripcion> suscripcion = rep.findById(id);
        if(suscripcion.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Suscripcion res = suscripcion.get();
        res.normalizar();
        return res;
    }

    @Override
    public List<Suscripcion> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Suscripcion> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Suscripcion> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<Suscripcion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
