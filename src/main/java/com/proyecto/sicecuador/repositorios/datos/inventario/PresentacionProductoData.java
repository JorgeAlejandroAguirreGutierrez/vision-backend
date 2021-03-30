package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.PresentacionProducto;
import com.proyecto.sicecuador.modelos.inventario.SubLineaProducto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IPresentacionProductoRepository;
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
@Order(35)
@Profile({"dev","prod"})
public class PresentacionProductoData implements ApplicationRunner {
    @Autowired
    private IPresentacionProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<PresentacionProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<PresentacionProducto> presentaciones_productos = new ArrayList<>();
            presentaciones_productos.add(new PresentacionProducto("LPR011907000001", "30''", new SubLineaProducto(1)));
            presentaciones_productos.add(new PresentacionProducto("LPR011907000002", "PLUS", new SubLineaProducto(2)));
            presentaciones_productos.add(new PresentacionProducto("LPR011907000003", "ZERO FUSION", new SubLineaProducto(3)));
            presentaciones_productos.add(new PresentacionProducto("LPR011907000004", "ECO DELUXE", new SubLineaProducto(4)));
            presentaciones_productos.add(new PresentacionProducto("LPR011907000005", "GEFORCE", new SubLineaProducto(5)));
            presentaciones_productos.add(new PresentacionProducto("LPR011907000006", "DE SERIE", new SubLineaProducto(6)));
            presentaciones_productos.add(new PresentacionProducto("LPR011907000006", "RIZEN", new SubLineaProducto(7)));
            presentaciones_productos.add(new PresentacionProducto("LPR011907000006", "INTEL", new SubLineaProducto(8)));
            rep.saveAll(presentaciones_productos);
        }
    }
}
