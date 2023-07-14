package com.proyecto.vision.datos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.compra.Proveedor;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import com.proyecto.vision.modelos.inventario.*;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.inventario.IProductoRepository;
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
                //PRODUCTOS PARA EMPRESA 1
                productos.add(new Producto("PRO012307000001", "CELULARES", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(1), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO012307000002", "REFRIGERADOR", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO012307000003", "TELEVISOR", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(1), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO012307000004", "CAMAS", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO012307000005", "LADRILLOS", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(3), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO012307000006", "AVIONES", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(1), new GrupoProducto(1), new TipoGasto(1), new Impuesto(4), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                //SERVICIOS PARA EMPRESA 1
                productos.add(new Producto("PRO012307000007", "SERVICIO INFORMATICO", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(2), new GrupoProducto(1), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO012307000008", "SERVICIO ASEO", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(2), new GrupoProducto(2), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO012307000008", "INTERESES CORRIENTES", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(2), new GrupoProducto(3), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO012307000008", "INTERESES POR MORA", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(2), new GrupoProducto(4), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                //PRODUCTOS PARA EMPRESA 2
                productos.add(new Producto("PRO022306000001", "ARROZ CONEJO", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(1), new GrupoProducto(16), new TipoGasto(1), new Impuesto(2), new Medida(35), new Proveedor(3), new Empresa(2), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO022306000002", "MESA NAPOLITANA", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(1), new GrupoProducto(17), new TipoGasto(2), new Impuesto(4), new Medida(35), new Proveedor(3), new Empresa(2), Collections.emptyList(), Collections.emptyList()));
                //SERVICIOS PARA EMPRESA 2
                productos.add(new Producto("PRO022306000003", "SERVICIO INFORMATICO", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(2), new GrupoProducto(1), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO022306000004", "SERVICIO ASEO", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(2), new GrupoProducto(2), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO022306000005", "INTERESES CORRIENTES", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(2), new GrupoProducto(3), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                productos.add(new Producto("PRO022306000006", "INTERESES POR MORA", Constantes.no, Constantes.estadoActivo, new CategoriaProducto(2), new GrupoProducto(4), new TipoGasto(1), new Impuesto(2), new Medida(1), new Proveedor(1), new Empresa(1), Collections.emptyList(), Collections.emptyList()));
                rep.saveAll(productos);
            }
    }
}
