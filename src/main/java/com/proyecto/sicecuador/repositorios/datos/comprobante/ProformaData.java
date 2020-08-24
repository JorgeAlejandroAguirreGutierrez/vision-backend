package com.proyecto.sicecuador.repositorios.datos.comprobante;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.Pedido;
import com.proyecto.sicecuador.modelos.comprobante.Proforma;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IPedidoRepository;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IProformaRepository;
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
@Order(43)
@Profile({"dev","prod"})
public class ProformaData implements ApplicationRunner {
    @Autowired
    private IProformaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Proforma> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Proforma> pedidos = new ArrayList<>();
            pedidos.add(new Proforma("PRO000001", "001-001-000001", Date.valueOf("2019-08-23"), Date.valueOf("2019-09-23"), "VIGENTE", 91.43, 3.07, 71.43, 20.00, 8.57, 100.00, 1.1857, 1.20, "Proforma Vigente", new Cliente(1), new Usuario(3)));
            pedidos.add(new Proforma("PRO000002", "001-001-000002", Date.valueOf("2019-08-22"), Date.valueOf("2019-09-22"), "CADUCADA", 178.57, 1.43, 178.57, 0.00, 21.43, 200.00, 1.1, 2.20, "Proforma Caducada", new Cliente(2), new Usuario(2)));
            rep.saveAll(pedidos);
        }
    }
}
