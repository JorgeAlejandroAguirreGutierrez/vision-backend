package com.proyecto.sicecuador.repositorios.datos.comprobante;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.Proforma;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.interf.comprobante.ITipoComprobanteRepository;
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
public class TipoComprobanteData implements ApplicationRunner {
    @Autowired
    private ITipoComprobanteRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoComprobante> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoComprobante> tipos_comprobantes = new ArrayList<>();
            tipos_comprobantes.add(new TipoComprobante("TCO000001", "FACTURA", "FACTURA", "facturas"));
            tipos_comprobantes.add(new TipoComprobante("TCO000002", "EGRESO", "EGRESO", "egresos"));
            tipos_comprobantes.add(new TipoComprobante("TCO000003", "PEDIDO", "PEDIDO", "pedidos"));
            tipos_comprobantes.add(new TipoComprobante("TCO000004", "PROFORMA", "PROFORMA", "proformas"));
            tipos_comprobantes.add(new TipoComprobante("TCO000005", "NOTA DE CREDITO", "NOTA DE CREDITO", "notas_creditos"));
            tipos_comprobantes.add(new TipoComprobante("TCO000006", "FACTURA DE COMPRA", "FACTURA DE COMPRA", "facturas_compras"));
            tipos_comprobantes.add(new TipoComprobante("TCO000007", "ANTICIPOS", "ANTICIPOS", "recaudaciones"));
            tipos_comprobantes.add(new TipoComprobante("TCO000008", "RETENCIONES EN VENTA", "RETENCIONES EN VENTAS", "retenciones_ventas"));
            tipos_comprobantes.add(new TipoComprobante("TCO000009", "RETENCIONES EN COMPRAS", "RETENCIONES EN COMPRAS", "retenciones_compras"));
            rep.saveAll(tipos_comprobantes);
        }
    }
}
