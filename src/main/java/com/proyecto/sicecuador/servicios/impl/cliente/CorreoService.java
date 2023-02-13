package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Correo;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.cliente.ICorreoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CorreoService implements ICorreoService {
    @Autowired
    private ICorreoRepository rep;
    @Autowired
    private IClienteRepository rep_cliente;
    
    @Override
    public Correo crear(Correo correo) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_correo);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	correo.setCodigo(codigo.get());
    	return rep.save(correo);
    }

    @Override
    public Correo actualizar(Correo correo) {
        return rep.save(correo);
    }

    @Override
    public Correo obtener(long id) {
        Optional<Correo> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.correo);
    }

    @Override
    public List<Correo> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Correo> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
