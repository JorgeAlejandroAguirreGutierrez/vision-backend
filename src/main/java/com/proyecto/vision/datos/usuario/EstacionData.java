package com.proyecto.vision.datos.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.usuario.Establecimiento;
import com.proyecto.vision.modelos.usuario.Estacion;
import com.proyecto.vision.modelos.configuracion.Regimen;
import com.proyecto.vision.repositorios.usuario.IEstacionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(19)
@Profile({"dev","prod"})
public class EstacionData implements ApplicationRunner {
    @Autowired
    private IEstacionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Estacion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Estacion> estaciones = new ArrayList<>();
            estaciones.add(new Estacion("ESN012301000001", "001", "CAJA1", "PC", "192.168.1.1", Constantes.si, Constantes.estadoActivo, new Regimen(1), new Establecimiento(1)));
            estaciones.add(new Estacion("ESN012301000002", "002", "CAJA2", "PC", "192.168.1.2", Constantes.si, Constantes.estadoActivo, null, new Establecimiento(1)));
            estaciones.add(new Estacion("ESN012301000003", "003", "CAJA3", "PC", "192.168.1.3", Constantes.si, Constantes.estadoActivo, new Regimen(3), new Establecimiento(1)));
            estaciones.add(new Estacion("ESN012301000004", "001", "VENTAS1", "TABLET", "192.168.1.4", Constantes.si, Constantes.estadoActivo, null, new Establecimiento(2)));
            estaciones.add(new Estacion("ESN012301000005", "002", "GERENTE-EMP1", "PC", "192.168.1.5", Constantes.no, Constantes.estadoActivo, new Regimen(2), new Establecimiento(2)));
            estaciones.add(new Estacion("ESN012301000006", "001", "TESORERO", "PC", "192.168.1.7", Constantes.no, Constantes.estadoActivo, new Regimen(3), new Establecimiento(3)));
            estaciones.add(new Estacion("ESN012301000007", "002", "VENTAS2", "PORTATIL", "192.168.1.6", Constantes.si, Constantes.estadoActivo, new Regimen(1), new Establecimiento(3)));

            estaciones.add(new Estacion("ESN022301000001", "001", "GERENTE-EMP2", "PC", "192.168.2.1", Constantes.si, Constantes.estadoActivo, new Regimen(1), new Establecimiento(4)));
            estaciones.add(new Estacion("ESN022301000004", "001", "VENTAS1-EMP2", "TABLET", "192.168.2.2", Constantes.si, Constantes.estadoActivo, null, new Establecimiento(5)));
            estaciones.add(new Estacion("ESN022301000005", "", "CONTADOR-EMP2", "PC", "192.168.2.3", Constantes.no, Constantes.estadoActivo, new Regimen(2), new Establecimiento(5)));

            rep.saveAll(estaciones);
        }
    }
}
