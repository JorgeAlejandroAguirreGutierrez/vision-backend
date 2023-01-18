package com.proyecto.sicecuador.datos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;
import com.proyecto.sicecuador.repositorios.entrega.IVehiculoTransporteRepository;
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
@Order(35)
@Profile({"dev","prod"})
public class VehiculoTransporteData implements ApplicationRunner {
    @Autowired
    private IVehiculoTransporteRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<VehiculoTransporte> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<VehiculoTransporte> vehiculos_transportes = new ArrayList<>();
            vehiculos_transportes.add(new VehiculoTransporte("VTR001","AAA-4521","001","FORD","FIESTA", "2020", "1500", "ALTA", "ROJA", "2019", Constantes.activo));
            vehiculos_transportes.add(new VehiculoTransporte("VTR002","AAA-4211","002","MAZDA","IZ35", "2020", "1500", "ALTA", "BLANCA", "2019", Constantes.activo));
            vehiculos_transportes.add(new VehiculoTransporte("VTR003", "AAA-4963", "003", "CHEVROLET","TACKER", "2020", "1500", "ALTA", "NEGRA", "2019", Constantes.activo));
            vehiculos_transportes.add(new VehiculoTransporte("VTR004", "AAA-4263", "004", "FORD","ESCAPE", "2020", "2000", "ALTA", "NEGRA", "2019", Constantes.activo));
            rep.saveAll(vehiculos_transportes);
        }
    }
}
