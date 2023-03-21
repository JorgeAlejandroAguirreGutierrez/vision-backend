package com.proyecto.sicecuador.modelos.comprobante.electronico.guiaremision;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "guiaRemision")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"infoTributaria", "infoGuiaRemision", "destinatarios", "infoAdicional"})
@Data
public class GuiaRemisionElectronica {
	//DATOS DEL SRI CAMPOS EXACTAMENTE COMO LOS TIENE
	@XmlAttribute(name = "id")
    private String id="comprobante";
	@XmlAttribute(name = "version")
    private String version="2.1.0";
	private InfoTributaria infoTributaria;
	private InfoGuiaRemision infoGuiaRemision;
	private Destinatarios destinatarios;
	private List<CampoAdicional> infoAdicional;
}
