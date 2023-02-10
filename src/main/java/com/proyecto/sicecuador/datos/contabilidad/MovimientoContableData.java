package com.proyecto.sicecuador.datos.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.MovimientoContable;
import com.proyecto.sicecuador.Constantes;
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
            List<MovimientoContable> movimientosContables = new ArrayList<>();
            movimientosContables.add(new MovimientoContable("MVC011907000001", Constantes.activo, new AfectacionContable(1), new CuentaContable(1),new CuentaContable(2),new CuentaContable(3),new CuentaContable(5), new CuentaContable(6), new CuentaContable(4), new CuentaContable(5), new CuentaContable(1)));
            movimientosContables.add(new MovimientoContable("MVC011907000002", Constantes.activo, new AfectacionContable(1), new CuentaContable(1),new CuentaContable(2),new CuentaContable(3),new CuentaContable(5), new CuentaContable(6), new CuentaContable(4), new CuentaContable(5), new CuentaContable(2)));
            movimientosContables.add(new MovimientoContable("MVC011907000003", Constantes.activo, new AfectacionContable(1), new CuentaContable(1),new CuentaContable(3),new CuentaContable(3),new CuentaContable(4), new CuentaContable(1), new CuentaContable(4), new CuentaContable(5), new CuentaContable(3)));
            movimientosContables.add(new MovimientoContable("MVC011907000004", Constantes.activo, new AfectacionContable(1), new CuentaContable(2),new CuentaContable(3),new CuentaContable(5),new CuentaContable(4), new CuentaContable(1), new CuentaContable(3), new CuentaContable(6), new CuentaContable(4)));
            movimientosContables.add(new MovimientoContable("MVC011907000005", Constantes.activo, new AfectacionContable(2), new CuentaContable(2),new CuentaContable(4),new CuentaContable(5),new CuentaContable(3), new CuentaContable(2), new CuentaContable(3), new CuentaContable(6), new CuentaContable(5)));
            movimientosContables.add(new MovimientoContable("MVC011907000006", Constantes.activo, new AfectacionContable(2), new CuentaContable(2),new CuentaContable(4),new CuentaContable(5),new CuentaContable(3), new CuentaContable(2), new CuentaContable(3), new CuentaContable(6), new CuentaContable(6)));
            rep.saveAll(movimientosContables);
        }
    }
}
