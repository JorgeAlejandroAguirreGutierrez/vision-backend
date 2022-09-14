package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "impuestos")
public class Impuestos {

	private List<Impuesto> impuesto;
	
	public Impuestos() {
		
	}

	public List<Impuesto> getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(List<Impuesto> impuesto) {
		this.impuesto = impuesto;
	}


}
