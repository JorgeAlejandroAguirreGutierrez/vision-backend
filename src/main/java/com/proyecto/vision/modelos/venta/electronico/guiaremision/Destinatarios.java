package com.proyecto.vision.modelos.venta.electronico.guiaremision;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "destinatarios")
@Getter
@Setter
public class Destinatarios {
	private List<Destinatario> destinatario;
}
