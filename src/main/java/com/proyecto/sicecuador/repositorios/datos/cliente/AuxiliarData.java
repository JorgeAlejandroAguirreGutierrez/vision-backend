package com.proyecto.sicecuador.repositorios.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
import com.proyecto.sicecuador.repositorios.interf.cliente.IAuxiliarRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(21)
public class AuxiliarData implements ApplicationRunner {

    @Autowired
    private IAuxiliarRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Auxiliar> ant=rep.findById((long) 1);
        if (!ant.isPresent()){
            List<Auxiliar> auxiliares=new ArrayList<>();
            Direccion direccion=new Direccion("DIR011907000004", "CALLE 7 Y CALLE 8", "JUNTO AL TERMINAL", "-77.5000000", "-2.0000000", new Ubicacion(1));
            auxiliares.add(new Auxiliar("AUX011907000001", "AUXILIAR 1 DE CLIENTE A", true, false, new Cliente(1), direccion));
            direccion=new Direccion("DIR011907000005", "CALLE 7 Y CALLE 8", "JUNTO AL COLISEO", "-77.5000000", "-2.0000000", new Ubicacion(2));
            auxiliares.add(new Auxiliar("AUX011908000002", "AUXILIAR 2 DE CLIENTE A", true, false, new Cliente(1), direccion));
            direccion=new Direccion("DIR011907000006", "CALLE 7 Y CALLE 8", "JUNTO A LA PISICINA", "-77.5000000", "-2.0000000", new Ubicacion(3));
            auxiliares.add(new Auxiliar("AUX011908000003", "AUXILIAR 1 DE CLIENTE B", true, false, new Cliente(2), direccion));
            direccion=new Direccion("DIR011907000007", "CALLE 7 Y CALLE 8", "JUNTO A LA CAFETERIA", "-77.5000000", "-2.0000000", new Ubicacion(1));
            auxiliares.add(new Auxiliar("AUX011908000004", "AUXILIAR 2 DE CLIENTE B", true, false, new Cliente(2), direccion));
            direccion=new Direccion("DIR01190700008", "CALLE 7 Y CALLE 8", "JUNTO A LA POLICIA", "-77.5000000", "-2.0000000", new Ubicacion(1));
            auxiliares.add(new Auxiliar("AUX011908000005", "AUXILIAR 2 DE CLIENTE B", true, false, new Cliente(2), direccion));
            direccion=new Direccion("DIR01190700009", "CALLE 7 Y CALLE 8", "JUNTO A LA ALCALDIA", "-77.5000000", "-2.0000000", new Ubicacion(1));
            auxiliares.add(new Auxiliar("AUX011908000006", "AUXILIAR 2 DE CLIENTE B", true, false, new Cliente(2), direccion));
            rep.saveAll(auxiliares);
        }



    }
}
