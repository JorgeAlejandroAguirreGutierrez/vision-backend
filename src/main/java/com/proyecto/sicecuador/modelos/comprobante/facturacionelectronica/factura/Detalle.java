package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "detalle")
public class Detalle {

	private String codigoPrincipal;
	private String descripcion;
	private double cantidad;
	private double precioUnitario;
	private double descuento;
	private double precioTotalSinImpuesto;
	
	public Detalle() {
		
	}

	public String getCodigoPrincipal() {
		return codigoPrincipal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public double getCantidad() {
		return cantidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public double getDescuento() {
		return descuento;
	}

	public double getPrecioTotalSinImpuesto() {
		return precioTotalSinImpuesto;
	}

	public void setCodigoPrincipal(String codigoPrincipal) {
		this.codigoPrincipal = codigoPrincipal;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public void setPrecioTotalSinImpuesto(double precioTotalSinImpuesto) {
		this.precioTotalSinImpuesto = precioTotalSinImpuesto;
	}


}
