package com.proyecto.vision.datos.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.cliente.*;
import com.proyecto.vision.modelos.configuracion.EstadoCivil;
import com.proyecto.vision.modelos.configuracion.Genero;
import com.proyecto.vision.modelos.configuracion.TipoIdentificacion;
import com.proyecto.vision.modelos.configuracion.Ubicacion;
import com.proyecto.vision.repositorios.cliente.IClienteBaseRepository;
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
public class ClienteBaseData implements ApplicationRunner {
    @Autowired
    private IClienteBaseRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<ClienteBase> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<ClienteBase> clientes = new ArrayList<>();
            clientes.add(new ClienteBase("0502685969", "HIDALGO BOURGEAT", "JORGE ESTEBAN", null, "DIRECCION 1 # 1-1","CERCA A LA IGLESIA NOE", "CHIMBORAZO", "RIOBAMBA", "MALDONADO", "032333333", "0987654321", "gato_sta@hotmail.com", Constantes.estadoActivo,
                    "RIOBAMBA", 0, new Ubicacion(1), new Genero(1), new EstadoCivil(2)));

            clientes.add(new ClienteBase("0603468620", "UQUILLAS DONOSO", "IRINA LILIAN", null, "DIRECCION 2 # 1-1","CERCA A LA IGLESIA NOE", "CHIMBORAZO", "RIOBAMBA", "MALDONADO", "032333333", "0987654321", "gato_sta@hotmail.com", Constantes.estadoActivo,
                    "CHAMBO", 0, new Ubicacion(2), new Genero(2), new EstadoCivil(2)));

            clientes.add(new ClienteBase("0603467226", "DELGADO DAQUILEMA", "MARIO RUBEN", null, "CDLA POLITÉCNICA","TRAS EL PARQUE", "CHIMBORAZO", "PENIPE", "PENIPE", "032333333", "0987654321", "master_mds@hotmail.com", Constantes.estadoActivo,
                    "PENIPE", 0, new Ubicacion(3), new Genero(1), new EstadoCivil(1)));
            rep.saveAll(clientes);
        }
    }
}
