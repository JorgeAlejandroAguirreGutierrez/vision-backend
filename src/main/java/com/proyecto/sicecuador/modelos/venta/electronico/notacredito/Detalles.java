package com.proyecto.sicecuador.modelos.venta.electronico.notacredito;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "detalles")
@Getter
@Setter
public class Detalles {
	private List<Detalle> detalle;
}
