package com.proyecto.sicecuador.datos.cajaBanco;

import com.proyecto.sicecuador.modelos.cajaBanco.CuentaPropia;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cajaBanco.Banco;
import com.proyecto.sicecuador.repositorios.cajaBanco.ICuentaPropiaRepository;
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
            cuentas_propias.add(new CuentaPropia("CTP202305000001","CORRIENTE","MARIO DELGADO","0000000001", Constantes.activo, new Banco(1)));
            cuentas_propias.add(new CuentaPropia("CTP202305000002","CORRIENTE","ALEJANDRO GOMEZ","0000000002", Constantes.activo, new Banco(2)));
            cuentas_propias.add(new CuentaPropia("CTP202305000003","AHORROS", "JORGE HIDALGO","0000000003", Constantes.activo, new Banco(3)));
            cuentas_propias.add(new CuentaPropia("CTP202305000004","AHORROS","FUTURISTIC S.A.","0000000004", Constantes.activo, new Banco(4)));
            rep.saveAll(cuentas_propias);
        }
    }
}
