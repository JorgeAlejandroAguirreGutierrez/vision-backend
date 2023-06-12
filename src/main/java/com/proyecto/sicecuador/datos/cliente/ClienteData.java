package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.EstadoCivil;
import com.proyecto.sicecuador.modelos.configuracion.Genero;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.cliente.Segmento;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
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

            List<Celular> celulares1 = new ArrayList<>();
            celulares1.add(new Celular("CEL012306000001", "09-8765-4321", new Cliente(1)));
            List<Telefono> telefonos1 = new ArrayList<>();
            telefonos1.add(new Telefono("TEL012306000001", "03-2964-123", new Cliente(1)));
            List<Correo> correos1 = new ArrayList<>();
            correos1.add(new Correo("COR012306000001", "consumidorfinal@futuristic.com.ec", new Cliente(1)));

            clientes.add(new Cliente("CLI012306000001", "9999999999999", "CONSUMIDOR FINAL", Constantes.no, Constantes.no, Constantes.activo, "CONSUMIDOR FINAL", "CASA", "", -1.6719601146175827, -78.65041698970857,
                    0, new TipoIdentificacion(4), new TipoContribuyente(2), new GrupoCliente(1), new FormaPago(1), new PlazoCredito(1), new Ubicacion(248), new Genero(1),
                    new EstadoCivil(1), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(1), new Empresa(1), Collections.emptyList(), telefonos1, celulares1, correos1, Collections.emptyList()));

            List<Celular> celulares2 = new ArrayList<>();
            celulares2.add(new Celular("CEL011909000003", "09-6543-1234", new Cliente(2)));
            List<Telefono> telefonos2 = new ArrayList<>();
            telefonos2.add(new Telefono("TEL011907000002", "03-2424-344", new Cliente(2)));
            List<Correo> correos2 = new ArrayList<>();
            correos2.add(new Correo("COR011908000002", "alejo@gmail.com", new Cliente(2)));

            clientes.add(new Cliente("CLI012306000002", "1160000240001", "CLIENTE B", Constantes.no, Constantes.si, Constantes.activo, "CARRERA 2 # 2-2", "", "CERCA A LA IGLESIA PATRIA", -1.6719601146175827, -78.65041698970857,
                    0, new TipoIdentificacion(1), new TipoContribuyente(2), new GrupoCliente(2), new FormaPago(2), new PlazoCredito(2), new Ubicacion(100), new Genero(1),
                    new EstadoCivil(1), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(2), new Empresa(1), Collections.emptyList(), telefonos2, celulares2, correos2, Collections.emptyList()));

            List<Celular> celulares3 = new ArrayList<>();
            celulares3.add(new Celular("CEL011909000003", "09-6543-1231", new Cliente(2)));
            List<Telefono> telefonos3 = new ArrayList<>();
            telefonos3.add(new Telefono("TEL011907000002", "03-2424-341", new Cliente(2)));
            List<Correo> correos3 = new ArrayList<>();
            correos3.add(new Correo("COR011908000002", "cliente3@gmail.com", new Cliente(2)));

            clientes.add(new Cliente("CLI012306000003", "1768038860001", "CLIENTE A", Constantes.no, Constantes.si, Constantes.activo, "CARRERA 1 # 1-1", "","CERCA A LA IGLESIA NOE", -1.6719601146175827, -78.65041698970857,
                    0, new TipoIdentificacion(1), new TipoContribuyente(1), new GrupoCliente(1), new FormaPago(1), new PlazoCredito(1), new Ubicacion(10), new Genero(2),
                    new EstadoCivil(2), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(1), new Empresa(1), Collections.emptyList(), telefonos3, celulares3, correos3, Collections.emptyList()));

            clientes.add(new Cliente("CLI022306000001", "9999999999999", "CONSUMIDOR FINAL", Constantes.no, Constantes.no, Constantes.activo, "RIOBAMBA (DIRECCIÓN GENERAL)", "CASA", "", -1.6719601146175827, -78.65041698970857,
                    0, new TipoIdentificacion(4), new TipoContribuyente(2), new GrupoCliente(1), new FormaPago(1), new PlazoCredito(1), new Ubicacion(248), new Genero(1),
                    new EstadoCivil(1), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(1), new Empresa(2), Collections.emptyList(), telefonos1, celulares1, correos1, Collections.emptyList()));

            rep.saveAll(clientes);
        }
    }
}
