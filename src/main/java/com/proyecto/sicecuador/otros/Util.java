package com.proyecto.sicecuador.otros;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.repositorios.interf.IGenericoRepository;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.impl.configuracion.ParametroService;
import com.proyecto.sicecuador.servicios.interf.configuracion.IEmpresaService;
import com.proyecto.sicecuador.servicios.interf.configuracion.IParametroService;
import org.hibernate.type.ObjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.PrePersist;
import java.util.Date;
import java.util.Optional;
@Component
public class Util {
    public static String generarCodigo(Optional<Parametro> parametro, long conteo){
        String rellenoConteo = String.format("%06d" , conteo);
        Date fecha = new Date();
        String año = String.valueOf(fecha.getYear());
        String mes = String.valueOf(fecha.getMonth());
        return parametro.isPresent()? parametro.get().getAbreviatura() + año + mes + rellenoConteo : null;
    }
    public static String generarCodigoFactura(String establecimiento, String punto_venta, long conteo){
        String rellenoConteo = String.format("%06d" , conteo);
        return establecimiento+"-"+punto_venta+"-" + rellenoConteo;
    }
    public static String generarCodigoGuiaRemision(String establecimiento, String punto_venta, long conteo){
        String rellenoConteo = String.format("%06d" , conteo);
        return establecimiento+"-"+punto_venta+"-" + rellenoConteo;
    }
    
    public static String vacio="";
}
