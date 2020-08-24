package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.modelos.cliente.Genero;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IGeneroRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return rep.save(genero);
    }

    @Override
    public Genero actualizar(Genero genero) {
        return rep.save(genero);
    }

    @Override
    public Genero eliminar(Genero genero) {
        rep.deleteById(genero.getId());
        return genero;
    }

    @Override
    public Optional<Genero> obtener(Genero genero) {
        return rep.findById(genero.getId());
    }

    @Override
    public List<Genero> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Genero> generos=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal, 11);
            for (List<String> datos: info) {
                Genero genero = new Genero(datos);
                generos.add(genero);
            }
            rep.saveAll(generos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
