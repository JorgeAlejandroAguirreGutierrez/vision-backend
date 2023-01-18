package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.cliente.IDependienteRepository;
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
@Order(21)
@Profile({"dev","prod"})
public class DependienteData implements ApplicationRunner {

    @Autowired
    private IDependienteRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Dependiente> ant=rep.findById((long) 1);
        if (!ant.isPresent()){
            List<Dependiente> dependientes=new ArrayList<>();

            dependientes.add(new Dependiente("DEP011907000001", "DEPENDIENTE 1 DE CLIENTE A", Constantes.activo, "CARRERA 4 # 4-4", new Ubicacion(1), new Cliente(1)));
            dependientes.add(new Dependiente("DEP011908000002", "DEPENDIENTE 2 DE CLIENTE A", Constantes.activo, "CARRERA 5 # 5-5", new Ubicacion(1), new Cliente(1)));
            dependientes.add(new Dependiente("DEP011908000003", "DEPENDIENTE 1 DE CLIENTE B", Constantes.activo, "CARRERA 6 # 6-6", new Ubicacion(1), new Cliente(2)));
            dependientes.add(new Dependiente("DEP011908000004", "DEPENDIENTE 2 DE CLIENTE B", Constantes.activo, "CARRERA 7 # 7-7", new Ubicacion(1), new Cliente(2)));
            dependientes.add(new Dependiente("DEP011908000005", "DEPENDIENTE 2 DE CLIENTE B", Constantes.activo, "CARRERA 8 # 8-8", new Ubicacion(1), new Cliente(2)));
            dependientes.add(new Dependiente("DEP011908000006", "DEPENDIENTE 2 DE CLIENTE B", Constantes.activo, "CARRERA 9 # 9-9", new Ubicacion(1), new Cliente(2)));

            rep.saveAll(dependientes);
        }
    }
}
