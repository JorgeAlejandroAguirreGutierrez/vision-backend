package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.ICalificacionClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICalificacionClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CalificacionClienteService implements ICalificacionClienteService {
	@Autowired
    private ICalificacionClienteRepository rep;

    @Override
    public void validar(CalificacionCliente calificacionCliente) {
        if(calificacionCliente.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(calificacionCliente.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public CalificacionCliente crear(CalificacionCliente calificacionCliente) {
        validar(calificacionCliente);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_calificacion_cliente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	calificacionCliente.setCodigo(codigo.get());
    	calificacionCliente.setEstado(Constantes.activo);
    	return rep.save(calificacionCliente);
    }

    @Override
    public CalificacionCliente actualizar(CalificacionCliente calificacionCliente) {
        validar(calificacionCliente);
        return rep.save(calificacionCliente);
    }

    @Override
    public CalificacionCliente activar(CalificacionCliente calificacionCliente) {
        validar(calificacionCliente);
        calificacionCliente.setEstado(Constantes.activo);
        return rep.save(calificacionCliente);
    }

    @Override
    public CalificacionCliente inactivar(CalificacionCliente calificacionCliente) {
        validar(calificacionCliente);
        calificacionCliente.setEstado(Constantes.inactivo);
        return rep.save(calificacionCliente);
    }

    @Override
    public CalificacionCliente obtener(long id) {
        Optional<CalificacionCliente> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.calificacion_cliente);
    }

    @Override
    public List<CalificacionCliente> consultar() {
        return rep.consultar();
    }

    public CalificacionCliente normalizar() {
        return null;
    }

    @Override
    public List<CalificacionCliente> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }
    
    @Override
    public Page<CalificacionCliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<CalificacionCliente> buscar(CalificacionCliente calificacionCliente) {
        return rep.buscar(calificacionCliente.getCodigo(), calificacionCliente.getDescripcion(), calificacionCliente.getAbreviatura());
    }
}
