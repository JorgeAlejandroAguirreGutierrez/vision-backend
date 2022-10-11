package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.repositorios.inventario.ITipoGastoRepository;
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
            tipos_gastos.add(new TipoGasto("TGA011907000001", "NO APLICA","N/A","ACTIVO"));
            tipos_gastos.add(new TipoGasto("TGA011907000002", "ALIMENTACION","AL","ACTIVO"));
            tipos_gastos.add(new TipoGasto("TGA011907000003", "SALUD","SA","ACTIVO"));
            tipos_gastos.add(new TipoGasto("TGA011907000004", "VIVIENDA","VI","ACTIVO"));
            tipos_gastos.add(new TipoGasto("TGA011907000005", "VESTIMENTA","VE","ACTIVO"));
            tipos_gastos.add(new TipoGasto("TGA011907000006", "EDUCACION","ED","ACTIVO"));
            tipos_gastos.add(new TipoGasto("TGA011907000007", "ARTE","AR","ACTIVO"));
            tipos_gastos.add(new TipoGasto("TGA011907000008", "CULTURA","CU","ACTIVO"));
            rep.saveAll(tipos_gastos);
        }
    }
}
