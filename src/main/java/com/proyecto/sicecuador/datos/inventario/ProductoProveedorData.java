package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.modelos.compra.Proveedor;
import com.proyecto.sicecuador.modelos.inventario.ProductoProveedor;
import com.proyecto.sicecuador.repositorios.inventario.IProductoProveedorRepository;
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
public class ProductoProveedorData implements ApplicationRunner {
    @Autowired
    private IProductoProveedorRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<ProductoProveedor> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {

            List<ProductoProveedor> productos_proveedores = new ArrayList<>();
            productos_proveedores.add(new ProductoProveedor("PYP011907000001","EQUIVALENTE 1",new Producto(2),new Proveedor(1)));
            productos_proveedores.add(new ProductoProveedor("PYP011907000002","EQUIVALENTE 2",new Producto(2),new Proveedor(2)));
            productos_proveedores.add(new ProductoProveedor("PYP011907000003","EQUIVALENTE 3",new Producto(3),new Proveedor(1)));
            rep.saveAll(productos_proveedores);
        }
    }
}
