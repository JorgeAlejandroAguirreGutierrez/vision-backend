package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.repositorios.inventario.ICategoriaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(29)
@Profile({"dev","prod"})
public class CategoriaProductoData implements ApplicationRunner {
    @Autowired
    private ICategoriaProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CategoriaProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CategoriaProducto> categorias_productos = new ArrayList<>();
            categorias_productos.add(new CategoriaProducto("TPR011907000001", "BIEN", "B", Constantes.activo));
            categorias_productos.add(new CategoriaProducto("TPR011907000002", "SERVICIO", "S", Constantes.activo));
            categorias_productos.add(new CategoriaProducto("TPR011907000003", "ACTIVO_FIJO", "AF", Constantes.activo));
            categorias_productos.add(new CategoriaProducto("TPR011907000004", "OTRO", "OT", Constantes.activo));
            rep.saveAll(categorias_productos);
        }
    }
}
