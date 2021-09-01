package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.repositorios.inventario.IPrecioRepository;
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
@Order(40)
@Profile({"dev","prod"})
public class CategoriaProductoData implements ApplicationRunner {
    @Autowired
    private ICategoriaProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CategoriaProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CategoriaProducto> categorias_productos = new ArrayList<>();
            categorias_productos.add(new CategoriaProducto("TPR011907000001", "BIEN", "BIEN", "B"));
            categorias_productos.add(new CategoriaProducto("TPR011907000002", "SERVICIO", "SERVICIO", "S"));
            categorias_productos.add(new CategoriaProducto("TPR011907000003", "ACTIVO FIJO", "ACTIVOFIJO", "AF"));
            rep.saveAll(categorias_productos);
        }
    }
}