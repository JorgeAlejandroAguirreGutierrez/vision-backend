package com.proyecto.sicecuador.datos.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.repositorios.recaudacion.ICuentaPropiaRepository;
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
@Order(26)
@Profile({"dev","prod"})
public class CuentaPropiaData implements ApplicationRunner {
    @Autowired
    private ICuentaPropiaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CuentaPropia> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CuentaPropia> cuentas_propias = new ArrayList<>();
            cuentas_propias.add(new CuentaPropia("CP000001","0000000001", Constantes.activo, new Banco(1)));
            cuentas_propias.add(new CuentaPropia("CP000002","0000000002", Constantes.activo, new Banco(1)));
            cuentas_propias.add(new CuentaPropia("CP000003","0000000003", Constantes.activo, new Banco(2)));
            cuentas_propias.add(new CuentaPropia("CP000004","0000000004", Constantes.activo, new Banco(2)));
            rep.saveAll(cuentas_propias);
        }
    }
}
