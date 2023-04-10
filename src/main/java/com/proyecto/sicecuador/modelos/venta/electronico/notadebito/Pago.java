package com.proyecto.sicecuador.modelos.venta.electronico.notadebito;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "pago")
@XmlType(propOrder={"formaPago", "total", "plazo", "unidadTiempo"})
@Data
public class Pago {
	private String formaPago;
	private double total;
	private long plazo;
	private String unidadTiempo;
}
