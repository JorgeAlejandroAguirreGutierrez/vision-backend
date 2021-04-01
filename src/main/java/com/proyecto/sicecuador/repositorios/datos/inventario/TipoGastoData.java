package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.repositorios.interf.inventario.ITipoGastoRepository;
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
@Order(33)
@Profile({"dev","prod"})
public class TipoGastoData implements ApplicationRunner {
    @Autowired
    private ITipoGastoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoGasto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoGasto> tipos_gastos = new ArrayList<>();
            tipos_gastos.add(new TipoGasto("TGA011907000001", "ALIMENTACION"));
            tipos_gastos.add(new TipoGasto("TGA011907000002", "SALUD"));
            tipos_gastos.add(new TipoGasto("TGA011907000003", "VIVIENDA"));
            tipos_gastos.add(new TipoGasto("TGA011907000004", "VESTIMENTA"));
            tipos_gastos.add(new TipoGasto("TGA011907000004", "EDUCACION"));
            tipos_gastos.add(new TipoGasto("TGA011907000004", "ARTE"));
            tipos_gastos.add(new TipoGasto("TGA011907000004", "CULTURA"));
            rep.saveAll(tipos_gastos);
        }
    }
}
