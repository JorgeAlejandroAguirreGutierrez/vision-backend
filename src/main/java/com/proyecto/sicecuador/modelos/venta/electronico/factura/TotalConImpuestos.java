package com.proyecto.vision.modelos.venta.electronico.factura;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "totalConImpuestos")
@Getter
@Setter
public class TotalConImpuestos {
	private List<TotalImpuesto> totalImpuesto;
}
