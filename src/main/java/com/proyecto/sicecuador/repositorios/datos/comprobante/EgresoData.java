package com.proyecto.sicecuador.repositorios.datos.comprobante;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.Egreso;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IEgresoRepository;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(44)
public class EgresoData implements ApplicationRunner {
    @Autowired
    private IEgresoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Egreso> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Egreso> egresos = new ArrayList<>();
            egresos.add(new Egreso("EGR000001", "001-001-000001", Date.valueOf("2019-08-22"), Date.valueOf("2019-08-23"), "EMITIDA", 91.43, 3.07, 71.43, 20.00, 8.57, 100.00, 1.1857, 1.20, 30.00, "Egreso de mercaderia, abona 30.00 usd", new Cliente(1), new Sesion(1), new Usuario(3)));
            egresos.add(new Egreso("EGR000002", "001-001-000002", Date.valueOf("2019-08-22"), Date.valueOf("2019-08-24"), "EMITIDA", 178.57, 1.43, 178.57, 0.00, 21.43, 200.00, 1.1, 2.20, 50.00, "Egreso de mercaderia, abona 50.00 usd", new Cliente(2), new Sesion(1), new Usuario(3)));
            rep.saveAll(egresos);
        }
    }
}
