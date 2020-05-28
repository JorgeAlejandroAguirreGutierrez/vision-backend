package com.proyecto.sicecuador.otros.comprobante;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.CelularAuxiliar;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.FacturaCaracteristica;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
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
    private final String tabla_factura_caracteristica="factura_caracteristica";
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
        String punto_venta=factura.getVendedor().getPunto_venta().getCodigo();
        String establecimiento=factura.getVendedor().getPunto_venta().getEstablecimiento().getCodigo();
        factura.setNumero(Util.generarCodigoFactura(establecimiento, punto_venta, conteo));
        for (FacturaDetalle factura_detalle : factura.getFactura_detalles()) {
            parametro = rep.findByTablaAndTipo(tabla_factura_detalle, tipo);
            conteo = rep.findConteoFacturaDetalle();
            factura_detalle.setCodigo(Util.generarCodigo(parametro, conteo));
            for (FacturaCaracteristica factura_caracteristica : factura_detalle.getFactura_caracteristicas()) {
                parametro = rep.findByTablaAndTipo(tabla_factura_caracteristica, tipo);
                conteo = rep.findConteoFacturaDetalle();
                factura_caracteristica.setCodigo(Util.generarCodigo(parametro, conteo));
            }
        }
    }
}
