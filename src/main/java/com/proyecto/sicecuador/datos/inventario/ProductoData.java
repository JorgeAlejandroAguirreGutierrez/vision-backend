package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.*;
import com.proyecto.sicecuador.modelos.proveedor.Proveedor;
import com.proyecto.sicecuador.repositorios.inventario.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Order(43)
@Profile({"dev","prod"})
public class ProductoData implements ApplicationRunner {
    @Autowired
    private IProductoRepository rep;
        @Override
        public void run(ApplicationArguments args) throws Exception {
            Optional<Producto> ant=rep.findById((long) 1);
            if (!ant.isPresent()) {
                List<Producto> productos = new ArrayList<>();
                productos.add(new Producto("PRO011907000001", "CELULARES", Constantes.no, Constantes.activo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(1), new Medida(1), new Proveedor(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO011907000002", "REFRIGERADOR", Constantes.no, Constantes.activo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(1), new Medida(1), new Proveedor(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO011907000003", "TELEVISOR", Constantes.no, Constantes.activo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(1), new Medida(1), new Proveedor(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO011907000004", "CAMAS", Constantes.no, Constantes.activo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(1), new Medida(1), new Proveedor(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO011907000005", "LADRILLOS", Constantes.no, Constantes.activo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(1), new Medida(1), new Proveedor(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO011907000006", "AVIONES", Constantes.no, Constantes.activo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(1), new Medida(1), new Proveedor(1), Collections.emptyList(), Collections.emptyList()));
                rep.saveAll(productos);
            }
    }
}
