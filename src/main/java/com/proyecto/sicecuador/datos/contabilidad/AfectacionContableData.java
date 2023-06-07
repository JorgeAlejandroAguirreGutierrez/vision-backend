package com.proyecto.sicecuador.datos.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
import com.proyecto.sicecuador.repositorios.contabilidad.IAfectacionContableRepository;
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
            afectacionesContables.add(new AfectacionContable("AFC011907000001","INVENTARIO","I", Constantes.activo, new Empresa(1)));
            afectacionesContables.add(new AfectacionContable("AFC011907000002","CONSUMOS - SUMINISTROS OFICINA","C",Constantes.activo, new Empresa(1)));
            afectacionesContables.add(new AfectacionContable("AFC011907000003","INVENTARIOS EN CONSIGNACIÓN","IC",Constantes.activo, new Empresa(2)));
            afectacionesContables.add(new AfectacionContable("AFC011907000004","ACTIVO FIJO","AF",Constantes.activo, new Empresa(2)));
            rep.saveAll(afectacionesContables);
        }
    }
}
