package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IOrigenIngresoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IOrigenIngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class OrigenIngresoService implements IOrigenIngresoService {
    @Autowired
    private IOrigenIngresoRepository rep;

    @Override
    public void validar(OrigenIngreso origenIngreso) {
        if(origenIngreso.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(origenIngreso.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public OrigenIngreso crear(OrigenIngreso origenIngreso) {
        validar(origenIngreso);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_origen_ingreso);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	origenIngreso.setCodigo(codigo.get());
    	origenIngreso.setEstado(Constantes.activo);
    	return rep.save(origenIngreso);
    }

    @Override
    public OrigenIngreso actualizar(OrigenIngreso origenIngreso) {
        validar(origenIngreso);
        return rep.save(origenIngreso);
    }

    @Override
    public OrigenIngreso activar(OrigenIngreso origenIngreso) {
        validar(origenIngreso);
        origenIngreso.setEstado(Constantes.activo);
        return rep.save(origenIngreso);
    }

    @Override
    public OrigenIngreso inactivar(OrigenIngreso origenIngreso) {
        validar(origenIngreso);
        origenIngreso.setEstado(Constantes.inactivo);
        return rep.save(origenIngreso);
    }

    @Override
    public OrigenIngreso obtener(long id) {
        Optional<OrigenIngreso> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.origen_ingreso);
    }

    @Override
    public List<OrigenIngreso> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<OrigenIngreso> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }
    
    @Override
    public Page<OrigenIngreso> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<OrigenIngreso> buscar(OrigenIngreso origenIngreso) {
        return  rep.buscar(origenIngreso.getCodigo(), origenIngreso.getDescripcion(), origenIngreso.getAbreviatura());
    }
}
