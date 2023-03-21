package com.proyecto.sicecuador.modelos.comprobante.electronico.guiaremision;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "destinatario")
@XmlType(propOrder={"identificacionDestinatario", "razonSocialDestinatario", "dirDestinatario", "motivoTraslado", "ruta", "codDocSustento", "numDocSustento", "numAutDocSustento", "fechaEmisionDocSustento"})
@Data
public class Destinatario {
	private String identificacionDestinatario;
	private String razonSocialDestinatario;
	private String dirDestinatario;
	private String motivoTraslado;
	private String ruta;
	private String codDocSustento;
	private String numDocSustento;
	private String numAutDocSustento;
	private String fechaEmisionDocSustento;
}
