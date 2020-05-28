package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.inventario.*;
import com.proyecto.sicecuador.repositorios.interf.inventario.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(43)
public class ProductoData implements ApplicationRunner {
    @Autowired
    private IProductoRepository rep;
        @Override
        public void run(ApplicationArguments args) throws Exception {
            Optional<Producto> ant=rep.findById((long) 1);
            if (!ant.isPresent()) {
                List<Producto> productos = new ArrayList<>();
                productos.add(new Producto("PRO011907000001", "ARROZ", "", "", "", "", 0, false, true, "", true, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000002", "HUEVOS", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000003", "GALLETAS", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000004", "CAFE", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000005", "FRIJOLES", "", "", "", "", 0, true, true, "", true,  new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000006", "COMPUTADOR", "", "", "", "", 0, true, true, "", true,  new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000007", "TELEVISOR", "", "", "", "", 0, true, true, "", true,  new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000008", "VENTILADOR", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000009", "ESCRITORIO", "", "", "", "", 0, true, true, "", true,  new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000010", "MESA", "", "", "", "", 0, true, true, "", true,  new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000011", "SILLA", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000012", "PS4", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000013", "CELULAR", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000014", "AURICULARES", "", "", "", "", 0, true, true, "",false,  new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000015", "ZAPATOS", "", "", "", "", 0, true, true, "",false,  new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000016", "CARGADOR", "", "", "", "", 0, true, true, "",false, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));
                productos.add(new Producto("PRO011907000017", "CAMA", "", "", "", "", 0, true, true, "", false, new TipoProducto(1), new GrupoProducto(1), new Impuesto(1)));

                productos.add(new Producto("PRO011907000018", "CARGA PANELA", "", "", "", "", 0, true, true, "", false, new TipoProducto(2), new GrupoProducto(2), new Impuesto(1)));
                productos.add(new Producto("PRO011907000019", "CARGA LENTEJA", "", "", "", "", 0, true, true, "", false, new TipoProducto(2), new GrupoProducto(2), new Impuesto(1)));
                productos.add(new Producto("PRO011907000020", "LIMPIEZA", "", "", "", "", 0, true, true, "", false, new TipoProducto(2), new GrupoProducto(2), new Impuesto(1)));
                productos.add(new Producto("PRO011907000021", "EMPAQUE", "", "", "", "", 0, true, true, "", false, new TipoProducto(2), new GrupoProducto(2), new Impuesto(1)));
                productos.add(new Producto("PRO011907000022", "CARGA PANELA AF", "", "", "", "", 0, true, true, "", false, new TipoProducto(3), new GrupoProducto(3), new Impuesto(1)));
                productos.add(new Producto("PRO011907000023", "CARGA LENTEJA AF", "", "", "", "", 0, true, true, "", false, new TipoProducto(3), new GrupoProducto(3), new Impuesto(1)));
                productos.add(new Producto("PRO011907000024", "CARGA ARROZ AF", "", "", "", "", 0, true, true, "", false, new TipoProducto(3), new GrupoProducto(3), new Impuesto(1)));
                productos.add(new Producto("PRO011907000025", "CARGA FRIJOLES AF", "", "", "", "", 0, true, true, "", false, new TipoProducto(3), new GrupoProducto(3), new Impuesto(1)));
                rep.saveAll(productos);
            }
    }
}
