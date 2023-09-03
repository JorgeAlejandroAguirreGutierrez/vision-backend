package com.proyecto.vision.datos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.inventario.TipoGasto;
import com.proyecto.vision.repositorios.inventario.ITipoGastoRepository;
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
            List<TipoGasto> tiposGastos = new ArrayList<>();
            tiposGastos.add(new TipoGasto("TGA011907000001", "NO APLICA","N/A", Constantes.estadoActivo));
            tiposGastos.add(new TipoGasto("TGA011907000002", "ALIMENTACION","AL", Constantes.estadoActivo));
            tiposGastos.add(new TipoGasto("TGA011907000003", "SALUD","SA", Constantes.estadoActivo));
            tiposGastos.add(new TipoGasto("TGA011907000004", "VIVIENDA","VI", Constantes.estadoActivo));
            tiposGastos.add(new TipoGasto("TGA011907000005", "VESTIMENTA","VE", Constantes.estadoActivo));
            tiposGastos.add(new TipoGasto("TGA011907000006", "EDUCACION","ED", Constantes.estadoActivo));
            tiposGastos.add(new TipoGasto("TGA011907000007", "ARTE","AR", Constantes.estadoActivo));
            tiposGastos.add(new TipoGasto("TGA011907000008", "CULTURA","CU", Constantes.estadoActivo));
            rep.saveAll(tiposGastos);
        }
    }
}
