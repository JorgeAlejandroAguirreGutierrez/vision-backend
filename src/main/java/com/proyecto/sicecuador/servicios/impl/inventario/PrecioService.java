package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.Precio;
import com.proyecto.sicecuador.repositorios.interf.inventario.IBodegaRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.IPrecioRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IBodegaService;
import com.proyecto.sicecuador.servicios.interf.inventario.IPrecioService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return rep.save(precio);
    }

    @Override
    public Precio actualizar(Precio precio) {
        return rep.save(precio);
    }

    @Override
    public Precio eliminar(Precio precio) {
        rep.deleteById(precio.getId());
        return precio;
    }

    @Override
    public Optional<Precio> obtener(Precio bodega) {
        return rep.findById(bodega.getId());
    }

    @Override
    public List<Precio> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Precio> precios=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,6);
            for (List<String> datos: info) {
                Precio precio = new Precio(datos);
                precios.add(precio);
            }
            if(precios.isEmpty()){
                return false;
            }
            rep.saveAll(precios);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
