package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import com.proyecto.sicecuador.modelos.comprobante.Factura;

public class FacturaE {
	//DATOS DEL SRI CAMPOS EXACTAMENTE COMO LOS TIENE
	InfoTributaria infoTributaria;
	InfoFactura infoFactura;
	Factura factura;
	String campoEjemplo;
	
	public FacturaE() {
		
	}
	
	public FacturaE(Factura factura) {
		this.factura = factura;
		
	}
	
		
	public InfoTributaria getInfoTributaria() {
		return infoTributaria;
	}

	public void setInfoTributaria(InfoTributaria infoTributaria) {
		this.infoTributaria = infoTributaria;
		
	}

	public void setFactura(Factura factura) {
		this.factura=factura;
	}
	
	public Factura getFactura() {
		return factura;
	}

	public String getCampoEjemplo() {
		return campoEjemplo;
	}

	public void setCampoEjemplo(String campoEjemplo) {
		this.campoEjemplo = campoEjemplo;
	}
	
	
	
}
