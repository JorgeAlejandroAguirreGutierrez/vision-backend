package com.proyecto.sicecuador.otros;


import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import org.springframework.stereotype.Component;

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
    public static String error_registro_existente="REGISTRO EXISTENTE";
}
