package com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura;

import java.util.List;

public class InfoTributaria {
	
	String ambiente;
	String tipoEmision;
	String razonSocial;
	String nombreComercial;
	String ruc;
	String claveAcceso;
	String codDoc;
	String estab;
	String ptoEmi;
	String secuencial;
	String dirMatriz;
	
	public InfoTributaria() {
		
	}

	public InfoTributaria(String ambiente, String tipoEmision, String razonSocial, String nombreComercial, String ruc,
			String claveAcceso, String codDoc, String estab, String ptoEmi, String secuencial, String dirMatriz) {
		this.ambiente = ambiente;
		this.tipoEmision = tipoEmision;
		this.razonSocial = razonSocial;
		this.nombreComercial = nombreComercial;
		this.ruc = ruc;
		this.claveAcceso = claveAcceso;
		this.codDoc = codDoc;
		this.estab = estab;
		this.ptoEmi = ptoEmi;
		this.secuencial = secuencial;
		this.dirMatriz = dirMatriz;
	}

	public InfoTributaria(List<String> datos) {
		ambiente=datos.get(0)== null ? null: datos.get(0);
		tipoEmision=datos.get(1)== null ? null: datos.get(1);
		razonSocial=datos.get(2)== null ? null: datos.get(2);
		nombreComercial=datos.get(3)== null ? null: datos.get(3);
		ruc=datos.get(4)== null ? null: datos.get(4);
		claveAcceso=datos.get(5)== null ? null: datos.get(5);
		codDoc=datos.get(6)== null ? null: datos.get(6);
		estab=datos.get(7)== null ? null: datos.get(7);
		ptoEmi=datos.get(8)== null ? null: datos.get(8);
		secuencial=datos.get(9)== null ? null: datos.get(9);
		dirMatriz=datos.get(10)== null ? null: datos.get(10);

		
	}
	
	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getTipoEmision() {
		return tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public String getCodDoc() {
		return codDoc;
	}

	public void setCodDoc(String codDoc) {
		this.codDoc = codDoc;
	}

	public String getEstab() {
		return estab;
	}

	public void setEstab(String estab) {
		this.estab = estab;
	}

	public String getPtoEmi() {
		return ptoEmi;
	}

	public void setPtoEmi(String ptoEmi) {
		this.ptoEmi = ptoEmi;
	}

	public String getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}

	public String getDirMatriz() {
		return dirMatriz;
	}

	public void setDirMatriz(String dirMatriz) {
		this.dirMatriz = dirMatriz;
	}
	
	

}
