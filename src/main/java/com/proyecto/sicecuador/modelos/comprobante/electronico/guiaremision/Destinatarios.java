package com.proyecto.sicecuador.modelos.comprobante.electronico.guiaremision;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "destinatarios")
@Data
public class Destinatarios {
	private List<Destinatario> destinatario;
}
