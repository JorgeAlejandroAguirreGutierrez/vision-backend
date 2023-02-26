package com.proyecto.sicecuador.datos.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.repositorios.comprobante.ITipoComprobanteRepository;
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
            tipos_comprobantes.add(new TipoComprobante("TCO000001","01", "FACTURA", "FACTURA", "factura"));
            tipos_comprobantes.add(new TipoComprobante("TCO000002","03", "LIQUIDACIÓN DE COMPRA DE BIENES Y PRESTACIÓN DE SERVICIOS", "LIQUIDACIÓN DE COMPRA DE BIENES Y PRESTACIÓN DE SERVICIOS", "pendiente revisar"));
            tipos_comprobantes.add(new TipoComprobante("TCO000003","04", "NOTA DE CRÉDITO COMPRA", "NOTA DE CRÉDITO COMPRA", "nota_credito_compra"));
            tipos_comprobantes.add(new TipoComprobante("TCO000004","05", "NOTA DE DÉBITO", "NOTA DE DÉBITO", "nota_debito"));
            tipos_comprobantes.add(new TipoComprobante("TCO000005","06", "GUÍA DE REMISIÓN", "GUÍA DE REMISIÓN", "guia_remision"));
            tipos_comprobantes.add(new TipoComprobante("TCO000006","07", "COMPROBANTE DE RETENCIÓN", "COMPROBANTE DE RETENCIÓN", "retencion"));
            tipos_comprobantes.add(new TipoComprobante("TCO000007","NA", "EGRESO", "EGRESO", "egreso"));
            tipos_comprobantes.add(new TipoComprobante("TCO000008","NA", "PEDIDO", "PEDIDO", "pedido"));
            tipos_comprobantes.add(new TipoComprobante("TCO000009","NA", "PROFORMA", "PROFORMA", "proforma"));
            tipos_comprobantes.add(new TipoComprobante("TCO000010","NA", "FACTURA DE COMPRA", "FACTURA DE COMPRA", "factura_compra"));
            tipos_comprobantes.add(new TipoComprobante("TCO000011","NA", "ANTICIPO", "ANTICIPO", "recaudacion"));
            tipos_comprobantes.add(new TipoComprobante("TCO000012","NA", "RETENCIONES EN COMPRAS", "RETENCIONES EN COMPRA", "retencion_compra"));
            rep.saveAll(tipos_comprobantes);
        }
    }
}
