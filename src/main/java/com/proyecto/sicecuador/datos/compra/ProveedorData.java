package com.proyecto.sicecuador.datos.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.modelos.cliente.TipoContribuyente;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.compra.*;
import com.proyecto.sicecuador.repositorios.compra.IProveedorRepository;
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
@Order(40)
@Profile({"dev","prod"})
public class ProveedorData implements ApplicationRunner {
    @Autowired
    private IProveedorRepository rep;
        @Override
        public void run(ApplicationArguments args) throws Exception {
            Optional<Proveedor> ant=rep.findById((long) 1);
            if (!ant.isPresent()) {
                List<Proveedor> proveedores = new ArrayList<>();
                proveedores.add(new Proveedor("PRV110721000001", "0101010101", "CORAL CENTRO", "NOMBRE COMERCIAL CORAL", "CALLE SUCRE Y VEGA MUÑOZ", "TRAS LA IGLESIA", 0, 0, 0, Constantes.si, Constantes.no, Constantes.no, Constantes.no, Constantes.activo, new TipoIdentificacion(1), new TipoContribuyente(1), new GrupoProveedor(1), new FormaPago(1), new PlazoCredito(1), new Ubicacion(1), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
                proveedores.add(new Proveedor("PRV110721000002", "0101010102", "SUPERMAXI", "NOMBRE COMERCIAL SUPERMAXI", "AV HURTADO DE MENDOZA", "TRAS EL MERCADO", 0, 0, 0, Constantes.si, Constantes.no, Constantes.no, Constantes.no, Constantes.activo, new TipoIdentificacion(1), new TipoContribuyente(1), new GrupoProveedor(1), new FormaPago(1), new PlazoCredito(1), new Ubicacion(1), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
                rep.saveAll(proveedores);
            }
    }
}
