package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IRetencionVentaRepository;
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
@Order(46)
@Profile({"dev","prod"})
public class RetencionVentaData implements ApplicationRunner {
    @Autowired
    private IRetencionVentaRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<RetencionVenta> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<RetencionVenta> retenciones_ventas = new ArrayList<>();
            retenciones_ventas.add(new RetencionVenta("RVE000001", "001-001-0000234", Date.valueOf("2019-08-28"), "EMPRESA PROVEEDORA 1", "123948474618293917383029", true, 100.00, 12.00, new TipoRetencion(1), new Recaudacion(1)));
            retenciones_ventas.add(new RetencionVenta("RVE000002", "001-001-0000444", Date.valueOf("2019-08-28"), "EMPRESA PROVEEDORA 1", "123948474618243543689", false, 12.00, 8.00, new TipoRetencion(2), new Recaudacion(1)));
            rep.saveAll(retenciones_ventas);
        }*/
    }
}
