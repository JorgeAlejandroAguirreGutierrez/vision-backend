package com.proyecto.sicecuador.modelos.venta.electronico.factura;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "impuestos")
@Getter
@Setter
public class Impuestos {
	private List<Impuesto> impuesto;
}
