package com.proyecto.vision.modelos.venta.electronico.factura;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "totalImpuesto")
@XmlType(propOrder={"codigo", "codigoPorcentaje", "baseImponible", "valor"})
@Getter
@Setter
public class TotalImpuesto {
	private String codigo;
	private String codigoPorcentaje;
	private double baseImponible;
	private double valor;
}
