package com.proyecto.sicecuador.datos.entrega;

import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;
import com.proyecto.sicecuador.repositorios.entrega.ITransportistaRepository;
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
@Order(36)
@Profile({"dev","prod"})
public class TransportistaData implements ApplicationRunner {
    @Autowired
    private ITransportistaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Transportista> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Transportista> transportistas = new ArrayList<>();
            transportistas.add(new Transportista("TRA001", "ABAD NIETO PABLO MARCELO", "1303753618", true, new VehiculoTransporte(1)));
            transportistas.add(new Transportista("TRA002", "ABATA REINOSO BELLA NARCISA DEL PILAR", "1706172648", false, new VehiculoTransporte(2)));
            transportistas.add(new Transportista("TRA003", "ACEVEDO PALACIO SONIA CECILIA", "1704997012", true, new VehiculoTransporte(3)));
            transportistas.add(new Transportista("TRA004", "AGUILAR PAZMIÑO SHEILA DAYAN", "1715241434", false, new VehiculoTransporte(4)));
            rep.saveAll(transportistas);
        }
    }
}
