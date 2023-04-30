package com.proyecto.sicecuador.modelos.venta.electronico.guiaremision;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "guiaRemision")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"infoTributaria", "infoGuiaRemision", "destinatarios", "infoAdicional"})
@Getter
@Setter
public class GuiaRemisionElectronica {
	//DATOS DEL SRI CAMPOS EXACTAMENTE COMO LOS TIENE
	@XmlAttribute(name = "id")
    private String id="comprobante";
	@XmlAttribute(name = "version")
    private String version="1.1.0";
	private InfoTributaria infoTributaria;
	private InfoGuiaRemision infoGuiaRemision;
	private Destinatarios destinatarios;
	private InfoAdicional infoAdicional;
}
