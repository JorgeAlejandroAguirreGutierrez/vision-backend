package com.proyecto.sicecuador.modelos.venta.electronico.factura;

import lombok.Data;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "impuestos")
@Data
public class Impuestos {
	private List<Impuesto> impuesto;
}
