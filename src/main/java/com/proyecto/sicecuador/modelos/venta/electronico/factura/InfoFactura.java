package com.proyecto.vision.modelos.venta.electronico.factura;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "infoFactura")
@XmlType(propOrder={"fechaEmision", "dirEstablecimiento", "obligadoContabilidad", "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", "direccionComprador", "totalSinImpuestos", "totalDescuento", "totalConImpuestos", "propina", "importeTotal", "moneda", "pagos"})
@Getter
@Setter
public class InfoFactura {
	private String fechaEmision;
	private String dirEstablecimiento;
	private String obligadoContabilidad;
	private String tipoIdentificacionComprador;
	private String razonSocialComprador;
	private String identificacionComprador;
	private String direccionComprador;
	private double totalSinImpuestos;
	private double totalDescuento;
	private TotalConImpuestos totalConImpuestos;
	private double propina;
	private double importeTotal;
	private String moneda;
	private Pagos pagos;
}
