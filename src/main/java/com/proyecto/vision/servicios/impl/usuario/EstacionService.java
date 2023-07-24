package com.proyecto.vision.servicios.impl.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.usuario.Estacion;
import com.proyecto.vision.repositorios.usuario.IEstacionRepository;
import com.proyecto.vision.servicios.interf.usuario.IEstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class EstacionService implements IEstacionService {
    @Autowired
    private IEstacionRepository rep;

    @Override
    public void validar(Estacion estacion) {
        if(estacion.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(estacion.getEstablecimiento().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.establecimiento);
        if(estacion.getRegimen().getId() == Constantes.ceroId) estacion.setRegimen(null);
    }
    
    @Override
    public Estacion crear(Estacion estacion) {
        validar(estacion);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_estacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	estacion.setCodigo(codigo.get());
    	estacion.setEstado(Constantes.estadoActivo);
    	Estacion res = rep.save(estacion);
        res.normalizar();
        return res;
    }

    @Override
    public Estacion actualizar(Estacion estacion) {
        validar(estacion);
        Estacion res = rep.save(estacion);
        res.normalizar();
        return res;
    }

    @Override
    public Estacion activar(Estacion estacion) {
        validar(estacion);
        estacion.setEstado(Constantes.estadoActivo);
        Estacion res = rep.save(estacion);
        res.normalizar();
        return res;
    }

    @Override
    public Estacion inactivar(Estacion estacion) {
        validar(estacion);
        estacion.setEstado(Constantes.estadoInactivo);
        Estacion res = rep.save(estacion);
        res.normalizar();
        return res;
    }

    @Override
    public Estacion obtener(long id) {
        Optional<Estacion> estacion = rep.findById(id);
        if(estacion.isPresent()) {
        	Estacion res = estacion.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.estacion);
    }

    @Override
    public List<Estacion> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Estacion> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Estacion> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Estacion> consultarPorEstablecimiento(long establecimientoId) {
        return rep.consultarPorEstablecimiento(establecimientoId, Constantes.estadoActivo);
    }
    @Override
    public List<Estacion> consultarEstacionesPorEstablecimiento(long establecimientoId) {
        return rep.consultarEstacionesPorEstablecimiento(establecimientoId, Constantes.estadoActivo);
    }
    @Override
    public List<Estacion> consultarPuntosVentaPorEstablecimiento(long establecimientoId) {
        return rep.consultarPuntosVentaPorEstablecimiento(establecimientoId, Constantes.si, Constantes.estadoActivo);
    }

    @Override
    public Page<Estacion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
