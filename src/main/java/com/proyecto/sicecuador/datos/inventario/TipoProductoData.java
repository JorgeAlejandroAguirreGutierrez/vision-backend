package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.modelos.inventario.TipoProducto;
import com.proyecto.sicecuador.repositorios.inventario.IPrecioRepository;
import com.proyecto.sicecuador.repositorios.inventario.ITipoProductoRepository;
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
@Order(40)
@Profile({"dev","prod"})
public class TipoProductoData implements ApplicationRunner {
    @Autowired
    private ITipoProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoProducto> tipos_productos = new ArrayList<>();
            tipos_productos.add(new TipoProducto("TPR011907000001", "BIEN", "BIEN", "B"));
            tipos_productos.add(new TipoProducto("TPR011907000002", "SERVICIO", "SERVICIO", "S"));
            tipos_productos.add(new TipoProducto("TPR011907000003", "ACTIVO FIJO", "ACTIVOFIJO", "AF"));
            rep.saveAll(tipos_productos);
        }
    }
}
