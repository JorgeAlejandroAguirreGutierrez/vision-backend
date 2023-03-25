package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.repositorios.inventario.IMedidaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MedidaService implements IMedidaService {
    @Autowired
    private IMedidaRepository rep;

    @Override
    public void validar(Medida medida) {
        if(medida.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(medida.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(medida.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public Medida crear(Medida medida) {
        validar(medida);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_medida);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	medida.setCodigo(codigo.get());
    	medida.setEstado(Constantes.activo);
    	return rep.save(medida);
    }

    @Override
    public Medida actualizar(Medida medida) {
        validar(medida);
        return rep.save(medida);
    }

    @Override
    public Medida activar(Medida medida) {
        validar(medida);
        medida.setEstado(Constantes.activo);
        return rep.save(medida);
    }

    @Override
    public Medida inactivar(Medida medida) {
        validar(medida);
        medida.setEstado(Constantes.inactivo);
        return rep.save(medida);
    }

    @Override
    public Medida obtener(long id) {
        Optional<Medida> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.medida);
    }

    @Override
    public List<Medida> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Medida> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Medida> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
