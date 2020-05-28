package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.BodegaProducto;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IBodegaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(45)
public class BodegaProductoData implements ApplicationRunner {
    @Autowired
    private IBodegaProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<BodegaProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<BodegaProducto> bodegas_productos = new ArrayList<>();
            bodegas_productos.add(new BodegaProducto("BOP011909000001", new Producto(1), new Kardex(1), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000002", new Producto(2), new Kardex(2), new Bodega(2)));
            bodegas_productos.add(new BodegaProducto("BOP011909000003",  new Producto(3), new Kardex(3), new Bodega(3)));
            bodegas_productos.add(new BodegaProducto("BOP011909000004", new Producto(4), new Kardex(4), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000005", new Producto(5),new Kardex(5), new Bodega(2)));
            bodegas_productos.add(new BodegaProducto("BOP011909000006", new Producto(6),new Kardex(6), new Bodega(3)));
            bodegas_productos.add(new BodegaProducto("BOP011909000007", new Producto(7),new Kardex(7), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000008",  new Producto(8),new Kardex(8), new Bodega(2)));

            bodegas_productos.add(new BodegaProducto("BOP011909000009", new Producto(9),new Kardex(9), new Bodega(3)));
            bodegas_productos.add(new BodegaProducto("BOP011909000010", new Producto(10),new Kardex(10), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000011", new Producto(11),new Kardex(11), new Bodega(2)));
            bodegas_productos.add(new BodegaProducto("BOP011909000012", new Producto(12),new Kardex(12), new Bodega(3)));
            bodegas_productos.add(new BodegaProducto("BOP011909000013", new Producto(13),new Kardex(13), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000014", new Producto(14),new Kardex(14), new Bodega(2)));
            bodegas_productos.add(new BodegaProducto("BOP011909000015", new Producto(15),new Kardex(15), new Bodega(3)));
            bodegas_productos.add(new BodegaProducto("BOP011909000016",  new Producto(16),new Kardex(16), new Bodega(2)));
            bodegas_productos.add(new BodegaProducto("BOP011909000017", new Producto(17),new Kardex(17), new Bodega(3)));
            //servicios
            bodegas_productos.add(new BodegaProducto("BOP011909000018", new Producto(18),new Kardex(18), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000019", new Producto(19),new Kardex(19), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000020", new Producto(20),new Kardex(20), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000021", new Producto(21),new Kardex(21), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000022", new Producto(22),new Kardex(22), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000023", new Producto(23),new Kardex(23), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000024", new Producto(24),new Kardex(24), new Bodega(1)));
            bodegas_productos.add(new BodegaProducto("BOP011909000025", new Producto(25),new Kardex(25), new Bodega(1)));
            rep.saveAll(bodegas_productos);
        }
    }
}
