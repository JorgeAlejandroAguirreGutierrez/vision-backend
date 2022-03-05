package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.modelos.proveedor.Proveedor;
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
            productos_proveedores.add(new ProductoProveedor("PYP011907000003","EQUIVALENTE 3",new Producto(2),new Proveedor(1)));
            rep.saveAll(productos_proveedores);
            /*medidas_precios.add(new Medida("MED011907000001", "LIBRA", "PESO","LIBRA", "LB"));
            medidas_precios.add(new Medida("MED011907000002", "KILOGRAMO", "PESO","KILOGRAMO", "KG"));
            medidas.add(new Medida("MED011907000003", "QUINTAL US", "PESO","QUINTAL US", "CWT"));
            medidas.add(new Medida("MED011907000004", "QUINTAL UK", "PESO","QUINTAL UK", "UK_CWT"));
            medidas.add(new Medida("MED011907000005", "STONE", "PESO","STONE", "STONE"));
            medidas.add(new Medida("MED011907000006", "ONZA", "PESO","ONZA", "OZ"));
            medidas.add(new Medida("MED011907000007", "GRANO", "PESO","GRANO", "GRAIN"));
            medidas.add(new Medida("MED011907000008", "GRAMO", "PESO","GRAMO", "GR"));
            medidas.add(new Medida("MED011907000009", "SLUG", "PESO","SLUG", "TON"));
            medidas.add(new Medida("MED011907000010", "TONELADA", "PESO","TONELADA LARGA", "UK_TON"));
            medidas.add(new Medida("MED011907000011", "TONELADA LARGA", "PESO","QUINTAL US", "CWT"));
            medidas.add(new Medida("MED011907000012", "MILIGRAMO", "PESO","MILIGRAMO", "MG"));
            */
        }
    }
}
