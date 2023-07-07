package com.proyecto.vision.modelos.venta.electronico.factura;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "pago")
@XmlType(propOrder={"formaPago", "total", "plazo", "unidadTiempo"})
@Getter
@Setter
public class Pago {
	private String formaPago;
	private double total;
	private long plazo;
	private String unidadTiempo;
}
