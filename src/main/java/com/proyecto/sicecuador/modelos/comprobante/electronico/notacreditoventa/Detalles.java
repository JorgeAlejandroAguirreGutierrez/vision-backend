package com.proyecto.sicecuador.modelos.comprobante.electronico.notacreditoventa;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "detalles")
@Data
public class Detalles {
	private List<Detalle> detalle;
}
