package com.proyecto.vision.modelos.venta.electronico.notadebito;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "notaDebito")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"infoTributaria", "infoNotaDebito", "motivos", "infoAdicional"})
@Getter
@Setter
public class NotaDebitoElectronica {
	@XmlAttribute(name = "id")
    private String id="comprobante";
	@XmlAttribute(name = "version")
    private String version="1.0.0";
	private InfoTributaria infoTributaria;
	private InfoNotaDebito infoNotaDebito;
	private Motivos motivos;
	private InfoAdicional infoAdicional;

}
