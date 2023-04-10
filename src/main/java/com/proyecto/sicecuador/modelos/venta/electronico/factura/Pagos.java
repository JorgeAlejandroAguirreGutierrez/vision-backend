package com.proyecto.sicecuador.modelos.venta.electronico.factura;

import lombok.Data;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pagos")
@Data
public class Pagos {
	private List<Pago> pago;
}
