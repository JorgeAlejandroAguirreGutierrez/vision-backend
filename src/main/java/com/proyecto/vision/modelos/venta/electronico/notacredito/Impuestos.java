package com.proyecto.vision.modelos.venta.electronico.notacredito;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "impuestos")
@Getter
@Setter
public class Impuestos {
	private List<Impuesto> impuesto;
}
