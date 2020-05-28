package com.proyecto.sicecuador.repositorios.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Correo;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.interf.cliente.ICorreoRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IDireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(19)
public class DireccionData implements ApplicationRunner {
    @Autowired
    private IDireccionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<Direccion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Direccion> direcciones = new ArrayList<>();
            direcciones.add(new Direccion("DIR011907000001", "CALLE 1 Y CALLE 2", "FRENTE AL PARQUE CENTRAL", "40.75793", "-73.98551", new Ubicacion(1)));
            direcciones.add(new Direccion("DIR011907000002", "CALLE 3 Y CALLE 4", "TRAS UE", "-77.5000000", "-2.0000000", new Ubicacion(2)));
            direcciones.add(new Direccion("DIR011908000003", "CALLE 5 Y CALLE 6", "ESTACION DEL TREN", "-77.5000000", "-2.0000000", new Ubicacion(1)));
            direcciones.add(new Direccion("DIR011907000004", "CALLE 7 Y CALLE 8", "JUNTO AL TERMINAL", "-77.5000000", "-2.0000000", new Ubicacion(2)));
            rep.saveAll(direcciones);
        }*/
    }
}
