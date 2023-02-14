package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Order(20)
@Profile({"dev","prod"})
public class ClienteData implements ApplicationRunner {
    @Autowired
    private IClienteRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Cliente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Cliente> clientes = new ArrayList<>();
            clientes.add(new Cliente("CLI011907000001", "1768038860001", "CLIENTE A", Constantes.no, Constantes.si, Constantes.activo, "CARRERA 1 # 1-1", "","CERCA A LA IGLESIA NOE", -1.6719601146175827, -78.65041698970857, 0, new TipoIdentificacion(1), new TipoContribuyente(1), new Estacion(1),
                    new GrupoCliente(1), new FormaPago(1), new PlazoCredito(1), new Ubicacion(1), new Genero(2),
                    new EstadoCivil(2), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(1), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));

            clientes.add(new Cliente("CLI011908000002", "1160000240001", "CLIENTE B", Constantes.no, Constantes.si, Constantes.activo, "CARRERA 2 # 2-2", "", "CERCA A LA IGLESIA PATRIA", -1.6719601146175827, -78.65041698970857, 0, new TipoIdentificacion(1), new TipoContribuyente(2), new Estacion(1),
                    new GrupoCliente(2), new FormaPago(2), new PlazoCredito(2), new Ubicacion(1), new Genero(1),
                    new EstadoCivil(1), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(2), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));

            clientes.add(new Cliente("CLI011909000003", "9999999999999", "CLIENTE CF", Constantes.no, Constantes.si, Constantes.activo, "CARRERA 3 # 3-3", "", "CERCA A LA IGLESIA DEL NORTE", -1.6719601146175827, -78.65041698970857, 0, new TipoIdentificacion(4), new TipoContribuyente(2), new Estacion(1),
                    new GrupoCliente(1), new FormaPago(2), new PlazoCredito(3), new Ubicacion(1), new Genero(2),
                    new EstadoCivil(1), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(3), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
            rep.saveAll(clientes);
        }
    }
}
