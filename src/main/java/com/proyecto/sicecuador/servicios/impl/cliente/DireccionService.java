package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IDireccionRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IDireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DireccionService implements IDireccionService {
    @Autowired
    private IDireccionRepository rep;
    
    @Override
    public Direccion crear(Direccion direccion) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_direccion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	direccion.setCodigo(codigo.get());
    	return rep.save(direccion);
    }

    @Override
    public Direccion actualizar(Direccion direccion) {
        return rep.save(direccion);
    }

    @Override
    public Direccion obtener(long id) {
        Optional<Direccion> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.direccion);
    }

    @Override
    public List<Direccion> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Direccion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<Direccion> direcciones=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal,7);
            for (List<String> datos: info) {
                Direccion direccion = new Direccion(datos);
                direcciones.add(direccion);
            }
            rep.saveAll(direcciones);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
