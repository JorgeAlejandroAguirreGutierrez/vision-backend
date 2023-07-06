package com.proyecto.sicecuador.modelos.reporte;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReporteKardexLinea {
    private String operacion;
    private String fecha;
    private String org;
    private String bodega;
    private String documento;
    private String provCliente;
    private String ingresoCantidad;
    private String ingresoCosto;
    private String ingresoCostoTotal;
    private String salidaCantidad;
    private String salidaCosto;
    private String salidaCostoTotal;
    private String existenciaCantidad;
    private String existenciaCosto;
    private String existenciaCostoTotal;
}
