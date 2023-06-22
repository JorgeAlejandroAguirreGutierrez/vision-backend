package com.proyecto.sicecuador.modelos.reporte;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReporteKardex {
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
    //PRODUCTO
    private String nombre;
    private String modelo;
    private String serie;
    private String caducidad;
    //VALORACION
    private String costoPromedio;
    private String ultimoCosto;
    private String margenRentabilidadPromedio;
    private String tarifaProducto;
    //TARJETA KARDEX
    private List<ReporteKardexLinea> reporteKardexLineas;
}
