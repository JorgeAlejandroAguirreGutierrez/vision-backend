package com.proyecto.vision.servicios.impl.recaudacion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.recaudacion.FranquiciaTarjeta;
import com.proyecto.vision.repositorios.recaudacion.IFranquiciaTarjetaRepository;
import com.proyecto.vision.servicios.interf.recaudacion.IFranquiciaTarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FranquiciaTarjetaService implements IFranquiciaTarjetaService {
    @Autowired
    private IFranquiciaTarjetaRepository rep;

    @Override
    public void validar(FranquiciaTarjeta franquiciaTarjeta) {
        if(franquiciaTarjeta.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(franquiciaTarjeta.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(franquiciaTarjeta.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public FranquiciaTarjeta crear(FranquiciaTarjeta franquiciaTarjeta) {
        validar(franquiciaTarjeta);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_franquicia_tarjeta);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	franquiciaTarjeta.setCodigo(codigo.get());
    	franquiciaTarjeta.setEstado(Constantes.estadoActivo);
    	return rep.save(franquiciaTarjeta);
    }

    @Override
    public FranquiciaTarjeta actualizar(FranquiciaTarjeta franquiciaTarjeta) {
        validar(franquiciaTarjeta);
        return rep.save(franquiciaTarjeta);
    }

    @Override
    public FranquiciaTarjeta activar(FranquiciaTarjeta franquiciaTarjeta) {
        validar(franquiciaTarjeta);
        franquiciaTarjeta.setEstado(Constantes.estadoActivo);
        return rep.save(franquiciaTarjeta);
    }

    @Override
    public FranquiciaTarjeta inactivar(FranquiciaTarjeta franquiciaTarjeta) {
        validar(franquiciaTarjeta);
        franquiciaTarjeta.setEstado(Constantes.estadoInactivo);
        return rep.save(franquiciaTarjeta);
    }

    @Override
    public FranquiciaTarjeta obtener(long id) {
        Optional<FranquiciaTarjeta> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.franquicia_tarjeta);
    }

    @Override
    public List<FranquiciaTarjeta> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<FranquiciaTarjeta> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<FranquiciaTarjeta> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
