package com.proyecto.vision.modelos.venta.electronico.notadebito;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "motivos")
@Getter
@Setter
public class Motivos {
	private List<Motivo> motivo;
}
