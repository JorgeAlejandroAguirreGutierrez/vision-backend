package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "detalle")
@XmlType(propOrder={"codigoPrincipal", "descripcion", "cantidad", "precioUnitario", "descuento", "precioTotalSinImpuesto", "impuestos"})
public class Detalle {

	private String codigoPrincipal;
	private String descripcion;
	private double cantidad;
	private double precioUnitario;
	private double descuento;
	private double precioTotalSinImpuesto;
	private Impuestos impuestos;
	
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
	
	public Impuestos getImpuestos() {
		return impuestos;
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
	
	public void setImpuestos(Impuestos impuestos) {
		this.impuestos = impuestos;
	}


}
