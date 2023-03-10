package com.proyecto.sicecuador.modelos.comprobante.electronico.factura;

import lombok.Data;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "totalConImpuestos")
@Data
public class TotalConImpuestos {
	private List<TotalImpuesto> totalImpuesto;
}
