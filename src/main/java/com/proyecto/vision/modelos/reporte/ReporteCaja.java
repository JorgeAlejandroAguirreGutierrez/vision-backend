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
    private long facturasEmitidas;
    private long facturasAnuladas;
    private long facturasTotales;
    //RESUMEN DE VENTAS
    private double total0;
    private double total12;
    private double totalIva;
    private double total;
    //CIERRE DE CAJA VENTAS AL CONTADO
    private double efectivo;
    private double cheque;
    private double tarjetaCredito;
    private double tarjetaDebito;
    private double transferencia;
    private double deposito;
    private double credito;
    private double totalRecaudacion;
    //DINERO RECAUDADO
    private double cantidadBillete100;
    private String denominacionBillete100;
    private double totalBillete100;
    private double cantidadBillete50;
    private String denominacionBillete50;
    private double totalBillete50;
    private double cantidadBillete20;
    private String denominacionBillete20;
    private double totalBillete20;
    private double cantidadBillete10;
    private String denominacionBillete10;
    private double totalBillete10;
    private double cantidadBillete5;
    private String denominacionBillete5;
    private double totalBillete5;
    private double cantidadBillete2;
    private String denominacionBillete2;
    private double totalBillete2;
    private double cantidadBillete1;
    private String denominacionBillete1;
    private double totalBillete1;

    private double totalBilletes;

    private double cantidadMoneda100;
    private String denominacionMoneda100;
    private double totalMoneda100;
    private double cantidadMoneda50;
    private String denominacionMoneda50;
    private double totalMoneda50;
    private double cantidadMoneda25;
    private String denominacionMoneda25;
    private double totalMoneda25;
    private double cantidadMoneda10;
    private String denominacionMoneda10;
    private double totalMoneda10;
    private double cantidadMoneda5;
    private String denominacionMoneda5;
    private double totalMoneda5;
    private double cantidadMoneda1;
    private String denominacionMoneda1;
    private double totalMoneda1;

    private double totalMonedas;

    private double totalCaja;

    //DIFERENCIAS
    private double faltante;
    private double sobrante;

    //FIRMAS DE RESPONSABILIDAD
    private String nombreRepresentanteLegal;
    private String cargoRepresentanteLegal;
    private String empresaRepresentanteLegal;
    private String nombreUsuario;
    private String cargoUsuario;
    private String empresaUsuario;
}
