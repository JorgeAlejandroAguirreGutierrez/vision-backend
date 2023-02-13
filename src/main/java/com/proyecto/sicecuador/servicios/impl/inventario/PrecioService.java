package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.Precio;
import com.proyecto.sicecuador.repositorios.inventario.IPrecioRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IPrecioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    	precio.setEstado(Constantes.activo);
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
