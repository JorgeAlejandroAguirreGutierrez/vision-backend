package com.proyecto.vision.modelos.reporte;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteCaja {
    private String razonSocial;
    private String nombreComercial;
    private String nombreReporte;
    private String fechaInicio;
    private String fechaFinal;
    //DATOS GENERALES
    private String fecha;
    private String periodoReporte;
    private String usuario;
    private String perfil;
    //COMPROBANTES EMITIDOS
    private String facturasEmitidas;
    private String facturasAnuladas;
    private String facturasTotales;
    //RESUMEN DE VENTAS
    private String total0;
    private String total12;
    private String totalIva;
    private String total;
    //CIERRE DE CAJA VENTAS AL CONTADO
    private String efectivo;
    private String cheque;
    private String tarjetaCredito;
    private String tarjetaDebito;
    private String transferencia;
    private String deposito;
    private String credito;
    private String totalRecaudacion;
    //DINERO RECAUDADO
    private String cantidadBillete100;
    private String denominacionBillete100;
    private String totalBillete100;
    private String cantidadBillete50;
    private String denominacionBillete50;
    private String totalBillete50;
    private String cantidadBillete20;
    private String denominacionBillete20;
    private String totalBillete20;
    private String cantidadBillete10;
    private String denominacionBillete10;
    private String totalBillete10;
    private String cantidadBillete5;
    private String denominacionBillete5;
    private String totalBillete5;
    private String cantidadBillete2;
    private String denominacionBillete2;
    private String totalBillete2;
    private String cantidadBillete1;
    private String denominacionBillete1;
    private String totalBillete1;
    private String totalBilletes;

    private String cantidadMoneda100;
    private String denominacionMoneda100;
    private String totalMoneda100;
    private String cantidadMoneda50;
    private String denominacionMoneda50;
    private String totalMoneda50;
    private String cantidadMoneda25;
    private String denominacionMoneda25;
    private String totalMoneda25;
    private String cantidadMoneda10;
    private String denominacionMoneda10;
    private String totalMoneda10;
    private String cantidadMoneda5;
    private String denominacionMoneda5;
    private String totalMoneda5;
    private String cantidadMoneda1;
    private String denominacionMoneda1;
    private String totalMoneda1;
    private String totalMonedas;

    private String totalCaja;

    //DIFERENCIAS
    private String faltante;
    private String sobrante;

    //FIRMAS DE RESPONSABILIDAD
    private String nombreRepresentanteLegal;
    private String cargoRepresentanteLegal;
    private String empresaRepresentanteLegal;
    private String nombreUsuario;
    private String cargoUsuario;
    private String empresaUsuario;
}
