package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "impuestos")
public class Impuestos {

	private List<ImpuestoE> impuestoE;
	
	public Impuestos() {
		
	}

	public List<ImpuestoE> getImpuestoE() {
		return impuestoE;
	}

	public void setImpuestoE(List<ImpuestoE> impuestoE) {
		this.impuestoE = impuestoE;
	}


}
