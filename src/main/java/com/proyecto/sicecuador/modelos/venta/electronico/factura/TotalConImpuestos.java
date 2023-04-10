package com.proyecto.sicecuador.modelos.venta.electronico.factura;

import lombok.Data;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "totalConImpuestos")
@Data
public class TotalConImpuestos {
	private List<TotalImpuesto> totalImpuesto;
}
