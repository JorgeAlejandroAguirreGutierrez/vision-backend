package com.proyecto.sicecuador.modelos.venta.electronico.factura;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pagos")
@Getter
@Setter
public class Pagos {
	private List<Pago> pago;
}
