package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.modelos.inventario.Precio;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IMedidaRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.IPrecioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(44)
public class PrecioData implements ApplicationRunner {
    @Autowired
    private IPrecioRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Precio> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Precio> precios = new ArrayList<>();
            precios.add(new Precio("PRE011907000001", "", 0, 5.62, new Producto(1)));
            precios.add(new Precio("PRE011907000002", "", 0, 10.23, new Producto(2)));
            precios.add(new Precio("PRE011907000003", "", 0, 15.45, new Producto(3)));
            precios.add(new Precio("PRE011907000004", "", 0, 20, new Producto(4)));
            precios.add(new Precio("PRE011907000005", "", 0, 25, new Producto(5)));
            precios.add(new Precio("PRE011907000006", "", 0, 30.36, new Producto(6)));
            precios.add(new Precio("PRE011907000007", "", 0, 35, new Producto(7)));
            precios.add(new Precio("PRE011907000008", "", 0, 40.18, new Producto(8)));
            precios.add(new Precio("PRE011907000009", "", 0, 45, new Producto(9)));
            precios.add(new Precio("PRE011907000010", "", 0, 50.23, new Producto(10)));
            precios.add(new Precio("PRE011907000011", "", 0, 55, new Producto(11)));
            precios.add(new Precio("PRE011907000012", "", 0, 60.85, new Producto(12)));
            precios.add(new Precio("PRE011907000013", "", 0, 65, new Producto(13)));

            precios.add(new Precio("PRE011907000014", "", 0, 40, new Producto(14)));
            precios.add(new Precio("PRE011907000015", "", 0, 45.96, new Producto(15)));
            precios.add(new Precio("PRE011907000016", "", 0, 50, new Producto(16)));
            precios.add(new Precio("PRE011907000017", "", 0, 55, new Producto(17)));
            precios.add(new Precio("PRE011907000018", "", 0, 60.22, new Producto(18)));
            precios.add(new Precio("PRE011907000019", "", 0, 65, new Producto(19)));
            precios.add(new Precio("PRE011907000020", "", 0, 70.88, new Producto(20)));
            precios.add(new Precio("PRE011907000021", "", 0, 75, new Producto(21)));
            precios.add(new Precio("PRE011907000022", "", 0, 80.22, new Producto(22)));
            precios.add(new Precio("PRE011907000023", "", 0, 85, new Producto(23)));
            precios.add(new Precio("PRE011907000024", "", 0, 90, new Producto(24)));
            precios.add(new Precio("PRE011907000025", "", 0, 95.15, new Producto(25)));
            rep.saveAll(precios);
        }
    }
}
