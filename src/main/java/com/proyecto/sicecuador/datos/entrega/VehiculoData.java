package com.proyecto.sicecuador.datos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.modelos.entrega.Vehiculo;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
import com.proyecto.sicecuador.repositorios.entrega.IVehiculoRepository;
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
@Order(37)
@Profile({"dev","prod"})
public class VehiculoData implements ApplicationRunner {
    @Autowired
    private IVehiculoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Vehiculo> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Vehiculo> vehiculosTransportes = new ArrayList<>();
            vehiculosTransportes.add(new Vehiculo("VTR012306000001","AAA-4521","001","FORD","FIESTA", "2020", "1500", "ALTA", "ROJA", "2019", Constantes.activo, new Empresa(1)));
            vehiculosTransportes.add(new Vehiculo("VTR012306000002","AAA-4211","002","MAZDA","IZ35", "2020", "1500", "ALTA", "BLANCA", "2019", Constantes.activo, new Empresa(1)));

            vehiculosTransportes.add(new Vehiculo("VTR022306000001", "AAA-4963", "003", "CHEVROLET","TACKER", "2020", "1500", "ALTA", "NEGRA", "2019", Constantes.activo, new Empresa(2)));
            vehiculosTransportes.add(new Vehiculo("VTR022306000002", "AAA-4263", "004", "FORD","ESCAPE", "2020", "2000", "ALTA", "NEGRA", "2019", Constantes.activo, new Empresa(2)));

            rep.saveAll(vehiculosTransportes);
        }
    }
}
