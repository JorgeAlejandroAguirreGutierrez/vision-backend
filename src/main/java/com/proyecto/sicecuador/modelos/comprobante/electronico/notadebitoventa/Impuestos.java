package com.proyecto.sicecuador.modelos.comprobante.electronico.notadebitoventa;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "impuestos")
@Data
public class Impuestos {
	private List<Impuesto> impuesto;
}
