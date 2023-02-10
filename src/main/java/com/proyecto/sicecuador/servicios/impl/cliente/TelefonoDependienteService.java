package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.modelos.cliente.TelefonoDependiente;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.cliente.ITelefonoDependienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ITelefonoDependienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelefonoDependienteService implements ITelefonoDependienteService {
    @Autowired
    private ITelefonoDependienteRepository rep;
    @Autowired
    private IClienteRepository rep_cliente;
    @Override
    public TelefonoDependiente crear(TelefonoDependiente telefonoDependiente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_telefono_dependiente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	telefonoDependiente.setCodigo(codigo.get());
    	return rep.save(telefonoDependiente);
    }

    @Override
    public TelefonoDependiente actualizar(TelefonoDependiente telefonoDependiente) {
        return rep.save(telefonoDependiente);
    }

    @Override
    public TelefonoDependiente obtener(long id) {
        Optional<TelefonoDependiente> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.telefono);
    }

    @Override
    public List<TelefonoDependiente> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<TelefonoDependiente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
