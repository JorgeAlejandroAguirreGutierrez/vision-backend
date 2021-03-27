package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.*;
import com.proyecto.sicecuador.repositorios.interf.inventario.IProductoRepository;
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
@Order(43)
@Profile({"dev","prod"})
public class ProductoData implements ApplicationRunner {
    @Autowired
    private IProductoRepository rep;
        @Override
        public void run(ApplicationArguments args) throws Exception {
            Optional<Producto> ant=rep.findById((long) 1);
            if (!ant.isPresent()) {
                /*List<Producto> productos = new ArrayList<>();
                productos.add(new Producto("PRO011907000001", "ARROZ", false, true, true, new TipoGasto(1), new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(1) ));
                productos.add(new Producto("PRO011907000002", "HUEVOS", true, true, true, new TipoGasto(2), new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(2)));
                productos.add(new Producto("PRO011907000003", "GALLETAS", true, true, true, new TipoGasto(3), new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(3)));
                productos.add(new Producto("PRO011907000004", "CAFE", true, true, true, new TipoGasto(4), new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1),new Kardex(4)));
                productos.add(new Producto("PRO011907000005", "FRIJOLES", true, true, true, new TipoGasto(1),  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1),new Kardex(5)));
                productos.add(new Producto("PRO011907000006", "COMPUTADOR", true, true, false, new TipoGasto(2),  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(6)));
                productos.add(new Producto("PRO011907000007", "TELEVISOR", true, true, false, new TipoGasto(3),  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(7)));
                productos.add(new Producto("PRO011907000008", "VENTILADOR", true, true, false, new TipoGasto(4), new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(8)));
                productos.add(new Producto("PRO011907000009", "ESCRITORIO", true, true, false, new TipoGasto(1),  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(9)));
                productos.add(new Producto("PRO011907000010", "MESA", true, true, false, new TipoGasto(2),  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(10)));
                productos.add(new Producto("PRO011907000011", "SILLA", true, true, false, new TipoGasto(3), new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(11)));
                productos.add(new Producto("PRO011907000012", "PS4", true, true, false, new TipoGasto(4), new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(12)));
                productos.add(new Producto("PRO011907000013", "CELULAR", true, true, false, new TipoGasto(1),new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(13)));
                productos.add(new Producto("PRO011907000014", "AURICULARES", true, true, true, new TipoGasto(2),  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(14)));
                productos.add(new Producto("PRO011907000015", "ZAPATOS", true, true, true, new TipoGasto(3),  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(15)));
                productos.add(new Producto("PRO011907000016", "CARGADOR", true, true, true, new TipoGasto(4), new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(16)));
                productos.add(new Producto("PRO011907000017", "CAMA", true, true, true, new TipoGasto(1), new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(17)));

                productos.add(new Producto("PRO011907000018", "CARGA PANELA", true, true, true, new TipoGasto(2), new TipoProducto(2), new PresentacionProducto(2), new Impuesto(1), new Kardex(18)));
                productos.add(new Producto("PRO011907000019", "CARGA LENTEJA", true, true, true, new TipoGasto(3), new TipoProducto(2), new PresentacionProducto(2), new Impuesto(1), new Kardex(19)));
                productos.add(new Producto("PRO011907000020", "LIMPIEZA", true, true, true, new TipoGasto(4), new TipoProducto(2), new PresentacionProducto(2), new Impuesto(1), new Kardex(20)));
                productos.add(new Producto("PRO011907000021", "EMPAQUE", true, true, true, new TipoGasto(1), new TipoProducto(2), new PresentacionProducto(2), new Impuesto(1), new Kardex(21)));
                productos.add(new Producto("PRO011907000022", "CARGA PANELA AF",true, true, true, new TipoGasto(2), new TipoProducto(3), new PresentacionProducto(3), new Impuesto(1), new Kardex(22)));
                productos.add(new Producto("PRO011907000023", "CARGA LENTEJA AF", true, true, true, new TipoGasto(3), new TipoProducto(3), new PresentacionProducto(3), new Impuesto(1), new Kardex(23)));
                productos.add(new Producto("PRO011907000024", "CARGA ARROZ AF", true, true, true, new TipoGasto(4), new TipoProducto(3), new PresentacionProducto(3), new Impuesto(1),new Kardex(24)));
                productos.add(new Producto("PRO011907000025", "CARGA FRIJOLES AF", true, true, true, new TipoGasto(1), new TipoProducto(3), new PresentacionProducto(3), new Impuesto(1), new Kardex(25)));
                rep.saveAll(productos);*/
            }
    }
}
