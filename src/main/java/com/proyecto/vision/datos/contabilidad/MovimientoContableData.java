package com.proyecto.vision.datos.contabilidad;

import com.proyecto.vision.modelos.contabilidad.MovimientoContable;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.contabilidad.AfectacionContable;
import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.contabilidad.IMovimientoContableRepository;

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
            movimientosContables.add(new MovimientoContable("MVC011907000001", Constantes.estadoActivo, new AfectacionContable(1), new CuentaContable(1),new CuentaContable(2),new CuentaContable(3),new CuentaContable(5), new CuentaContable(6), new CuentaContable(4), new CuentaContable(5), new CuentaContable(1), new Empresa(1)));
            movimientosContables.add(new MovimientoContable("MVC011907000002", Constantes.estadoActivo, new AfectacionContable(1), new CuentaContable(1),new CuentaContable(2),new CuentaContable(3),new CuentaContable(5), new CuentaContable(6), new CuentaContable(4), new CuentaContable(5), new CuentaContable(2), new Empresa(1)));
            movimientosContables.add(new MovimientoContable("MVC011907000003", Constantes.estadoActivo, new AfectacionContable(1), new CuentaContable(1),new CuentaContable(3),new CuentaContable(3),new CuentaContable(4), new CuentaContable(1), new CuentaContable(4), new CuentaContable(5), new CuentaContable(3), new Empresa(1)));
            movimientosContables.add(new MovimientoContable("MVC011907000004", Constantes.estadoActivo, new AfectacionContable(1), new CuentaContable(2),new CuentaContable(3),new CuentaContable(5),new CuentaContable(4), new CuentaContable(1), new CuentaContable(3), new CuentaContable(6), new CuentaContable(4), new Empresa(1)));
            movimientosContables.add(new MovimientoContable("MVC011907000005", Constantes.estadoActivo, new AfectacionContable(2), new CuentaContable(2),new CuentaContable(4),new CuentaContable(5),new CuentaContable(3), new CuentaContable(2), new CuentaContable(3), new CuentaContable(6), new CuentaContable(5), new Empresa(1)));
            movimientosContables.add(new MovimientoContable("MVC011907000006", Constantes.estadoActivo, new AfectacionContable(2), new CuentaContable(2),new CuentaContable(4),new CuentaContable(5),new CuentaContable(3), new CuentaContable(2), new CuentaContable(3), new CuentaContable(6), new CuentaContable(6), new Empresa(1)));

            movimientosContables.add(new MovimientoContable("MVC011907000007", Constantes.estadoActivo, new AfectacionContable(1), new CuentaContable(1),new CuentaContable(2),new CuentaContable(3),new CuentaContable(5), new CuentaContable(6), new CuentaContable(4), new CuentaContable(5), new CuentaContable(1), new Empresa(2)));
            movimientosContables.add(new MovimientoContable("MVC011907000008", Constantes.estadoActivo, new AfectacionContable(1), new CuentaContable(1),new CuentaContable(2),new CuentaContable(3),new CuentaContable(5), new CuentaContable(6), new CuentaContable(4), new CuentaContable(5), new CuentaContable(2), new Empresa(2)));
            movimientosContables.add(new MovimientoContable("MVC011907000009", Constantes.estadoActivo, new AfectacionContable(1), new CuentaContable(1),new CuentaContable(3),new CuentaContable(3),new CuentaContable(4), new CuentaContable(1), new CuentaContable(4), new CuentaContable(5), new CuentaContable(3), new Empresa(2)));
            movimientosContables.add(new MovimientoContable("MVC011907000010", Constantes.estadoActivo, new AfectacionContable(1), new CuentaContable(2),new CuentaContable(3),new CuentaContable(5),new CuentaContable(4), new CuentaContable(1), new CuentaContable(3), new CuentaContable(6), new CuentaContable(4), new Empresa(2)));
            movimientosContables.add(new MovimientoContable("MVC011907000011", Constantes.estadoActivo, new AfectacionContable(2), new CuentaContable(2),new CuentaContable(4),new CuentaContable(5),new CuentaContable(3), new CuentaContable(2), new CuentaContable(3), new CuentaContable(6), new CuentaContable(5), new Empresa(2)));
            movimientosContables.add(new MovimientoContable("MVC011907000012", Constantes.estadoActivo, new AfectacionContable(2), new CuentaContable(2),new CuentaContable(4),new CuentaContable(5),new CuentaContable(3), new CuentaContable(2), new CuentaContable(3), new CuentaContable(6), new CuentaContable(6), new Empresa(2)));
            rep.saveAll(movimientosContables);
        }
    }
}
