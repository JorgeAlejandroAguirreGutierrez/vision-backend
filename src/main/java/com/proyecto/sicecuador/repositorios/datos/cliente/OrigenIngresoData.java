package com.proyecto.sicecuador.repositorios.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.repositorios.interf.cliente.IGrupoClienteRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IOrigenIngresoRepository;
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
@Order(10)
@Profile({"dev","prod"})
public class OrigenIngresoData implements ApplicationRunner {
    @Autowired
    private IOrigenIngresoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<OrigenIngreso> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<OrigenIngreso> origenes_ingresos = new ArrayList<>();
            origenes_ingresos.add(new OrigenIngreso("OIN011907000001", "SALARIO", "S"));
            origenes_ingresos.add(new OrigenIngreso("OIN011908000002", "VENTAS", "V"));
            origenes_ingresos.add(new OrigenIngreso("OIN011909000003", "INDEPENDIENTE", "I"));
            rep.saveAll(origenes_ingresos);
        }
    }
}
