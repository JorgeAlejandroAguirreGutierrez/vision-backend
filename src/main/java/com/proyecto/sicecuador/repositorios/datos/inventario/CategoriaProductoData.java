package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.modelos.inventario.SubGrupoProducto;
import com.proyecto.sicecuador.repositorios.interf.inventario.ICategoriaProductoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.IGrupoProductoRepository;
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
@Order(32)
@Profile({"dev","prod"})
public class CategoriaProductoData implements ApplicationRunner {
    @Autowired
    private ICategoriaProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CategoriaProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CategoriaProducto> categorias_productos = new ArrayList<>();
            categorias_productos.add(new CategoriaProducto("GPR011907000001", "TELEVISOR", new SubGrupoProducto(1)));
            categorias_productos.add(new CategoriaProducto("GPR011907000002", "NEVERA", new SubGrupoProducto(2)));
            categorias_productos.add(new CategoriaProducto("GPR011907000003", "CELULAR", new SubGrupoProducto(3)));
            categorias_productos.add(new CategoriaProducto("GPR011907000004", "SILLA", new SubGrupoProducto(4)));
            categorias_productos.add(new CategoriaProducto("GPR011907000005", "COMPUTADOR", new SubGrupoProducto(1)));
            categorias_productos.add(new CategoriaProducto("GPR011907000006", "MESA", new SubGrupoProducto(2)));
            rep.saveAll(categorias_productos);
        }
    }
}
