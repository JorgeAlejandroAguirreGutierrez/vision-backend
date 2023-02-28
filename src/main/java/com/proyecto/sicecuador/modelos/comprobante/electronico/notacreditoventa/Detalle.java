package com.proyecto.sicecuador.modelos.comprobante.electronico.notacreditoventa;

import com.proyecto.sicecuador.modelos.comprobante.electronico.notacreditoventa.Impuestos;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "detalle")
@XmlType(propOrder={"codigoInterno", "descripcion", "cantidad", "precioUnitario", "descuento", "precioTotalSinImpuesto", "impuestos"})
@Data
public class Detalle {
	private String codigoInterno;
	private String descripcion;
	private double cantidad;
	private double precioUnitario;
	private double descuento;
	private double precioTotalSinImpuesto;
	private Impuestos impuestos;
}
