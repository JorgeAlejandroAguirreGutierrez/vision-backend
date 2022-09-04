package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "factura")
public class FacturaE {
	//DATOS DEL SRI CAMPOS EXACTAMENTE COMO LOS TIENE
	private InfoTributaria infoTributaria;
	private InfoFactura infoFactura;
	private Detalles detalles;
	
	public FacturaE() {
		
	}
	
		
	public InfoTributaria getInfoTributaria() {
		return infoTributaria;
	}
	
	public InfoFactura getInfoFactura() {
		return infoFactura;
	}

	public Detalles getDetalles() {
		return detalles;
	}

	public void setInfoTributaria(InfoTributaria infoTributaria) {
		this.infoTributaria = infoTributaria;	
	}
	public void setInfoFactura(InfoFactura infoFactura) {
		this.infoFactura = infoFactura;
	}

	public void setDetalles(Detalles detalles) {
		this.detalles = detalles;
	}
	
}
