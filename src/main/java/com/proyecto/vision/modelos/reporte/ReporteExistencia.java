package com.proyecto.vision.modelos.reporte;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReporteExistencia {
    //DATOS GENERALES
    private String razonSocial;
    private String nombreComercial;
    private String nombreReporte;
    private String fecha;
    private String fechaCorte;
    private String usuario;
    private String perfil;
    private double totalExistencia;
    private double totalCostoUnitario;
    private double totalCosto;
    private List<ReporteExistenciaLinea> reporteExistenciaLineas;
    //FIRMAS DE RESPONSABILIDAD
    private String nombreRepresentanteLegal;
    private String cargoRepresentanteLegal;
    private String empresaRepresentanteLegal;
    private String nombreUsuario;
    private String cargoUsuario;
    private String empresaUsuario;

}
