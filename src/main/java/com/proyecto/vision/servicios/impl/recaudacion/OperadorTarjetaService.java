package com.proyecto.vision.servicios.impl.recaudacion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.vision.repositorios.recaudacion.IOperadorTarjetaRepository;
import com.proyecto.vision.servicios.interf.recaudacion.IOperadorTarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperadorTarjetaService implements IOperadorTarjetaService {
    @Autowired
    private IOperadorTarjetaRepository rep;

    @Override
    public void validar(OperadorTarjeta operadorTarjeta) {
        if(operadorTarjeta.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(operadorTarjeta.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(operadorTarjeta.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public OperadorTarjeta crear(OperadorTarjeta operadorTarjeta) {
        validar(operadorTarjeta);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_operador_tarjeta);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	operadorTarjeta.setCodigo(codigo.get());
    	operadorTarjeta.setEstado(Constantes.estadoActivo);
    	return rep.save(operadorTarjeta);
    }

    @Override
    public OperadorTarjeta actualizar(OperadorTarjeta operadorTarjeta) {
        validar(operadorTarjeta);
        return rep.save(operadorTarjeta);
    }

    @Override
    public OperadorTarjeta activar(OperadorTarjeta operadorTarjeta) {
        validar(operadorTarjeta);
        operadorTarjeta.setEstado(Constantes.estadoActivo);
        return rep.save(operadorTarjeta);
    }

    @Override
    public OperadorTarjeta inactivar(OperadorTarjeta operadorTarjeta) {
        validar(operadorTarjeta);
        operadorTarjeta.setEstado(Constantes.estadoInactivo);
        return rep.save(operadorTarjeta);
    }

    @Override
    public OperadorTarjeta obtener(long id) {
        Optional<OperadorTarjeta> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.operador_tarjeta);
    }

    @Override
    public List<OperadorTarjeta> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<OperadorTarjeta> consultarPorEstado(String estado) {
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<OperadorTarjeta> consultarPorTipo(String tipo) {
        return rep.consultarPorTipo(tipo, Constantes.estadoActivo);
    }
}
