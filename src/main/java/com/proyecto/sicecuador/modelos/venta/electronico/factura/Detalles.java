package com.proyecto.sicecuador.modelos.venta.electronico.factura;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "detalles")
@Getter
@Setter
public class Detalles {
	private List<Detalle> detalle;
}
