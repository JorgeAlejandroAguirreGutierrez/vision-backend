package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "factura")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"infoTributaria", "infoFactura", "detalles"})
public class FacturaElectronica {
	//DATOS DEL SRI CAMPOS EXACTAMENTE COMO LOS TIENE
	@XmlAttribute(name = "id")
    private String id="comprobante";
	
	@XmlAttribute(name = "version")
    private String version="1.0.0";
	
	private InfoTributaria infoTributaria;
	
	private InfoFactura infoFactura;
	
	private Detalles detalles;
	
	public FacturaElectronica() {
		
	}
	
	public String getId() {
		return id;
	}
	
	public String getVersion() {
		return version;
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
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setVersion(String version) {
		this.version = version;
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
