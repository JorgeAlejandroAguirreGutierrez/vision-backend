package com.proyecto.vision.modelos.venta.electronico.guiaremision;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "infoGuiaRemision")
@XmlType(propOrder={"dirEstablecimiento", "dirPartida", "razonSocialTransportista", "tipoIdentificacionTransportista", "rucTransportista", "obligadoContabilidad", "contribuyenteEspecial", "fechaIniTransporte", "fechaFinTransporte", "placa"})
@Getter
@Setter
public class InfoGuiaRemision {
	private String dirEstablecimiento;
	private String dirPartida;
	private String razonSocialTransportista;
	private String tipoIdentificacionTransportista;
	private String rucTransportista;
	private String obligadoContabilidad;
	private String contribuyenteEspecial;
	private String fechaIniTransporte;
	private String fechaFinTransporte;
	private String placa;
}
