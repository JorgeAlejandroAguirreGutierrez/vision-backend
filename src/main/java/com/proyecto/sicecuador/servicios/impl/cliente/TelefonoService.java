package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Telefono;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.cliente.ITelefonoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ITelefonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TelefonoService implements ITelefonoService {
    @Autowired
    private ITelefonoRepository rep;
    @Autowired
    private IClienteRepository rep_cliente;
    @Override
    public Telefono crear(Telefono telefono) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_telefono);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	telefono.setCodigo(codigo.get());
    	return rep.save(telefono);
    }

    @Override
    public Telefono actualizar(Telefono telefono) {
        return rep.save(telefono);
    }

    @Override
    public Telefono obtener(long id) {
        Optional<Telefono> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.telefono);
    }

    @Override
    public List<Telefono> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Telefono> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
