package com.proyecto.vision.modelos.venta.electronico.guiaremision;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "infoTributaria")
@XmlType(propOrder={"ambiente", "tipoEmision", "razonSocial", "nombreComercial", "ruc", "claveAcceso", "codDoc", "estab", "ptoEmi", "secuencial", "dirMatriz"})
@Getter
@Setter
public class InfoTributaria {
	private String ambiente;
	private String tipoEmision;
	private String razonSocial;
	private String nombreComercial;
	private String ruc;
	private String claveAcceso;
	private String codDoc;
	private String estab;
	private String ptoEmi;
	private String secuencial;
	private String dirMatriz;
}
