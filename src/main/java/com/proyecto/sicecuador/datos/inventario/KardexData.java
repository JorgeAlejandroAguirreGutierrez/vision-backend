package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.repositorios.inventario.IKardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(44)
@Profile({"dev","prod"})
public class KardexData implements ApplicationRunner {
    @Autowired
    private IKardexRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Kardex> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Kardex> kardexs = new ArrayList<>();
            kardexs.add(new Kardex("KAR012001000001", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000001", "COMPRA", 200, 0, 0, 200, 2, 100.00, 200.0, new Producto(1)));
            kardexs.add(new Kardex("KAR012001000002", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000002", "COMPRA", 200, 0, 0, 240, 1, 120.0, 240.0, new Producto(2)));
            kardexs.add(new Kardex("KAR012001000003", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000003", "COMPRA",200, 0, 0, 50, 3, 200, 400.0, new Producto(3)));
            kardexs.add(new Kardex("KAR012001000004", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000004", "COMPRA",200, 0, 0, 50, 3, 200, 400.0, new Producto(4)));
            kardexs.add(new Kardex("KAR012001000005", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000005", "COMPRA",200, 0, 0, 50, 3, 200, 400.0, new Producto(5)));
            rep.saveAll(kardexs);
        }
    }
}
