package com.proyecto.sicecuador.modelos.venta.electronico.notadebito;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "motivos")
@Data
public class Motivos {
	private List<Motivo> motivo;
}
