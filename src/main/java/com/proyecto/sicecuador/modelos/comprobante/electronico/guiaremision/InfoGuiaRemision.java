package com.proyecto.sicecuador.modelos.comprobante.electronico.guiaremision;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "infoGuiaRemision")
@XmlType(propOrder={"dirEstablecimiento", "dirPartida", "razonSocialTransportista", "tipoIdentificacionTransportista", "rucTransportista", "obligadoContabilidad", "contribuyenteEspecial", "fechaIniTransporte", "fechaFinTransporte", "placa"})
@Data
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
