package com.proyecto.vision.modelos.configuracion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloDetalle {
    private String codigoPrincipal;
    private String descripcion;
    private String cantidad;
    private String precioUnitario;
    private String descuento;
    private String precioTotalSinImpuesto;
    private String codigoImpuesto;
    private String codigoPorcentaje;
    private String tarifa;
    private String baseImponible;
    private String valor;
}
