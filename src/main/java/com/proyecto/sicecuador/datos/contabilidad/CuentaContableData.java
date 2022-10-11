package com.proyecto.sicecuador.datos.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.repositorios.contabilidad.ICuentaContableRepository;
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
@Order(25)
@Profile({"dev","prod"})
public class CuentaContableData implements ApplicationRunner {
    @Autowired
    private ICuentaContableRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CuentaContable> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CuentaContable> cuentas_contables = new ArrayList<>();
            cuentas_contables.add(new CuentaContable("CC011907000001","1.","ACTIVO", "G", 1, true, "CASILLERO", "MAPEO", "ACTIVO"));
            cuentas_contables.add(new CuentaContable("CC011907000002","1.1.","ACTIVO CORRIENTE", "G", 2, true, "CASILLERO", "MAPEO", "ACTIVO"));
            cuentas_contables.add(new CuentaContable("CC011907000003","1.1.01.","EFECTIVO Y EQUIVALENTES AL EFECTIVO", "G", 3, true, "CASILLERO", "MAPEO", "ACTIVO"));
            cuentas_contables.add(new CuentaContable("CC011907000004","1.1.01.01.","CAJA GENERAL", "G", 4, true, "CASILLERO", "MAPEO", "ACTIVO"));
            cuentas_contables.add(new CuentaContable("CC011907000005","1.1.01.01.001","Caja General Chimborazo", "M", 5, true, "CASILLERO", "MAPEO", "ACTIVO"));
            cuentas_contables.add(new CuentaContable("CC011907000006","1.1.01.01.002","Caja General Oficina Central", "M", 5, true, "CASILLERO", "MAPEO", "ACTIVO"));
            rep.saveAll(cuentas_contables);
        }
    }
}
