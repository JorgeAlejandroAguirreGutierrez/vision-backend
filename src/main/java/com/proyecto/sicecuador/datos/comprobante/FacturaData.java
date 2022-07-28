package com.proyecto.sicecuador.datos.comprobante;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.Egreso;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.comprobante.IFacturaRepository;
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
@Order(44)
@Profile({"dev","prod"})
public class FacturaData implements ApplicationRunner {
    @Autowired
    private IFacturaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<Factura> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Factura> facturas = new ArrayList<>();
            facturas.add(new Factura("FAC000001", "001-001-000001", Date.valueOf("2019-08-22"), "true", "clave acceso sri", 660.00, 60.00, 5.00, 33.00, 93.80, 4, 20, 202.00, 183.34, 174.66, 22.00, 480.00, "El subdecuento aplicado es al subtotal y al total", new Cliente(1), new Cliente(1), new Auxiliar(1), new Usuario(1), new Sesion(1)));
            facturas.add(new Factura("FAC000002", "001-001-000002", Date.valueOf("2019-08-22"), "true", "clave acceso sri", 610.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 21.50, 256.00, 332.50, 30.72, 619.22, "Se aplican descuentos individuales que constan en el detalle factura", new Cliente(1), new Cliente(3), new Auxiliar(2), new Usuario(1), new Sesion(1)));
            facturas.add(new Factura("FAC000003", "001-001-000003", Date.valueOf("2019-08-22"), "false", "clave acceso sri",660.00, 60.00, 5.00, 33.00, 93.80, 4.00, 20.00, 202.00, 183.34, 174.66, 22.00, 480.00, "Se anula por solicitud del cliente", new Cliente(1), new Cliente(3), new Auxiliar(2), new Usuario(3), new Sesion(1)));
            rep.saveAll(facturas);
        }*/
    }
}
