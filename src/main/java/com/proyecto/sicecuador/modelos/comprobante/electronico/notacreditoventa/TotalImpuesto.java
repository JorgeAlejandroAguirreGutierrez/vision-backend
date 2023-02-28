package com.proyecto.sicecuador.modelos.comprobante.electronico.notacreditoventa;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "totalImpuesto")
@XmlType(propOrder={"codigo", "codigoPorcentaje", "baseImponible", "valor"})
@Data
public class TotalImpuesto {
	private String codigo;
	private String codigoPorcentaje;
	private double baseImponible;
	private double valor;
}
