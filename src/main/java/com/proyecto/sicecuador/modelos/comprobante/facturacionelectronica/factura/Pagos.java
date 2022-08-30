package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pagos")
public class Pagos {

	private List<Pago> pago;
	
	public Pagos() {
		
	}

	public List<Pago> getPago() {
		return pago;
	}

	public void setPago(List<Pago> pago) {
		this.pago = pago;
	}
	
	

}
