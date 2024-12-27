package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.cliente.Segmento;
import com.proyecto.vision.repositorios.cliente.ISegmentoRepository;
import com.proyecto.vision.servicios.interf.cliente.ISegmentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

@Service
public class SegmentoService implements ISegmentoService {
    @Autowired
    private ISegmentoRepository rep;

    /**
     * Metodo que permite la validacion de las propiedades del objeto
     * @param segmento
     */
    @Override
    public void validar(Segmento segmento) {
        if(segmento.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(segmento.getMargenGanancia() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.margenGanancia);
        if(segmento.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }

    /**
     * Metodo que permite la creacion del objeto
     * @param segmento
     * @return
     */
    @Override
    public Segmento crear(Segmento segmento) {
        validar(segmento);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(null, Constantes.tabla_segmento, segmento.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	segmento.setCodigo(codigo.get());
    	segmento.setEstado(Constantes.estadoActivo);
    	return rep.save(segmento);
    }

    /**
     * Metodo que permite la actualizacion del objeto
     * @param segmento
     * @return el objeto actualizado
     */
    @Override
    public Segmento actualizar(Segmento segmento) {
        validar(segmento);
        return rep.save(segmento);
    }

    /**
     * Metodo que permite poner la propiedad estado en activo
     * @param segmento
     * @return el objeto con la propiedad estado en activo
     */
    @Override
    public Segmento activar(Segmento segmento) {
        validar(segmento);
        segmento.setEstado(Constantes.estadoActivo);
        return rep.save(segmento);
    }

    /**
     * Metodo que permite poner la propiedad estado en inactivo
     * @param segmento
     * @return el objeto con la propiedad estado en inactivo
     */
    @Override
    public Segmento inactivar(Segmento segmento) {
        validar(segmento);
        segmento.setEstado(Constantes.estadoInactivo);
        return rep.save(segmento);
    }

    /**
     * Metodo que permite obtener un objeto a traves del id
     * @param id
     * @return el objeto
     */
    @Override
    public Segmento obtener(long id) {
        Optional<Segmento> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.segmento);
    }

    /**
     * Metodo que permite consultar todos los objetos
     * @return lista de todos los objetos
     */
    @Override
    public List<Segmento> consultar() {
        return rep.consultar();
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por la empresa
     * @param empresaId
     * @return lista de los objetos filtrados por la empresa
     */
    @Override
    public List<Segmento> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por el estado
     * @param estado
     * @return lista de los objetos filtrados por el estado
     */
    @Override
    public List<Segmento> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por la empresa y el estado
     * @param empresaId
     * @param estado
     * @return lista de los objetos filtrados por la empresa y el estado
     */
    @Override
    public List<Segmento> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }
}
