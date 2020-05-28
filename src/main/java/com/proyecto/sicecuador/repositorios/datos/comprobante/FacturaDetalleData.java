package com.proyecto.sicecuador.repositorios.datos.comprobante;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.Egreso;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
import com.proyecto.sicecuador.modelos.inventario.Precio;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IFacturaDetalleRepository;
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
@Order(45)
public class FacturaDetalleData implements ApplicationRunner {
    @Autowired
    private IFacturaDetalleRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<FacturaDetalle> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<FacturaDetalle> facturas_detalles = new ArrayList<>();
            facturas_detalles.add(new FacturaDetalle("FAD000001", 1, true, false, "UNIDAD", 10, 0, 0, 13.64, 5.00, 7.50, 21.32, 3.03, 4.55, 47.01, 150.00, 0, 0, new Precio(1), new Producto(2), new Factura(1)));
            facturas_detalles.add(new FacturaDetalle("FAD000002", 2, true, false, "UNIDAD", 100, 0, 0, 18.18, 5.00, 10.00, 28.42, 3.03, 6.06, 62.66, 200.00, 0, 0, new Precio(1), new Producto(1), new Factura(1)));
            facturas_detalles.add(new FacturaDetalle("FAD000003", 3, true, true, "UNIDAD", 20, 0, 0, 5.45, 5.00, 3.00, 7.61, 2.70, 1.62, 17.68, 60.00, 12, 5.08, new Precio(1), new Producto(3), new Factura(1)));
            facturas_detalles.add(new FacturaDetalle("FAD000004", 4, false, false, "UNIDAD", 8, 0, 0, 3.64, 5.00, 2.00, 5.08, 2.70, 1.08, 11.80, 40.00, 12, 3.38, new Precio(1), new Producto(4), new Factura(1)));
            rep.saveAll(facturas_detalles);
        }*/
    }
}
