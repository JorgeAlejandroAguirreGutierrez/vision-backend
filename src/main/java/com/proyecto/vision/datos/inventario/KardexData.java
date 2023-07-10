package com.proyecto.vision.datos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.inventario.Producto;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.repositorios.inventario.IKardexRepository;
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
            kardexs.add(new Kardex("KAR202304000001", Date.valueOf("2023-04-01"), Constantes.vacio, 200, 0, 200, 100.00, 0, 100.00, 20000.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(1)));
            kardexs.add(new Kardex("KAR202304000002", Date.valueOf("2023-04-01"), Constantes.vacio, 240, 0, 240, 120.75, 0, 120.75, 28980.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(2)));
            kardexs.add(new Kardex("KAR202304000003", Date.valueOf("2023-04-01"), Constantes.vacio, 345, 0, 345, 200.15, 0, 200.15, 69051.75, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(3)));
            kardexs.add(new Kardex("KAR202304000004", Date.valueOf("2023-04-01"), Constantes.vacio, 400, 0, 400, 200.00, 0, 200.00, 80000.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(4)));
            kardexs.add(new Kardex("KAR202304000005", Date.valueOf("2023-04-01"), Constantes.vacio, 100, 0, 100, 300.25, 0, 300.25, 30025.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(5)));
            kardexs.add(new Kardex("KAR202304000006", Date.valueOf("2023-04-01"), Constantes.vacio, 100, 0, 100, 30.00, 0, 30.00, 3000.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(6)));

            kardexs.add(new Kardex("KAR202304000001", Date.valueOf("2023-06-01"), Constantes.vacio, 200, 0, 200, 100.00, 0, 100.00, 20000.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(7)));
            kardexs.add(new Kardex("KAR202304000002", Date.valueOf("2023-06-01"), Constantes.vacio, 240, 0, 240, 120.75, 0, 120.75, 28980.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(8)));
            rep.saveAll(kardexs);
        }
    }
}