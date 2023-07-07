package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.cliente.CalificacionCliente;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.cliente.Segmento;
import com.proyecto.vision.repositorios.cliente.ICalificacionClienteRepository;
import com.proyecto.vision.servicios.interf.cliente.ICalificacionClienteService;
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
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_calificacion_cliente, calificacionCliente.getEmpresa().getId());
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

    public CalificacionCliente normalizar() { return null; }

    @Override
    public List<CalificacionCliente> consultar() {
        return rep.consultar();
    }

    @Override
    public List<CalificacionCliente> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<CalificacionCliente> consultarPorEstado(String estado) {
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<CalificacionCliente> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
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
