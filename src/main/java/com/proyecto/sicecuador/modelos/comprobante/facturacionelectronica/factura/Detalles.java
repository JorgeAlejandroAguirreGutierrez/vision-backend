package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "detalles")
public class Detalles {

	private List<Detalle> detalle;
	
	public Detalles() {
		
	}

	public List<Detalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<Detalle> detalle) {
		this.detalle = detalle;
	}


}
