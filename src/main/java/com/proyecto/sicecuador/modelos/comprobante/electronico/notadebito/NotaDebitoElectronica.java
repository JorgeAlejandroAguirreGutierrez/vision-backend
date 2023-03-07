package com.proyecto.sicecuador.modelos.comprobante.electronico.notadebito;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "notaDebito")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"infoTributaria", "infoNotaDebito", "motivos"})
@Data
public class NotaDebitoElectronica {
	@XmlAttribute(name = "id")
    private String id="comprobante";

	@XmlAttribute(name = "version")
    private String version="2.1.0";

	private InfoTributaria infoTributaria;

	private InfoNotaDebito infoNotaDebito;

	private Motivos motivos;

	private String infoAdicional;

}
