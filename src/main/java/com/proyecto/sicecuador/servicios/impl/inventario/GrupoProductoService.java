package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IGrupoProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IGrupoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoProductoService implements IGrupoProductoService {
    @Autowired
    private IGrupoProductoRepository rep;
    @Override
    public GrupoProducto crear(GrupoProducto grupo_producto) {
        return rep.save(grupo_producto);
    }

    @Override
    public GrupoProducto actualizar(GrupoProducto grupo_producto) {
        return rep.save(grupo_producto);
    }

    @Override
    public GrupoProducto eliminar(GrupoProducto grupo_producto) {
        rep.deleteById(grupo_producto.getId());
        return grupo_producto;
    }

    @Override
    public Optional<GrupoProducto> obtener(GrupoProducto grupo_producto) {
        return rep.findById(grupo_producto.getId());
    }

    @Override
    public List<GrupoProducto> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<GrupoProducto> grupos_productos=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,2);
            for (List<String> datos: info) {
                GrupoProducto caracteristica = new GrupoProducto(datos);
                grupos_productos.add(caracteristica);
            }
            if(grupos_productos.isEmpty()){
                return false;
            }
            rep.saveAll(grupos_productos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
