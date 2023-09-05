package com.proyecto.vision.datos.cliente;

import com.proyecto.vision.modelos.cliente.Contribuyente;
import com.proyecto.vision.modelos.cliente.TipoContribuyente;
import com.proyecto.vision.modelos.configuracion.Ubicacion;
import com.proyecto.vision.repositorios.cliente.IContribuyenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(20)
@Profile({"dev","prod"})
public class ContribuyenteData implements ApplicationRunner {
    @Autowired
    private IContribuyenteRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Contribuyente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Contribuyente> contribuyentes = new ArrayList<>();
            contribuyentes.add(new Contribuyente("0490006958001", "PANTOJA MUNOZ FERNANDO MARCELO", "CHIMBORAZO", "SUSPENDIDO", "OTROS",  Date.valueOf("1972-02-05"), null,  Date.valueOf("2006-09-13"), Date.valueOf("1986-10-01"), "NO", 1, "", "CERRADO", "PICHINCHA", "QUITO", "LA CONCEPCIÓN", "C141004", new TipoContribuyente(1), new Ubicacion(980)));
            contribuyentes.add(new Contribuyente("0401125521001", "MONTENEGRO BENITEZ GRACE SOFIA", "CHIMBORAZO", "SUSPENDIDO", "OTROS",  Date.valueOf("1999-12-10"),  Date.valueOf("2006-09-28"), Date.valueOf("2018-07-31"),null, "NO", 1, "", "CERRADO", "CARCHI", "TULCAN", "GONZALEZ SUAREZ", "G464112", new TipoContribuyente(1), new Ubicacion(158)));
            contribuyentes.add(new Contribuyente("0401020961001", "ARGOTI SANTACRUZ LORENA MATILDE", "CHIMBORAZO", "ACTIVO", "OTROS",  Date.valueOf("1999-12-01"),  Date.valueOf("2016-05-05"),null,  Date.valueOf("2015-06-02"), "NO", 1, "", "ABIERTO", "CHIMBORAZO", "RIOBAMBA", "VELASCO", "M702004", new TipoContribuyente(1), new Ubicacion(245)));
            contribuyentes.add(new Contribuyente("0400864146001", "PANTOJA ORTIZ OSCAR RUBEN", "CHIMBORAZO", "INACTIVO", "OTROS",  Date.valueOf("1999-07-01"),  Date.valueOf("2011-04-29"), Date.valueOf("2011-06-21"), Date.valueOf("2011-04-29"), "NO", 1, "EL GRANJERITO", "CERRADO", "CHIMBORAZO", "RIOBAMBA", "LIZARZABURU", "H492202", new TipoContribuyente(1), new Ubicacion(243)));
            contribuyentes.add(new Contribuyente("0400821377001", "CAMPAÑA OSEJO HAROLD LEONEL", "CHIMBORAZO", "ACTIVO", "OTROS",  Date.valueOf("1997-12-08"),  Date.valueOf("2018-02-01"),null,  Date.valueOf("2018-02-01"), "NO", 1, "", "ABIERTO", "CHIMBORAZO", "RIOBAMBA", "SAN JUAN", "M711024", new TipoContribuyente(1), new Ubicacion(258)));
            contribuyentes.add(new Contribuyente("0400783163001", "NARVAEZ MARTINEZ JAIME EFRAIN", "CHIMBORAZO", "SUSPENDIDO", "OTROS",  Date.valueOf("1998-12-15"),  Date.valueOf("2002-03-19"), Date.valueOf("2008-10-13"),null, "NO", 1, "PINCHOS BURGUER", "CERRADO", "CHIMBORAZO", "RIOBAMBA", "VELASCO", "I563002", new TipoContribuyente(1), new Ubicacion(245)));
            contribuyentes.add(new Contribuyente("0400744173001", "SIERRA LIMA ALFONSO JOSELITO", "CHIMBORAZO", "ACTIVO", "RIMPE",  Date.valueOf("1999-08-09"),  Date.valueOf("2023-03-22"),null, null, "SI", 1, "LA PECEBRERA", "CERRADO", "CHIMBORAZO", "RIOBAMBA", "VELASCO", "G464922",  new TipoContribuyente(1), new Ubicacion(245)));
            contribuyentes.add(new Contribuyente("0400734141001", "GUIJARRO GARZON GINNA ELIZABETH", "CHIMBORAZO", "ACTIVO", "RIMPE",  Date.valueOf("1999-07-29"),  Date.valueOf("2023-01-05"), Date.valueOf("2020-02-06"), Date.valueOf("2023-01-05"), "NO", 1, "", "CERRADO", "CARCHI", "TULCAN", "GONZALEZ SUAREZ", "G471101",  new TipoContribuyente(1), new Ubicacion(158)));
            contribuyentes.add(new Contribuyente("0400715157001", "ARMAS VINUEZA AMBROCIO RAMIRO", "CHIMBORAZO", "SUSPENDIDO", "OTROS",  Date.valueOf("1999-10-29"),  Date.valueOf("2016-02-29"), Date.valueOf("2016-08-17"), Date.valueOf("2016-02-29"), "NO", 1, "", "CERRADO", "CHIMBORAZO", "RIOBAMBA", "SAN LUIS", "M702001", new TipoContribuyente(1), new Ubicacion(259)));

            rep.saveAll(contribuyentes);
        }
    }
}
