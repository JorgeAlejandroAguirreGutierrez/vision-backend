package com.proyecto.vision.servicios.impl.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.modelos.usuario.Paquete;
import com.proyecto.vision.modelos.usuario.Suscripcion;
import com.proyecto.vision.repositorios.usuario.IPaqueteRepository;
import com.proyecto.vision.servicios.interf.usuario.IPaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaqueteService implements IPaqueteService {
    @Autowired
    private IPaqueteRepository rep;

    @Override
    public void validar(Paquete paquete) {
        if(paquete.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
    }

    @Override
    public Paquete crear(Paquete paquete) {
        validar(paquete);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_empresa);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        paquete.setCodigo(codigo.get());
        paquete.setEstado(Constantes.estadoActivo);
        Paquete res = rep.save(paquete);
        res.normalizar();
        return res;
    }

    @Override
    public Paquete actualizar(Paquete paquete) {
        validar(paquete);
        Paquete res = rep.save(paquete);
        res.normalizar();
        return res;
    }

    @Override
    public Paquete activar(Paquete paquete) {
        validar(paquete);
        paquete.setEstado(Constantes.estadoActivo);
        Paquete res = rep.save(paquete);
        res.normalizar();
        return res;
    }

    @Override
    public Paquete inactivar(Paquete paquete) {
        validar(paquete);
        paquete.setEstado(Constantes.estadoInactivo);
        Paquete res = rep.save(paquete);
        res.normalizar();
        return res;
    }

    @Override
    public Paquete obtener(long id) {
        Optional<Paquete> paquete = rep.findById(id);
        if(paquete.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Paquete res = paquete.get();
        res.normalizar();
        return res;
    }

    @Override
    public List<Paquete> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Paquete> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<Paquete> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
