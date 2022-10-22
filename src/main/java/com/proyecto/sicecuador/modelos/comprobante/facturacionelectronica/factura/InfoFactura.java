package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "infoFactura")
@XmlType(propOrder={"fechaEmision", "obligadoContabilidad", "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", "direccionComprador", "totalSinImpuestos", "totalDescuento", "totalConImpuestos", "propina", "importeTotal", "moneda", "pagos"})
public class InfoFactura {
	private String fechaEmision;
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
	
	public InfoFactura() {
		
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public String getObligadoContabilidad() {
		return obligadoContabilidad;
	}

	public String getTipoIdentificacionComprador() {
		return tipoIdentificacionComprador;
	}

	public String getRazonSocialComprador() {
		return razonSocialComprador;
	}

	public String getIdentificacionComprador() {
		return identificacionComprador;
	}

	public String getDireccionComprador() {
		return direccionComprador;
	}

	public double getTotalSinImpuestos() {
		return totalSinImpuestos;
	}

	public double getTotalDescuento() {
		return totalDescuento;
	}
	
	public TotalConImpuestos getTotalConImpuestos() {
		return totalConImpuestos;
	}

	public double getImporteTotal() {
		return importeTotal;
	}
	
	public double getPropina() {
		return propina;
	}

	public String getMoneda() {
		return moneda;
	}		

	public Pagos getPagos() {
		return pagos;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	
	public void setObligadoContabilidad(String obligadoContabilidad) {
		this.obligadoContabilidad = obligadoContabilidad;
	}

	public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
		this.tipoIdentificacionComprador = tipoIdentificacionComprador;
	}

	public void setRazonSocialComprador(String razonSocialComprador) {
		this.razonSocialComprador = razonSocialComprador;
	}

	public void setIdentificacionComprador(String identificacionComprador) {
		this.identificacionComprador = identificacionComprador;
	}

	public void setDireccionComprador(String direccionComprador) {
		this.direccionComprador = direccionComprador;
	}

	public void setTotalSinImpuestos(double totalSinImpuestos) {
		this.totalSinImpuestos = totalSinImpuestos;
	}
	
	public void setPropina(double propina) {
		this.propina = propina;
	}

	public void setTotalDescuento(double totalDescuento) {
		this.totalDescuento = totalDescuento;
	}

	public void setTotalConImpuestos(TotalConImpuestos totalConImpuestos) {
		this.totalConImpuestos = totalConImpuestos;
	}

	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public void setPagos(Pagos pagos) {
		this.pagos = pagos;
	}
	
	
}
