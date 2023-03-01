package com.proyecto.sicecuador.modelos.comprobante.electronico.notadebitoventa;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "motivos")
@Data
public class Motivos {
	private List<Motivo> motivo;
}
