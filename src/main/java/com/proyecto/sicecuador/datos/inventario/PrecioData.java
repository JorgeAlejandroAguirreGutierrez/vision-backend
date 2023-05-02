package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.Segmento;
import com.proyecto.sicecuador.modelos.inventario.*;
import com.proyecto.sicecuador.repositorios.inventario.IPrecioRepository;
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
public class PrecioData implements ApplicationRunner {
    @Autowired
    private IPrecioRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Precio> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Precio> precios = new ArrayList<>();
            precios.add(new Precio("PRE011907000001", 15.0, 6.0, 15.0, 15.0, 30.0, 10.0, 10.0, Constantes.activo, new Segmento(1), new Producto(1)));
            precios.add(new Precio("PRE011907000002", 2.0, 2.0, 2.0, 2.0, 20.0, 10.0, 10.0, Constantes.activo, new Segmento(2), new Producto(1)));
            precios.add(new Precio("PRE011907000003", 3.0, 3.0, 3.0, 3.0, 30.0, 10.0, 10.0, Constantes.activo, new Segmento(3), new Producto(1)));
            precios.add(new Precio("PRE011907000004", 20.0, 10.0, 20.0, 20.0, 30.0, 10.0, 10.0, Constantes.activo, new Segmento(4), new Producto(1)));

            precios.add(new Precio("PRE011907000005", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.activo, new Segmento(1), new Producto(2)));
            precios.add(new Precio("PRE011907000006", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.activo, new Segmento(2), new Producto(2)));
            precios.add(new Precio("PRE011907000007", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.activo, new Segmento(3), new Producto(2)));
            precios.add(new Precio("PRE011907000008", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.activo, new Segmento(4), new Producto(2)));

            precios.add(new Precio("PRE011907000009", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.activo, new Segmento(1), new Producto(3)));
            precios.add(new Precio("PRE011907000010", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.activo, new Segmento(2), new Producto(3)));
            precios.add(new Precio("PRE011907000011", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.activo, new Segmento(3), new Producto(3)));
            precios.add(new Precio("PRE011907000012", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.activo, new Segmento(4), new Producto(3)));

            precios.add(new Precio("PRE011907000013", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.activo, new Segmento(1), new Producto(4)));
            precios.add(new Precio("PRE011907000014", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.activo, new Segmento(2), new Producto(4)));
            precios.add(new Precio("PRE011907000015", 40.0, 3.0, 50.0, 40.0, 60.0, 110.0, 10.0, Constantes.activo, new Segmento(3), new Producto(4)));
            precios.add(new Precio("PRE011907000016", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.activo, new Segmento(4), new Producto(4)));

            precios.add(new Precio("PRE011907000017", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.activo, new Segmento(1), new Producto(5)));
            precios.add(new Precio("PRE011907000018", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.activo, new Segmento(2), new Producto(5)));
            precios.add(new Precio("PRE011907000019", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.activo, new Segmento(3), new Producto(5)));
            precios.add(new Precio("PRE011907000020", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.activo, new Segmento(4), new Producto(5)));

            precios.add(new Precio("PRE011907000021", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.activo, new Segmento(1), new Producto(6)));
            precios.add(new Precio("PRE011907000022", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.activo, new Segmento(2), new Producto(6)));
            precios.add(new Precio("PRE011907000023", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.activo, new Segmento(3), new Producto(6)));
            precios.add(new Precio("PRE011907000024", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.activo, new Segmento(4), new Producto(6)));
            rep.saveAll(precios);
        }
    }
}
