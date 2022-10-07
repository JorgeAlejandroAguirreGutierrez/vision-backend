package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "totalImpuesto")
public class TotalImpuesto {

	private String codigo;
	private String codigoPorcentaje;
	private double baseImponible;
	private double valor;
	
	public TotalImpuesto() {
		
	}

	public String getCodigo() {
		return codigo;
	}

	public String getCodigoPorcentaje() {
		return codigoPorcentaje;
	}

	public double getBaseImponible() {
		return baseImponible;
	}

	public double getValor() {
		return valor;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setCodigoPorcentaje(String codigoPorcentaje) {
		this.codigoPorcentaje = codigoPorcentaje;
	}

	public void setBaseImponible(double baseImponible) {
		this.baseImponible = baseImponible;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	
}
