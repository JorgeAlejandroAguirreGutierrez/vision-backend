package com.proyecto.sicecuador.modelos.comprobante.electronico.notacredito;
import com.proyecto.sicecuador.modelos.comprobante.electronico.notacredito.CampoAdicional;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "notaCredito")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"infoTributaria", "infoNotaCredito", "detalles", "infoAdicional"})
@Data
public class NotaCreditoElectronica {
	//DATOS DEL SRI CAMPOS EXACTAMENTE COMO LOS TIENE
	@XmlAttribute(name = "id")
    private String id="comprobante";

	@XmlAttribute(name = "version")
    private String version="1.1.0";

	private InfoTributaria infoTributaria;

	private InfoNotaCredito infoNotaCredito;

	private Detalles detalles;
	private List<CampoAdicional> infoAdicional;
}
