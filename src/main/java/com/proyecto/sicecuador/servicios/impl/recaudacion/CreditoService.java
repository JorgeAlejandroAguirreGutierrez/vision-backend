package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.Credito;
import com.proyecto.sicecuador.repositorios.recaudacion.ICreditoRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class CreditoService implements ICreditoService {
    @Autowired
    private ICreditoRepository rep;
    
    @Override
    public Credito crear(Credito credito) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_credito);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	credito.setCodigo(codigo.get());
    	return rep.save(credito);
    }

    @Override
    public Credito actualizar(Credito credito) {
        return rep.save(credito);
    }

    @Override
    public Credito obtener(long id) {
        Optional<Credito> res=rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.credito);
    }

    @Override
    public List<Credito> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Credito> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
