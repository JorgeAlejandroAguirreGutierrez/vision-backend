package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.repositorios.cliente.IDependienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IDependienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DependienteService implements IDependienteService {
    @Autowired
    private IDependienteRepository rep;
    
    @Override
    public Dependiente crear(Dependiente dependiente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_dependiente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	dependiente.setCodigo(codigo.get());
    	return rep.save(dependiente);
    }

    @Override
    public Dependiente actualizar(Dependiente dependiente) {
        return rep.save(dependiente);
    }

    @Override
    public Dependiente obtener(long id) {
        Optional<Dependiente> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.dependiente);
    }

    @Override
    public List<Dependiente> consultar() {
        return rep.findAll();
    }
    
    @Override
    public Page<Dependiente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Dependiente> consultarPorRazonSocial(String razonSocial) {
        return  rep.consultarPorRazonSocial(razonSocial);

    }
    @Override
    public List<Dependiente> consultarPorCliente(long clienteId) {
        return  rep.consultarPorCliente(clienteId);
    }
}
