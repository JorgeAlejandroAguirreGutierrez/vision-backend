package com.proyecto.sicecuador.datos.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.MovimientoContable;
import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.repositorios.contabilidad.IMovimientoContableRepository;

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
@Order(29)
@Profile({"dev","prod"})
public class MovimientoContableData implements ApplicationRunner {
    @Autowired
    private IMovimientoContableRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<MovimientoContable> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {

            List<MovimientoContable> movimientos_contables = new ArrayList<>();
            movimientos_contables.add(new MovimientoContable("MVC011907000001",new AfectacionContable(1), new CuentaContable(1),new CuentaContable(2),new CuentaContable(3),new CuentaContable(1), new CuentaContable(2), new CuentaContable(3), new CuentaContable(3), new CuentaContable(3),"ACTIVO"));

            rep.saveAll(movimientos_contables);
        }
    }
}
