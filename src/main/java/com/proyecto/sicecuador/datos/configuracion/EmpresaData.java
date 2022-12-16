package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.repositorios.configuracion.IEmpresaRepository;
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
@Order(15)
@Profile({"dev","prod"})
public class EmpresaData implements ApplicationRunner {
    @Autowired
    private IEmpresaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Empresa> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Empresa> empresas = new ArrayList<>();
            empresas.add(new Empresa("EMP011912001", "0603467226001", "RIO TOUR S.A.", "NOMBRE COMERCIAL RIO TOUR S.A", "empresa1.jpg", "NO", "ACTIVO", new TipoIdentificacion(1), new Direccion(1)));
            rep.saveAll(empresas);
        }
    }
}
