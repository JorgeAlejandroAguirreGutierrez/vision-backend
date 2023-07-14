package com.proyecto.vision.datos.entrega;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.TipoIdentificacion;
import com.proyecto.vision.modelos.entrega.Transportista;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.entrega.ITransportistaRepository;
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
            transportistas.add(new Transportista("TRA012306000001", "ABAD NIETO PABLO MARCELO", "1303753618", Constantes.estadoActivo, new TipoIdentificacion(1), new Empresa(1)));
            transportistas.add(new Transportista("TRA012306000002", "ABATA REINOSO BELLA NARCISA DEL PILAR", "1706172648", Constantes.estadoActivo, new TipoIdentificacion(1), new Empresa(1)));

            transportistas.add(new Transportista("TRA022306000001", "ACEVEDO PALACIO SONIA CECILIA", "1704997012", Constantes.estadoActivo, new TipoIdentificacion(1), new Empresa(2)));
            transportistas.add(new Transportista("TRA022306000002", "AGUILAR PAZMIÑO SHEILA DAYAN", "1715241434", Constantes.estadoActivo, new TipoIdentificacion(1), new Empresa(2)));

            rep.saveAll(transportistas);
        }
    }
}
