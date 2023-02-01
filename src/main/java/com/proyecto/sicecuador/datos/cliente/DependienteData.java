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

            dependientes.add(new Dependiente("DEP011907000001", "DEPENDIENTE 1 DE CLIENTE A", "CARRERA 4 # 4-4", 0, 0, Constantes.activo, new Ubicacion(1), new Cliente(1)));
            dependientes.add(new Dependiente("DEP011908000002", "DEPENDIENTE 2 DE CLIENTE A", "CARRERA 5 # 5-5", 0, 0, Constantes.activo, new Ubicacion(2), new Cliente(1)));
            dependientes.add(new Dependiente("DEP011908000003", "DEPENDIENTE 1 DE CLIENTE B", "CARRERA 6 # 6-6", 0, 0, Constantes.activo, new Ubicacion(3), new Cliente(2)));
            dependientes.add(new Dependiente("DEP011908000004", "DEPENDIENTE 2 DE CLIENTE B", "CARRERA 7 # 7-7", 0, 0, Constantes.activo, new Ubicacion(4), new Cliente(2)));
            dependientes.add(new Dependiente("DEP011908000005", "DEPENDIENTE 2 DE CLIENTE B", "CARRERA 8 # 8-8", 0, 0, Constantes.activo, new Ubicacion(5), new Cliente(2)));
            dependientes.add(new Dependiente("DEP011908000006", "DEPENDIENTE 2 DE CLIENTE B", "CARRERA 9 # 9-9", 0, 0, Constantes.activo, new Ubicacion(6), new Cliente(2)));

            rep.saveAll(dependientes);
        }
    }
}
