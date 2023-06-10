package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IFormaPagoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IFormaPagoService;
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

    @Override
    public void validar(FormaPago formaPago) {
        if(formaPago.getCodigoSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.codigoSRI);
        if(formaPago.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(formaPago.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public FormaPago crear(FormaPago formaPago) {
        validar(formaPago);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_forma_pago);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	formaPago.setCodigo(codigo.get());
    	formaPago.setEstado(Constantes.activo);
    	return rep.save(formaPago);
    }

    @Override
    public FormaPago actualizar(FormaPago formaPago) {
        validar(formaPago);
        return rep.save(formaPago);
    }

    @Override
    public FormaPago activar(FormaPago formaPago) {
        validar(formaPago);
        formaPago.setEstado(Constantes.activo);
        return rep.save(formaPago);
    }

    @Override
    public FormaPago inactivar(FormaPago formaPago) {
        validar(formaPago);
        formaPago.setEstado(Constantes.inactivo);
        return rep.save(formaPago);
    }

    @Override
    public FormaPago obtener(long id) {
        Optional<FormaPago> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.forma_pago);
    }

    @Override
    public List<FormaPago> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<FormaPago> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<FormaPago> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<FormaPago> buscar(FormaPago formaPago) {
        return  rep.buscar(formaPago.getCodigo(), formaPago.getDescripcion(), formaPago.getAbreviatura());
    }
}
