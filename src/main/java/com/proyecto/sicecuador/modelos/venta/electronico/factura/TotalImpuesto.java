package com.proyecto.sicecuador.modelos.venta.electronico.factura;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "totalImpuesto")
@XmlType(propOrder={"codigo", "codigoPorcentaje", "descuentoAdicional", "baseImponible", "valor"})
@Data
public class TotalImpuesto {
	private String codigo;
	private String codigoPorcentaje;
	private double descuentoAdicional;
	private double baseImponible;
	private double valor;
}