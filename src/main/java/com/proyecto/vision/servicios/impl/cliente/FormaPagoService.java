package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.cliente.FormaPago;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.repositorios.cliente.IFormaPagoRepository;
import com.proyecto.vision.servicios.interf.cliente.IFormaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormaPagoService implements IFormaPagoService {
    @Autowired
    private IFormaPagoRepository rep;

    /**
     * Metodo que permite la validacion de las propiedades del objeto
     * @param formaPago
     */
    @Override
    public void validar(FormaPago formaPago) {
        if(formaPago.getCodigoSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.codigoSRI);
        if(formaPago.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(formaPago.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }

    /**
     * Metodo que permite la creacion del objeto
     * @param formaPago
     * @return el objeto creado
     */
    @Override
    public FormaPago crear(FormaPago formaPago) {
        validar(formaPago);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_forma_pago);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	formaPago.setCodigo(codigo.get());
    	formaPago.setEstado(Constantes.estadoActivo);
    	return rep.save(formaPago);
    }

    /**
     * Metodo que permite la actualizacion del objeto
     * @param formaPago
     * @return el objeto actualizado
     */
    @Override
    public FormaPago actualizar(FormaPago formaPago) {
        validar(formaPago);
        return rep.save(formaPago);
    }

    /**
     * Metodo que permite poner la propiedad estado en activo
     * @param formaPago
     * @return el objeto con la propiedad estado en activo
     */
    @Override
    public FormaPago activar(FormaPago formaPago) {
        validar(formaPago);
        formaPago.setEstado(Constantes.estadoActivo);
        return rep.save(formaPago);
    }

    /**
     * Metodo que permite poner la propiedad estado en inactivo
     * @param formaPago
     * @return el objeto con la propiedad estado en inactivo
     */
    @Override
    public FormaPago inactivar(FormaPago formaPago) {
        validar(formaPago);
        formaPago.setEstado(Constantes.estadoInactivo);
        return rep.save(formaPago);
    }

    /**
     * Metodo que permite obtener un objeto a traves del id
     * @param id
     * @return el objeto
     */
    @Override
    public FormaPago obtener(long id) {
        Optional<FormaPago> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.forma_pago);
    }

    /**
     * Metodo que permite consultar todos los objetos
     * @return lista de todos los objetos
     */
    @Override
    public List<FormaPago> consultar() {
        return rep.consultar();
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por el estado
     * @param estado
     * @return lista de los objetos filtrados por el estado
     */
    @Override
    public List<FormaPago> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }
}
