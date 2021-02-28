package com.proyecto.sicecuador.otros.entrega;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.util.Optional;

@Component
public class GuiaRemisionUtil {
    private static IParametroRepository rep;
    private final String tabla="guia_remision";
    private final String tipo="CREAR";

    @Autowired
    public void initParametro(IParametroRepository rep){
        this.rep = rep;
    }

    @PrePersist
    public void prePersist(Entidad entidad) {
        Optional<Parametro> parametro = rep.findByTablaAndTipo(tabla,tipo);
        long conteo=rep.findConteoGuiaRemision();
        entidad.setCodigo(Util.generarCodigo(parametro, conteo));
        GuiaRemision guia_remision=(GuiaRemision)entidad;
        String punto_venta=guia_remision.getFactura().getVendedor().getPuntoVenta().getCodigo();
        String establecimiento=guia_remision.getFactura().getVendedor().getPuntoVenta().getEstablecimiento().getCodigo();
        guia_remision.setNumero(Util.generarCodigoGuiaRemision(establecimiento, punto_venta, conteo));
    }
}
