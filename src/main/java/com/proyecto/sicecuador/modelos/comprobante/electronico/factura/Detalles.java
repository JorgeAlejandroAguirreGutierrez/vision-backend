package com.proyecto.sicecuador.modelos.comprobante.electronico.factura;

import lombok.Data;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "detalles")
@Data
public class Detalles {
	private List<Detalle> detalle;
}
