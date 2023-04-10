package com.proyecto.sicecuador.modelos.venta.electronico.factura;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "detalle")
@XmlType(propOrder={"codigoPrincipal", "descripcion", "cantidad", "precioUnitario", "descuento", "precioTotalSinImpuesto", "impuestos"})
@Data
public class Detalle {
	private String codigoPrincipal;
	private String descripcion;
	private double cantidad;
	private double precioUnitario;
	private double descuento;
	private double precioTotalSinImpuesto;
	private Impuestos impuestos;
}
