package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
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
            Direccion direccion=new Direccion("DIR011907000001", "CALLE 1 Y CALLE 2", "FRENTE AL PARQUE CENTRAL", "40.75793", "-73.98551", new Ubicacion(1));
            Financiamiento financiamiento=new Financiamiento("FIN011907000001", 100.20, new TipoPago(1), new FormaPago(1), new PlazoCredito(1));
            clientes.add(new Cliente("CLI011907000001", "C", "1768038860001", "CLIENTE A", true, "ACTIVO", false, new PuntoVenta(1),
                    new GrupoCliente(1), new TipoContribuyente(1), direccion, financiamiento, new Genero(2),
                    new EstadoCivil(2), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(1)));
            direccion=new Direccion("DIR011907000002", "CALLE 3 Y CALLE 4", "TRAS UE", "-77.5000000", "-2.0000000", new Ubicacion(2));
            financiamiento=new Financiamiento("FIN011908000002", 80.20, new TipoPago(2), new FormaPago(2), new PlazoCredito(2));
            clientes.add(new Cliente("CLI011908000002", "C", "1160000240001", "CLIENTE B", true, "ACTIVO", false, new PuntoVenta(1),
                    new GrupoCliente(2), new TipoContribuyente(2), direccion, financiamiento, new Genero(1),
                    new EstadoCivil(1), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(2)));
            direccion=new Direccion("DIR011908000003", "CALLE 5 Y CALLE 6", "ESTACION DEL TREN", "-77.5000000", "-2.0000000", new Ubicacion(1));
            financiamiento=new Financiamiento("FIN011909000003", 90.20, new TipoPago(2), new FormaPago(2), new PlazoCredito(3));
            clientes.add(new Cliente("CLI011909000003", "C", "9999999999999", "CLIENTE CF", true, "ACTIVO", false, new PuntoVenta(1),
                    new GrupoCliente(1), new TipoContribuyente(2), direccion, financiamiento, new Genero(2),
                    new EstadoCivil(1), new CalificacionCliente(1), new OrigenIngreso(1), new Segmento(3)));
            rep.saveAll(clientes);
        }
    }
}
