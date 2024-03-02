package com.proyecto.vision.modelos.reporte;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReporteVenta {
    //DATOS GENERALES
    private String razonSocial;
    private String nombreComercial;
    private String nombreReporte;
    private String fechaInicio;
    private String fechaFinal;
    private String fecha;
    private String periodoReporte;
    private String usuario;
    private String perfil;
    //DOCUMENTOS EN EL PERIODO
    private List<ReporteVentaLinea> reporteVentaLineas;
    //TOTALES
    private double total0;
    private double total12;
    private double totalIva;
    private double total;
    //INFORMACION RESUMEN
    private long facturasEmitidas;
    private long facturasAnuladas;
    private long facturasTotales;
    //REPORTE DE COBROS
    private double efectivo;
    private double cheque;
    private double tarjetaCredito;
    private double tarjetaDebito;
    private double transferencia;
    private double deposito;
    private double credito;
    private double totalRecaudacion;
    //FIRMAS DE RESPONSABILIDAD
    private String nombreRepresentanteLegal;
    private String cargoRepresentanteLegal;
    private String empresaRepresentanteLegal;
    private String nombreUsuario;
    private String cargoUsuario;
    private String empresaUsuario;
}
