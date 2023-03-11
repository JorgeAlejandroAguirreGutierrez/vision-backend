package com.proyecto.sicecuador.modelos.comprobante.electronico.notacredito;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "totalConImpuestos")
@Data
public class TotalConImpuestos {
	private List<TotalImpuesto> totalImpuesto;
}