package com.proyecto.sicecuador.datos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
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
            List<VehiculoTransporte> vehiculosTransportes1 = new ArrayList<>();
            vehiculosTransportes1.add(new VehiculoTransporte("VTR001","AAA-4521","001","FORD","FIESTA", "2020", "1500", "ALTA", "ROJA", "2019", Constantes.activo));
            transportistas.add(new Transportista("TRA001", "ABAD NIETO PABLO MARCELO", "1303753618", Constantes.si, Constantes.activo, new TipoIdentificacion(1), new Empresa(1), vehiculosTransportes1));
            List<VehiculoTransporte> vehiculosTransportes2 = new ArrayList<>();
            vehiculosTransportes2.add(new VehiculoTransporte("VTR002","AAA-4211","002","MAZDA","IZ35", "2020", "1500", "ALTA", "BLANCA", "2019", Constantes.activo));
            transportistas.add(new Transportista("TRA002", "ABATA REINOSO BELLA NARCISA DEL PILAR", "1706172648", Constantes.no, Constantes.activo, new TipoIdentificacion(1), new Empresa(1), vehiculosTransportes2));
            List<VehiculoTransporte> vehiculosTransportes3 = new ArrayList<>();
            vehiculosTransportes3.add(new VehiculoTransporte("VTR003", "AAA-4963", "003", "CHEVROLET","TACKER", "2020", "1500", "ALTA", "NEGRA", "2019", Constantes.activo));
            transportistas.add(new Transportista("TRA003", "ACEVEDO PALACIO SONIA CECILIA", "1704997012", Constantes.si, Constantes.activo, new TipoIdentificacion(1), new Empresa(2), vehiculosTransportes3));
            List<VehiculoTransporte> vehiculosTransportes4 = new ArrayList<>();
            vehiculosTransportes4.add(new VehiculoTransporte("VTR004", "AAA-4263", "004", "FORD","ESCAPE", "2020", "2000", "ALTA", "NEGRA", "2019", Constantes.activo));
            transportistas.add(new Transportista("TRA004", "AGUILAR PAZMIÑO SHEILA DAYAN", "1715241434", Constantes.no, Constantes.activo, new TipoIdentificacion(1), new Empresa(2), vehiculosTransportes4));
            rep.saveAll(transportistas);
        }
    }
}
