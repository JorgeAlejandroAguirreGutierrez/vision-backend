package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "pago")
@XmlType(propOrder={"formaPago", "total", "plazo", "unidadTiempo"})
public class Pago {

	private String formaPago;
	private double total;
	private long plazo;
	private String unidadTiempo;
	
	public Pago() {
		
	}

	public String getFormaPago() {
		return formaPago;
	}

	public double getTotal() {
		return total;
	}

	public long getPlazo() {
		return plazo;
	}

	public String getUnidadTiempo() {
		return unidadTiempo;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setPlazo(long plazo) {
		this.plazo = plazo;
	}

	public void setUnidadTiempo(String unidadTiempo) {
		this.unidadTiempo = unidadTiempo;
	}

	
}
