package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.modelos.inventario.LineaProducto;
import com.proyecto.sicecuador.repositorios.interf.inventario.ILineaProductoRepository;
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
@Order(33)
@Profile({"dev","prod"})
public class LineaProductoData implements ApplicationRunner {
    @Autowired
    private ILineaProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<LineaProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<LineaProducto> lineas_productos = new ArrayList<>();
            lineas_productos.add(new LineaProducto("LPR011907000001", "PRISMA", new CategoriaProducto(1)));
            lineas_productos.add(new LineaProducto("LPR011907000002", "BICICLETA", new CategoriaProducto(2)));
            lineas_productos.add(new LineaProducto("LPR011907000003", "REFRIGERADORA", new CategoriaProducto(1)));
            lineas_productos.add(new LineaProducto("LPR011907000004", "SECADORA", new CategoriaProducto(2)));
            lineas_productos.add(new LineaProducto("LPR011907000005", "COMPUTADOR", new CategoriaProducto(3)));
            lineas_productos.add(new LineaProducto("LPR011907000006", "GAFAS", new CategoriaProducto(4)));
            lineas_productos.add(new LineaProducto("LPR011907000007", "BILLETERA", new CategoriaProducto(5)));
            lineas_productos.add(new LineaProducto("LPR011907000008", "MUEBLE", new CategoriaProducto(6)));
            rep.saveAll(lineas_productos);
        }
    }
}
