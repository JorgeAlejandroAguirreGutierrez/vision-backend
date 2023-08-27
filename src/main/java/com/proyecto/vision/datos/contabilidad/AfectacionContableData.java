package com.proyecto.vision.datos.contabilidad;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.contabilidad.AfectacionContable;
import com.proyecto.vision.repositorios.contabilidad.IAfectacionContableRepository;
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
@Order(28)
@Profile({"dev","prod"})
public class AfectacionContableData implements ApplicationRunner {
    @Autowired
    private IAfectacionContableRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<AfectacionContable> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<AfectacionContable> afectacionesContables = new ArrayList<>();
            afectacionesContables.add(new AfectacionContable("AFC202301000001","INVENTARIO","I", Constantes.estadoActivo));
            afectacionesContables.add(new AfectacionContable("AFC202301000002","CONSUMOS - SUMINISTROS OFICINA","C",Constantes.estadoActivo));
            afectacionesContables.add(new AfectacionContable("AFC202301000003","INVENTARIOS EN CONSIGNACIÓN","IC",Constantes.estadoActivo));
            afectacionesContables.add(new AfectacionContable("AFC202301000004","ACTIVO FIJO","AF",Constantes.estadoActivo));
            rep.saveAll(afectacionesContables);
        }
    }
}
