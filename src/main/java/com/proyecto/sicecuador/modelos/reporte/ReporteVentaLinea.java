package com.proyecto.sicecuador.modelos.reporte;

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
    private String subtotal0;
    private String subtotal12;
    private String iva;
    private String total;
}
