package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.repositorios.interf.recaudacion.IRetencionVentaDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(47)
public class RetencionVentaDetalleData implements ApplicationRunner {
    @Autowired
    private IRetencionVentaDetalleRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<RetencionVentaDetalle> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<RetencionVentaDetalle> retenciones_ventas_detalles = new ArrayList<>();
            retenciones_ventas_detalles.add(new RetencionVentaDetalle("RVD", 1, 10.00, 7.00, new RetencionVenta(1), new TipoRetencion(1)));
            retenciones_ventas_detalles.add(new RetencionVentaDetalle("RVD", 2, 100.00, 2.00, new RetencionVenta(1), new TipoRetencion(4)));
            retenciones_ventas_detalles.add(new RetencionVentaDetalle("RVD", 1, 10.00, 7.00, new RetencionVenta(2), new TipoRetencion(2)));
            retenciones_ventas_detalles.add(new RetencionVentaDetalle("RVD", 1, 100.00, 2.00, new RetencionVenta(2), new TipoRetencion(3)));
            rep.saveAll(retenciones_ventas_detalles);
        }*/
    }
}
