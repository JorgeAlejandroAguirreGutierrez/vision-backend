package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.inventario.Medida;
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
            kardexs.add(new Kardex("KAR012001000001", Date.valueOf("2021-01-29"), Constantes.factura_compra, "001-001-000001", Constantes.operacion_compra,600, 0, 0, 600, 10, 60, 600, new Producto(1), new Bodega(1)));
            kardexs.add(new Kardex("KAR012001000002", Date.valueOf("2021-01-29"), Constantes.factura_compra, "001-001-000002", Constantes.operacion_compra,1000, 0, 0, 1000, 20, 50, 1000, new Producto(2), new Bodega(1)));
            kardexs.add(new Kardex("KAR012001000003", Date.valueOf("2021-01-29"), Constantes.factura_compra, "001-001-000003", Constantes.operacion_compra,1200, 0, 0, 1200, 30, 40, 1200, new Producto(3), new Bodega(1)));
            kardexs.add(new Kardex("KAR012001000004", Date.valueOf("2021-01-29"), Constantes.factura_compra, "001-001-000004", Constantes.operacion_compra,1200, 0, 0, 1200, 40, 30, 1200, new Producto(4), new Bodega(1)));
            kardexs.add(new Kardex("KAR012001000005", Date.valueOf("2021-01-29"), Constantes.factura_compra, "001-001-000005", Constantes.operacion_compra,1000, 0, 0, 1000, 50, 20, 1000, new Producto(5), new Bodega(1)));
            kardexs.add(new Kardex("KAR012001000006", Date.valueOf("2021-01-29"), Constantes.factura_compra, "001-001-000006", Constantes.operacion_compra,600, 0, 0, 600, 60, 10, 600, new Producto(6), new Bodega(1)));
            rep.saveAll(kardexs);
        }
    }
}
