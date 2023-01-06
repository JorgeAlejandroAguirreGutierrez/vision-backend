package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.*;
import com.proyecto.sicecuador.repositorios.inventario.IProductoRepository;
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
                List<Producto> productos = new ArrayList<>();
                productos.add(new Producto("PRO011907000001", "CELULARES", Constantes.no, Constantes.activo, Constantes.si, new TipoGasto(1), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1)));
                productos.add(new Producto("PRO011907000002", "REFRIGERADOR", Constantes.no, Constantes.activo, Constantes.si, new TipoGasto(1), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1)));
                productos.add(new Producto("PRO011907000003", "TELEVISOR", Constantes.no, Constantes.activo, Constantes.si, new TipoGasto(1), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1)));
                productos.add(new Producto("PRO011907000004", "CAMAS", Constantes.no, Constantes.activo, Constantes.si, new TipoGasto(1), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1)));
                productos.add(new Producto("PRO011907000005", "LADRILLOS", Constantes.no, Constantes.activo, Constantes.si, new TipoGasto(1), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1)));
                productos.add(new Producto("PRO011907000006", "AVIONES", Constantes.no, Constantes.activo, Constantes.si, new TipoGasto(1), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1)));
                /*productos.add(new Producto("PRO011907000001", "ARROZ", false, "ACTIVO", true, new TipoGasto(1), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000002", "HUEVOS", true, "ACTIVO", true, new TipoGasto(2), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000003", "GALLETAS", true, "ACTIVO", true, new TipoGasto(3), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1),new Medida(2), new Bodega(1)));
                productos.add(new Producto("PRO011907000004", "CAFE", true, "ACTIVO", true, new TipoGasto(4), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000005", "FRIJOLES", true, "ACTIVO", true, new TipoGasto(1),  new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000006", "COMPUTADOR", true, "ACTIVO", false, new TipoGasto(2),  new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000007", "TELEVISOR", true, "ACTIVO", false, new TipoGasto(3),  new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000008", "VENTILADOR", true, "ACTIVO", false, new TipoGasto(4), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000009", "ESCRITORIO", true, "ACTIVO", false, new TipoGasto(1),  new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000010", "MESA", true, "ACTIVO", false, new TipoGasto(2),  new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000011", "SILLA", true, "ACTIVO", false, new TipoGasto(3), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000012", "PS4", true, "ACTIVO", false, new TipoGasto(4), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000013", "CELULAR", true, "ACTIVO", false, new TipoGasto(1),new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000014", "AURICULARES", true, "ACTIVO", true, new TipoGasto(2),  new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000015", "ZAPATOS", true, "ACTIVO", true, new TipoGasto(3),  new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000016", "CARGADOR", true, "ACTIVO", true, new TipoGasto(4), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));
                productos.add(new Producto("PRO011907000017", "CAMA", true, "ACTIVO", true, new TipoGasto(1), new CategoriaProducto(1), new Impuesto(1), new GrupoProducto(1), new Medida(1), new Bodega(1)));

                productos.add(new Producto("PRO011907000018", "CARGA PANELA", true, "ACTIVO", true, new TipoGasto(2), new CategoriaProducto(2),new Impuesto(1), new GrupoProducto(2), new Medida(3), new Bodega(1)));
                productos.add(new Producto("PRO011907000019", "CARGA LENTEJA", true, "ACTIVO", true, new TipoGasto(3), new CategoriaProducto(2), new Impuesto(1), new GrupoProducto(2), new Medida(3), new Bodega(1)));
                productos.add(new Producto("PRO011907000020", "LIMPIEZA", true, "ACTIVO", true, new TipoGasto(4), new CategoriaProducto(2), new Impuesto(1), new GrupoProducto(2), new Medida(3), new Bodega(1)));
                productos.add(new Producto("PRO011907000021", "EMPAQUE", true, "ACTIVO", true, new TipoGasto(1), new CategoriaProducto(2), new Impuesto(1), new GrupoProducto(2), new Medida(3), new Bodega(1)));
                productos.add(new Producto("PRO011907000022", "CARGA PANELA AF",true, "ACTIVO", true, new TipoGasto(2), new CategoriaProducto(3), new Impuesto(1), new GrupoProducto(3), new Medida(3), new Bodega(1)));
                productos.add(new Producto("PRO011907000023", "CARGA LENTEJA AF", true, "ACTIVO", true, new TipoGasto(3), new CategoriaProducto(3), new Impuesto(1), new GrupoProducto(3), new Medida(3), new Bodega(1)));
                productos.add(new Producto("PRO011907000024", "CARGA ARROZ AF", true, "ACTIVO", true, new TipoGasto(4), new CategoriaProducto(3), new Impuesto(1), new GrupoProducto(3), new Medida(3), new Bodega(1)));
                productos.add(new Producto("PRO011907000025", "CARGA FRIJOLES AF", true, "ACTIVO", true, new TipoGasto(1), new CategoriaProducto(3), new Impuesto(1), new GrupoProducto(3), new Medida(3), new Bodega(1)));*/
                rep.saveAll(productos);
            }
    }
}
