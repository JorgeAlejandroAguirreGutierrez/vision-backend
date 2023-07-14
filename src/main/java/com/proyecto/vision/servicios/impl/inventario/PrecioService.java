package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.inventario.Precio;
import com.proyecto.vision.repositorios.inventario.IPrecioRepository;
import com.proyecto.vision.servicios.interf.inventario.IPrecioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PrecioService implements IPrecioService {
    @Autowired
    private IPrecioRepository rep;
    
    @Override
    public Precio crear(Precio precio) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_precio);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	precio.setCodigo(codigo.get());
    	precio.setEstado(Constantes.estadoActivo);
    	return rep.save(precio);
    }

    @Override
    public Precio actualizar(Precio precio) {
        return rep.save(precio);
    }

    @Override
    public Precio obtener(long id) {
        Optional<Precio> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.precio);
    }

    @Override
    public List<Precio> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Precio> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
