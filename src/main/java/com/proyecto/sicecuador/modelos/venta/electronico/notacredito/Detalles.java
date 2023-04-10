package com.proyecto.sicecuador.modelos.venta.electronico.notacredito;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "detalles")
@Data
public class Detalles {
	private List<Detalle> detalle;
}
