package com.proyecto.sicecuador.otros.comprobante;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.util.Optional;

@Component
public class EgresoUtil {
    private static IParametroRepository rep;
    private final String tabla="egreso";
    private final String tipo="CREAR";

    @Autowired
    public void initParametro(IParametroRepository rep){
        this.rep = rep;
    }

    @PrePersist
    public void prePersist(Entidad entidad) {
        Optional<Parametro> parametro = rep.findByTablaAndTipo(tabla,tipo);
        long conteo=rep.findConteoEgreso();
        entidad.setCodigo(Util.generarCodigo(parametro, conteo));
    }
}
