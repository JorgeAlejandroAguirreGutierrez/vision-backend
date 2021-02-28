package com.proyecto.sicecuador.otros.comprobante;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.DetalleFactura;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.util.Optional;

@Component
public class FacturaUtil {
    private static IParametroRepository rep;
    private final String tabla="factura";
    private final String tabla_factura_detalle="factura_detalle";
    private final String tipo="CREAR";

    @Autowired
    public void initParametro(IParametroRepository rep){
        this.rep = rep;
    }

    @PrePersist
    public void prePersist(Entidad entidad) {
        Optional<Parametro> parametro = rep.findByTablaAndTipo(tabla,tipo);
        long conteo=rep.findConteoFactura();
        entidad.setCodigo(Util.generarCodigo(parametro, conteo));
        Factura factura=(Factura)entidad;
        String punto_venta=factura.getVendedor().getPuntoVenta().getCodigo();
        String establecimiento=factura.getVendedor().getPuntoVenta().getEstablecimiento().getCodigo();
        factura.setNumero(Util.generarCodigoFactura(establecimiento, punto_venta, conteo));
        for (DetalleFactura factura_detalle : factura.getDetallesFactura()) {
            parametro = rep.findByTablaAndTipo(tabla_factura_detalle, tipo);
            conteo = rep.findConteoFacturaDetalle();
            factura_detalle.setCodigo(Util.generarCodigo(parametro, conteo));
        }
    }
}
