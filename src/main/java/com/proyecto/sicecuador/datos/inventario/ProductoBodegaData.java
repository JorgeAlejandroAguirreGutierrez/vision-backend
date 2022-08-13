package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.*;
import com.proyecto.sicecuador.repositorios.inventario.IProductoBodegaRepository;
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
@Order(44)
@Profile({"dev","prod"})
public class ProductoBodegaData implements ApplicationRunner {
    @Autowired
    private IProductoBodegaRepository rep;
        @Override
        public void run(ApplicationArguments args) throws Exception {
            Optional<ProductoBodega> ant=rep.findById((long) 1);
            if (!ant.isPresent()) {
                List<ProductoBodega> productosBodegas = new ArrayList<>();
                productosBodegas.add(new ProductoBodega("PRB011907000001", 50, new Producto(1), new Bodega(1)));
                productosBodegas.add(new ProductoBodega("PRB011907000002", 100, new Producto(2), new Bodega(1)));
                productosBodegas.add(new ProductoBodega("PRB011907000003", 220, new Producto(3), new Bodega(1)));
                productosBodegas.add(new ProductoBodega("PRB011907000004", 230, new Producto(4), new Bodega(2)));
                productosBodegas.add(new ProductoBodega("PRB011907000005", 210, new Producto(5), new Bodega(2)));
                productosBodegas.add(new ProductoBodega("PRB011907000006", 300, new Producto(6), new Bodega(2)));
                rep.saveAll(productosBodegas);
            }
    }
}
