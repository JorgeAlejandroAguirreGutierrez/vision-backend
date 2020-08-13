package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.modelos.inventario.LineaProducto;
import com.proyecto.sicecuador.modelos.inventario.SubLineaProducto;
import com.proyecto.sicecuador.repositorios.interf.inventario.ILineaProductoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.ISubLineaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(34)
public class SubLineaProductoData implements ApplicationRunner {
    @Autowired
    private ISubLineaProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<SubLineaProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<SubLineaProducto> sub_lineas_productos = new ArrayList<>();
            sub_lineas_productos.add(new SubLineaProducto("LPR011907000001", "INDURAMA", new LineaProducto(1)));
            sub_lineas_productos.add(new SubLineaProducto("LPR011907000002", "BROTHER", new LineaProducto(2)));
            sub_lineas_productos.add(new SubLineaProducto("LPR011907000003", "LG", new LineaProducto(3)));
            sub_lineas_productos.add(new SubLineaProducto("LPR011907000004", "SONY", new LineaProducto(4)));
            sub_lineas_productos.add(new SubLineaProducto("LPR011907000004", "BRAHMA", new LineaProducto(1)));
            sub_lineas_productos.add(new SubLineaProducto("LPR011907000004", "PIAF", new LineaProducto(2)));
            rep.saveAll(sub_lineas_productos);
        }
    }
}
