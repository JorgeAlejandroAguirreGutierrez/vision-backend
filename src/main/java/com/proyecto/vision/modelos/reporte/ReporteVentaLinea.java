package com.proyecto.vision.modelos.reporte;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReporteVentaLinea {
    private String fecha;
    private String hora;
    private String documento;
    private String establecimiento;
    private String estacion;
    private String secuencia;
    private String cliente;
    private String identificacion;
    private String vendedor;
    private String tipoVenta;
    private double subtotal0;
    private double subtotal12;
    private double iva;
    private double total;
}
