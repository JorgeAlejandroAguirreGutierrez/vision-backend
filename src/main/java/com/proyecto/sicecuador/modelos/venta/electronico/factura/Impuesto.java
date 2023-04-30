package com.proyecto.sicecuador.modelos.venta.electronico.factura;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "impuesto")
@XmlType(propOrder={"codigo", "codigoPorcentaje", "tarifa", "baseImponible", "valor"})
@Getter
@Setter
public class Impuesto {
	private String codigo;
	private String codigoPorcentaje;
	private double tarifa;
	private double baseImponible;
	private double valor;
}
