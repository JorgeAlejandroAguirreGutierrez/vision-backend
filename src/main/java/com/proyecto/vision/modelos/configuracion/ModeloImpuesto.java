package com.proyecto.vision.modelos.configuracion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloImpuesto {
    private String codigo;
    private String codigoPorcentaje;
    private String baseImponible;
    private String valor;
}
