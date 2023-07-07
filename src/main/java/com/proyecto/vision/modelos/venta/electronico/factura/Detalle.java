package com.proyecto.vision.modelos.venta.electronico.factura;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "detalle")
@XmlType(propOrder={"codigoPrincipal", "descripcion", "cantidad", "precioUnitario", "descuento", "precioTotalSinImpuesto", "impuestos"})
@Getter
@Setter
public class Detalle {
	private String codigoPrincipal;
	private String descripcion;
	private double cantidad;
	private double precioUnitario;
	private double descuento;
	private double precioTotalSinImpuesto;
	private Impuestos impuestos;
}
