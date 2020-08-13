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
                productos.add(new Producto("PRO011907000001", "ARROZ", "", "", "", "", 0, false, true, "", true, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(1)));
                productos.add(new Producto("PRO011907000002", "HUEVOS", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(2)));
                productos.add(new Producto("PRO011907000003", "GALLETAS", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(3)));
                productos.add(new Producto("PRO011907000004", "CAFE", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1),new Kardex(4)));
                productos.add(new Producto("PRO011907000005", "FRIJOLES", "", "", "", "", 0, true, true, "", true,  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1),new Kardex(5)));
                productos.add(new Producto("PRO011907000006", "COMPUTADOR", "", "", "", "", 0, true, true, "", false,  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(6)));
                productos.add(new Producto("PRO011907000007", "TELEVISOR", "", "", "", "", 0, true, true, "", false,  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(7)));
                productos.add(new Producto("PRO011907000008", "VENTILADOR", "", "", "", "", 0, true, true, "", false, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(8)));
                productos.add(new Producto("PRO011907000009", "ESCRITORIO", "", "", "", "", 0, true, true, "", false,  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(9)));
                productos.add(new Producto("PRO011907000010", "MESA", "", "", "", "", 0, true, true, "", false,  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(10)));
                productos.add(new Producto("PRO011907000011", "SILLA", "", "", "", "", 0, true, true, "", false, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(11)));
                productos.add(new Producto("PRO011907000012", "PS4", "", "", "", "", 0, true, true, "", false, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(12)));
                productos.add(new Producto("PRO011907000013", "CELULAR", "", "", "", "", 0, true, true, "", false, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(13)));
                productos.add(new Producto("PRO011907000014", "AURICULARES", "", "", "", "", 0, true, true, "",true,  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(14)));
                productos.add(new Producto("PRO011907000015", "ZAPATOS", "", "", "", "", 0, true, true, "",true,  new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(15)));
                productos.add(new Producto("PRO011907000016", "CARGADOR", "", "", "", "", 0, true, true, "",true, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(16)));
                productos.add(new Producto("PRO011907000017", "CAMA", "", "", "", "", 0, true, true, "", true, new TipoProducto(1), new PresentacionProducto(1), new Impuesto(1), new Kardex(17)));

                productos.add(new Producto("PRO011907000018", "CARGA PANELA", "", "", "", "", 0, true, true, "", true, new TipoProducto(2), new PresentacionProducto(2), new Impuesto(1), new Kardex(18)));
                productos.add(new Producto("PRO011907000019", "CARGA LENTEJA", "", "", "", "", 0, true, true, "", true, new TipoProducto(2), new PresentacionProducto(2), new Impuesto(1), new Kardex(19)));
                productos.add(new Producto("PRO011907000020", "LIMPIEZA", "", "", "", "", 0, true, true, "", true, new TipoProducto(2), new PresentacionProducto(2), new Impuesto(1), new Kardex(20)));
                productos.add(new Producto("PRO011907000021", "EMPAQUE", "", "", "", "", 0, true, true, "", true, new TipoProducto(2), new PresentacionProducto(2), new Impuesto(1), new Kardex(21)));
                productos.add(new Producto("PRO011907000022", "CARGA PANELA AF", "", "", "", "", 0, true, true, "", true, new TipoProducto(3), new PresentacionProducto(3), new Impuesto(1), new Kardex(22)));
                productos.add(new Producto("PRO011907000023", "CARGA LENTEJA AF", "", "", "", "", 0, true, true, "", true, new TipoProducto(3), new PresentacionProducto(3), new Impuesto(1), new Kardex(23)));
                productos.add(new Producto("PRO011907000024", "CARGA ARROZ AF", "", "", "", "", 0, true, true, "", true, new TipoProducto(3), new PresentacionProducto(3), new Impuesto(1),new Kardex(24)));
                productos.add(new Producto("PRO011907000025", "CARGA FRIJOLES AF", "", "", "", "", 0, true, true, "", true, new TipoProducto(3), new PresentacionProducto(3), new Impuesto(1), new Kardex(25)));
                rep.saveAll(productos);
            }
    }
}
