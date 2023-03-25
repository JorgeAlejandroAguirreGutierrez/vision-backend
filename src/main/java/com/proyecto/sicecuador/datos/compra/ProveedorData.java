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
                List<CorreoProveedor> correos = new ArrayList<>();
                correos.add(new CorreoProveedor("COA011908000001", "AUXILIAR1@GMAIL.COM", new Proveedor(1)));
                correos.add(new CorreoProveedor("COA011908000002", "AUXILIAR11@GMAIL.COM", new Proveedor(1)));
                List<CelularProveedor> celulares = new ArrayList<>();
                celulares.add(new CelularProveedor("CPR011908000001", "0987654322", new Proveedor(1)));
                celulares.add(new CelularProveedor("CPR011908000002", "0981234563", new Proveedor(1)));
                List<TelefonoProveedor> telefonos = new ArrayList<>();
                telefonos.add(new TelefonoProveedor("TEA011908000001", "032964121", new Proveedor(1)));
                proveedores.add(new Proveedor("PRV110721000001", "0101010101", "CORAL CENTRO", "NOMBRE COMERCIAL CORAL","CALLE SUCRE Y VEGA MUÑOZ", "TRAS LA IGLESIA", -1.6719601146175827, -78.65041698970857, 0, Constantes.si, Constantes.no, Constantes.no, Constantes.no, Constantes.activo, new TipoIdentificacion(1), new TipoContribuyente(1), new GrupoProveedor(1), new FormaPago(1), new PlazoCredito(1), new Ubicacion(1), telefonos, celulares, correos));
                List<CorreoProveedor> correos2 = new ArrayList<>();
                correos2.add(new CorreoProveedor("COA011909000003", "AUXILIAR2@GMAIL.COM", new Proveedor(2)));
                correos2.add(new CorreoProveedor("COA011909000004", "AUXILIAR3@GMAIL.COM", new Proveedor(2)));
                List<CelularProveedor> celulares2 = new ArrayList<>();
                celulares2.add(new CelularProveedor("CPR011909000003", "0965431235", new Proveedor(2)));
                celulares2.add(new CelularProveedor("CPR011909000004", "0965431236", new Proveedor(2)));
                List<TelefonoProveedor> telefonos2 = new ArrayList<>();
                telefonos2.add(new TelefonoProveedor("TEA011909000003", "032964123", new Proveedor(2)));
                proveedores.add(new Proveedor("PRV110721000002",  "0101010102", "SUPERMAXI", "NOMBRE COMERCIAL SUPERMAXI", "AV HURTADO DE MENDOZA", "TRAS EL MERCADO", -1.6719601146175827, -78.65041698970857, 0, Constantes.si, Constantes.no, Constantes.si, Constantes.no, Constantes.activo, new TipoIdentificacion(1), new TipoContribuyente(1), new GrupoProveedor(2), new FormaPago(1), new PlazoCredito(1), new Ubicacion(5), telefonos2, celulares2, correos2));
                rep.saveAll(proveedores);
            }
    }
}
