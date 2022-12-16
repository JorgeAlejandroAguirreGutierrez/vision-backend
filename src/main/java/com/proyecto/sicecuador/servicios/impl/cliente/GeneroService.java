package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.Genero;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IGeneroRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeneroService implements IGeneroService {
    @Autowired
    private IGeneroRepository rep;
    
    @Override
    public Genero crear(Genero genero) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_genero);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	genero.setCodigo(codigo.get());
    	genero.setEstado(Constantes.activo);
    	return rep.save(genero);
    }

    @Override
    public Genero actualizar(Genero genero) {
        return rep.save(genero);
    }

    @Override
    public Genero activar(Genero genero) {
        genero.setEstado(Constantes.activo);
        return rep.save(genero);
    }

    @Override
    public Genero inactivar(Genero genero) {
        genero.setEstado(Constantes.inactivo);
        return rep.save(genero);
    }

    @Override
    public Genero obtener(long id) {
        Optional<Genero> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.genero);
    }

    @Override
    public List<Genero> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Genero> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Genero> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Genero> buscar(Genero genero) {
        return  rep.buscar(genero.getCodigo(), genero.getDescripcion(), genero.getAbreviatura());
    }
    

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<Genero> generos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal, 11);
            for (List<String> datos: info) {
                Genero genero = new Genero(datos);
                generos.add(genero);
            }
            rep.saveAll(generos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
