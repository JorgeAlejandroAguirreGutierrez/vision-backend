package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "totalConImpuestos")
public class TotalConImpuestos {

	private List<TotalImpuesto> totalImpuesto;
	
	public TotalConImpuestos() {
		
	}

	public List<TotalImpuesto> getTotalImpuesto() {
		return totalImpuesto;
	}

	public void setTotalImpuesto(List<TotalImpuesto> totalImpuesto) {
		this.totalImpuesto = totalImpuesto;
	}
	
	

}
