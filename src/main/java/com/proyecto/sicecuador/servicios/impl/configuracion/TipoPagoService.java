package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.TipoPago;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.repositorios.configuracion.ITipoPagoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ITipoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TipoPagoService implements ITipoPagoService {
    @Autowired
    private ITipoPagoRepository rep;

    @Override
    public void validar(TipoPago tipoPago) {
        if(tipoPago.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(tipoPago.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public TipoPago crear(TipoPago tipoPago) {
        validar(tipoPago);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_pago);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipoPago.setCodigo(codigo.get());
    	tipoPago.setEstado(Constantes.activo);
    	return rep.save(tipoPago);
    }

    @Override
    public TipoPago actualizar(TipoPago tipoPago) {
        validar(tipoPago);
        return rep.save(tipoPago);
    }

    @Override
    public TipoPago activar(TipoPago tipoPago) {
        validar(tipoPago);
        tipoPago.setEstado(Constantes.activo);
        return rep.save(tipoPago);
    }

    @Override
    public TipoPago inactivar(TipoPago tipoPago) {
        validar(tipoPago);
        tipoPago.setEstado(Constantes.inactivo);
        return rep.save(tipoPago);
    }

    @Override
    public TipoPago obtener(long id) {
    	Optional<TipoPago> res= rep.findById(id);
    	if(res.isPresent()) {
    		return res.get();
    	}
    	throw new EntidadNoExistenteException(Constantes.tipo_pago);
    }

    @Override
    public List<TipoPago> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<TipoPago> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<TipoPago> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
