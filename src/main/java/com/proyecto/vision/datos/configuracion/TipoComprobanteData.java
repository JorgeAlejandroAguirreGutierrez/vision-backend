package com.proyecto.vision.datos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.repositorios.configuracion.ITipoComprobanteRepository;
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
@Order(17)
@Profile({"dev","prod"})
public class TipoComprobanteData implements ApplicationRunner {
    @Autowired
    private ITipoComprobanteRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoComprobante> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoComprobante> tiposComprobantes = new ArrayList<>();
            tiposComprobantes.add(new TipoComprobante("TCO000001","NA", "PROCESO INTERNO", "PROCESO INTERNO", null, Constantes.no, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000002","01", "FACTURA", "FACTURA VENTA", "factura", Constantes.si, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000003","03", "LIQUIDACIÓN DE COMPRA DE BIENES Y PRESTACIÓN DE SERVICIOS", "LIQUIDACIÓN COMPRA", "liquidacion_compra", Constantes.si, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000004","04", "NOTA DE CRÉDITO VENTA", "N. CRÉDITO VENTA", "nota_credito_venta", Constantes.si, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000005","05", "NOTA DE DÉBITO VENTA", "N. DÉBITO VENTA", "nota_debito_venta", Constantes.si, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000006","06", "GUÍA DE REMISIÓN", "GUÍA REMISIÓN", "guia_remision", Constantes.si, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000007","07", "COMPROBANTE DE RETENCIÓN", "RETENCIÓN VENTA", "retencion", Constantes.si, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000008","NA", "FACTURA DE COMPRA", "FACTURA COMPRA", "factura_compra", Constantes.no, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000009","NA", "NOTA DE CRÉDITO COMPRA", "N. CRÉDITO COMPRA", "nota_credito_compra", Constantes.no, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000010","NA", "NOTA DE DÉBITO COMPRA", "N. DÉBITO COMPRA", "nota_debito_compra", Constantes.no, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000011","NA", "EGRESO", "EGRESO", "egreso", Constantes.no, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000012","NA", "PEDIDO", "PEDIDO", "pedido", Constantes.no, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000013","NA", "PROFORMA", "PROFORMA", "proforma", Constantes.no, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000014","NA", "ANTICIPO", "ANTICIPO", "recaudacion", Constantes.no, Constantes.activo));
            tiposComprobantes.add(new TipoComprobante("TCO000015","NA", "RETENCIÓN EN COMPRA", "RETENCIÓN COMPRA", "retencion_compra", Constantes.no, Constantes.activo));
            rep.saveAll(tiposComprobantes);
        }
    }
}
