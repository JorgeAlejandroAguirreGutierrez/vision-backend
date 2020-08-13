package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.modelos.inventario.SubGrupoProducto;
import com.proyecto.sicecuador.repositorios.interf.inventario.ISubGrupoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
@Order(31)
public class SubGrupoProductoData implements ApplicationRunner {
    @Autowired
    private ISubGrupoProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<SubGrupoProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<SubGrupoProducto> sub_grupos_productos = new ArrayList<>();
            sub_grupos_productos.add(new SubGrupoProducto("SPR011907000001", "PRISMA", new GrupoProducto(1)));
            sub_grupos_productos.add(new SubGrupoProducto("SPR011907000002", "PLATA", new GrupoProducto(2)));
            sub_grupos_productos.add(new SubGrupoProducto("SPR011907000003", "BLANCA", new GrupoProducto(1)));
            sub_grupos_productos.add(new SubGrupoProducto("SPR011907000004", "GRIS", new GrupoProducto(2)));
            rep.saveAll(sub_grupos_productos);
        }
    }
}
