package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.modelos.proveedor.Proveedor;
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
            kardexs.add(new Kardex("KAR012001000001", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000001", "COMPRA", 200, 0, 0, 200, 2, 100.00, 200.0, new Proveedor(1), new Producto(1)));
            kardexs.add(new Kardex("KAR012001000002", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000002", "COMPRA", 200, 0, 0, 240, 1, 120.0, 240.0, new Proveedor(1), new Producto(2)));
            kardexs.add(new Kardex("KAR012001000003", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000003", "COMPRA",200, 0, 0, 50, 3, 200, 400.0, new Proveedor(1), new Producto(3)));
            kardexs.add(new Kardex("KAR012001000004", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000004", "COMPRA",200, 0, 0, 50, 3, 200, 400.0, new Proveedor(1), new Producto(4)));
            kardexs.add(new Kardex("KAR012001000005", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000005", "COMPRA",200, 0, 0, 50, 3, 200, 400.0, new Proveedor(1), new Producto(5)));
            kardexs.add(new Kardex("KAR012001000006", Date.valueOf("2021-01-29"), "FACTURA DE COMPRA", "001-001-000006", "COMPRA",200, 0, 0, 50, 3, 200, 400.0, new Proveedor(1), new Producto(6)));
            /*kardexs.add(new Kardex("KAR012001000004", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000004", "COMPRA", 1, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000005", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000005", "COMPRA", 3, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000006", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000006", "COMPRA", 2, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000007", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000007", "COMPRA", 3, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54,null, null, null));
            kardexs.add(new Kardex("KAR012001000008", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000008", "COMPRA", 4, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000009", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000009", "COMPRA", 100, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000010", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000010", "COMPRA", 200, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000011", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000011", "COMPRA", 500, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000012", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000012", "COMPRA", 150, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000013", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000013", "COMPRA", 20, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000014", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000014", "COMPRA", 10, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000015", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000015", "COMPRA", 20, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000016", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000016", "COMPRA", 20, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000017", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000017", "COMPRA", 20, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54,null, null, null));
            kardexs.add(new Kardex("KAR012001000018", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000017", "COMPRA", 100, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000019", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000017", "COMPRA", 100, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000020", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000017", "COMPRA", 100, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000021", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000017", "COMPRA", 100, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000022", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000017", "COMPRA", 100, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000023", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000017", "COMPRA", 100, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000024", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000017", "COMPRA", 100, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            kardexs.add(new Kardex("KAR012001000025", Date.valueOf("2020-01-29"), "FACTURA DE COMPRA", "001-001-000017", "COMPRA", 100, 0, 75, 0, 106.1538, 106.1538,80.0,  7961.54, null, null, null));
            */
            rep.saveAll(kardexs);
        }
    }
}
