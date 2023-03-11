package com.proyecto.sicecuador.modelos.comprobante.electronico.notadebito;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pagos")
@Data
public class Pagos {
	private List<Pago> pago;
}