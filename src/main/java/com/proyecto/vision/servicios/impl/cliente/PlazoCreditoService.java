package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.cliente.GrupoCliente;
import com.proyecto.vision.modelos.cliente.OrigenIngreso;
import com.proyecto.vision.modelos.cliente.PlazoCredito;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.repositorios.cliente.IPlazoCreditoRepository;
import com.proyecto.vision.servicios.interf.cliente.IPlazoCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PlazoCreditoService implements IPlazoCreditoService {
    @Autowired
    private IPlazoCreditoRepository rep;

    /**
     * Metodo que permite la validacion de las propiedades del objeto
     * @param plazoCredito
     */
    @Override
    public void validar(PlazoCredito plazoCredito) {
        if(plazoCredito.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(plazoCredito.getPlazo() == Constantes.cero) throw new DatoInvalidoException(Constantes.plazo);
    }

    /**
     * Metodo que permite la creacion del objeto
     * @param plazoCredito
     * @return el objeto creado
     */
    @Override
    public PlazoCredito crear(PlazoCredito plazoCredito) {
        validar(plazoCredito);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(null, Constantes.tabla_plazo_credito, plazoCredito.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	plazoCredito.setCodigo(codigo.get());
    	plazoCredito.setEstado(Constantes.estadoActivo);
    	return rep.save(plazoCredito);
    }

    /**
     * Metodo que permite la actualizacion del objeto
     * @param plazoCredito
     * @return el objeto actualizado
     */
    @Override
    public PlazoCredito actualizar(PlazoCredito plazoCredito) {
        validar(plazoCredito);
        return rep.save(plazoCredito);
    }

    /**
     * Metodo que permite poner la propiedad estado en activo
     * @param plazoCredito
     * @return el objeto con la propiedad estado en activo
     */
    @Override
    public PlazoCredito activar(PlazoCredito plazoCredito) {
        validar(plazoCredito);
        plazoCredito.setEstado(Constantes.estadoActivo);
        return rep.save(plazoCredito);
    }

    /**
     * Metodo que permite poner la propiedad estado en inactivo
     * @param plazoCredito
     * @return el objeto con la propiedad estado en inactivo
     */
    @Override
    public PlazoCredito inactivar(PlazoCredito plazoCredito) {
        validar(plazoCredito);
        plazoCredito.setEstado(Constantes.estadoInactivo);
        return rep.save(plazoCredito);
    }

    /**
     * Metodo que permite obtener un objeto a traves del id
     * @param id
     * @return el objeto
     */
    @Override
    public PlazoCredito obtener(long id) {
        Optional<PlazoCredito> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.plazo_credito);
    }

    /**
     * Metodo que permite consultar todos los objetos
     * @return lista de todos los objetos
     */
    @Override
    public List<PlazoCredito> consultar() {
        return rep.consultar();
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por la empresa
     * @param empresaId
     * @return lista de los objetos filtrados por la empresa
     */
    @Override
    public List<PlazoCredito> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por el estado
     * @param estado
     * @return lista de los objetos filtrados por el estado
     */
    @Override
    public List<PlazoCredito> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por la empresa y el estado
     * @param empresaId
     * @param estado
     * @return lista de los objetos filtrados por la empresa y el estado
     */
    @Override
    public List<PlazoCredito> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }
}
