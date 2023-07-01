package com.proyecto.sicecuador.modelos.reporte;

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
    private String periodoDelReporte;
    private String usuario;
    private String perfil;
    //DOCUMENTOS EN EL PERIODO
    private List<ReporteVentaLinea> reporteVentaLineas;
    //TOTALES
    private String total0;
    private String total12;
    private String totalIva;
    private String total;
    //INFORMACION RESUMEN
    private String facturasEmitidas;
    private String facturasAnuladas;
    private String facturasTotales;
    //REPORTE DE COBROS
    private String efectivo;
    private String cheque;
    private String tarjetaCredito;
    private String tarjetaDebito;
    private String transferencia;
    private String credito;
    private String totalRecaudacion;
    //FIRMAS DE RESPONSABILIDAD
    private String nombreRepresentanteLegal;
    private String cargoRepresentanteLegal;
    private String empresaRepresentanteLegal;
    private String nombreUsuario;
    private String cargoUsuario;
    private String empresaUsuario;


}
