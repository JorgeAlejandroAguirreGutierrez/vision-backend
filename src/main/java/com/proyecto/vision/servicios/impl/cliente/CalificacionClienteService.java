package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.cliente.CalificacionCliente;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.repositorios.cliente.ICalificacionClienteRepository;
import com.proyecto.vision.servicios.interf.cliente.ICalificacionClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class CalificacionClienteService implements ICalificacionClienteService {

	@Autowired
    private ICalificacionClienteRepository rep;

    /**
     * Metodo que permite la validacion de las propiedades del objeto
     * @param calificacionCliente
     */
    @Override
    public void validar(CalificacionCliente calificacionCliente) {
        if(calificacionCliente.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(calificacionCliente.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }

    /**
     * Metodo que permite la creacion del objeto
     * @param calificacionCliente
     * @return el objeto creado
     */
    @Override
    public CalificacionCliente crear(CalificacionCliente calificacionCliente) {
        validar(calificacionCliente);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(null, Constantes.tabla_calificacion_cliente, calificacionCliente.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	calificacionCliente.setCodigo(codigo.get());
    	calificacionCliente.setEstado(Constantes.estadoActivo);
    	return rep.save(calificacionCliente);
    }

    /**
     * Metodo que permite la actualizacion del objeto
     * @param calificacionCliente
     * @return el objeto actualizado
     */
    @Override
    public CalificacionCliente actualizar(CalificacionCliente calificacionCliente) {
        validar(calificacionCliente);
        return rep.save(calificacionCliente);
    }

    /**
     * Metodo que permite poner la propiedad estado en activo
     * @param calificacionCliente
     * @return el objeto con la propiedad estado en activo
     */
    @Override
    public CalificacionCliente activar(CalificacionCliente calificacionCliente) {
        validar(calificacionCliente);
        calificacionCliente.setEstado(Constantes.estadoActivo);
        return rep.save(calificacionCliente);
    }

    /**
     * Metodo que permite poner la propiedad estado en inactivo
     * @param calificacionCliente
     * @return el objeto con la propiedad estado en inactivo
     */
    @Override
    public CalificacionCliente inactivar(CalificacionCliente calificacionCliente) {
        validar(calificacionCliente);
        calificacionCliente.setEstado(Constantes.estadoInactivo);
        return rep.save(calificacionCliente);
    }

    /**
     * Metodo que permite obtener un objeto a traves del id
     * @param id
     * @return el objeto segun id
     */
    @Override
    public CalificacionCliente obtener(long id) {
        Optional<CalificacionCliente> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.calificacion_cliente);
    }

    /**
     * Metodo que permite consultar todos los objetos
     * @return lista de todos los objetos
     */
    @Override
    public List<CalificacionCliente> consultar() {
        return rep.consultar();
    }

    /**
     * Metodo que permite consultar los objetos filtrando por la empresa
     * @param empresaId
     * @return lista de los objetos consultados filtrando por la empresa
     */
    @Override
    public List<CalificacionCliente> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    /**
     * Metodo que permite consultar los objetos filtrando por el estado
     * @param estado
     * @return lista de los objetos consultados filtrando por el estado
     */
    @Override
    public List<CalificacionCliente> consultarPorEstado(String estado) {
        return rep.consultarPorEstado(estado);
    }

    /**
     * Metodo que permite consultar los objetos filtrando por la empresa y el estado
     * @param empresaId
     * @param estado
     * @return lista de los objetos consultados filtrando por la empresa y el estado
     */
    @Override
    public List<CalificacionCliente> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    /**
     * Metodo que permite consultar los objetos por pagina
     * @param pageable
     * @return
     */
    @Override
    public Page<CalificacionCliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
