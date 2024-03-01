package com.proyecto.vision.modelos.reporte;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteExistenciaLinea {
    //DATOS GENERALES
    private String codigo;
    private String nombre;
    private String iva;
    private String existencia;
    private String costoUnitario;
    private String costoTotal;
}
