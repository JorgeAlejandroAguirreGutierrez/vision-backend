package com.proyecto.vision.datos.cajaBanco;

import com.proyecto.vision.modelos.cajaBanco.CuentaPropia;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.cajaBanco.ICuentaPropiaRepository;
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
            cuentas_propias.add(new CuentaPropia("CTP012305000001","CORRIENTE","MARIO DELGADO","0000000001", Constantes.estadoActivo, new Banco(1), new Empresa(1)));
            cuentas_propias.add(new CuentaPropia("CTP012305000002","CORRIENTE","ALEJANDRO GOMEZ","0000000002", Constantes.estadoActivo, new Banco(2), new Empresa(1)));
            cuentas_propias.add(new CuentaPropia("CTP012305000003","AHORROS", "JORGE HIDALGO","0000000003", Constantes.estadoActivo, new Banco(3), new Empresa(1)));

            cuentas_propias.add(new CuentaPropia("CTP022305000001","AHORROS","FUTURISTIC S.A.","0000000004", Constantes.estadoActivo, new Banco(4), new Empresa(2)));

            cuentas_propias.add(new CuentaPropia("CTP032309000001","AHORROS","DIANA MOLINA","20001056989", Constantes.estadoActivo, new Banco(11), new Empresa(3)));
            rep.saveAll(cuentas_propias);
        }
    }
}
