package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.modelos.inventario.LineaProducto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IGrupoProductoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.ILineaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
@Order(38)
public class LineaProductoData implements ApplicationRunner {
    @Autowired
    private ILineaProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<LineaProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<LineaProducto> lineas_productos = new ArrayList<>();
            lineas_productos.add(new LineaProducto("LPR011907000001", "PRISMA", "INDURAMA", "LG", new GrupoProducto(1)));
            lineas_productos.add(new LineaProducto("LPR011907000002", "PLATA", "INDURAMA", "MABE", new GrupoProducto(2)));
            lineas_productos.add(new LineaProducto("LPR011907000003", "PRISMA", "BROTHER", "HUAWEI", new GrupoProducto(3)));
            lineas_productos.add(new LineaProducto("LPR011907000004", "PLATA", "BROTHER", "HUEAWEI", new GrupoProducto(3)));
            rep.saveAll(lineas_productos);
        }
    }
}
