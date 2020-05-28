package com.proyecto.sicecuador.repositorios.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.RetencionCliente;
import com.proyecto.sicecuador.modelos.cliente.Telefono;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.repositorios.interf.cliente.IRetencionClienteRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITelefonoRepository;
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
public class TelefonoData implements ApplicationRunner {
    @Autowired
    private ITelefonoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Telefono> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Telefono> telefonos = new ArrayList<>();
            telefonos.add(new Telefono("TEL011907000001", "032964123", new Cliente(1)));
            telefonos.add(new Telefono("TEL011907000002", "032424344", new Cliente(2)));
            telefonos.add(new Telefono("TEL011907000003", "023987100", new Cliente(3)));
            rep.saveAll(telefonos);
        }
    }
}
