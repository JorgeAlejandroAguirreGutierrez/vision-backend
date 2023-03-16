package com.proyecto.sicecuador.modelos.comprobante.electronico.factura;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "factura")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"infoTributaria", "infoFactura", "detalles", "infoAdicional"})
@Data
public class FacturaElectronica {
	//DATOS DEL SRI CAMPOS EXACTAMENTE COMO LOS TIENE
	@XmlAttribute(name = "id")
    private String id="comprobante";
	@XmlAttribute(name = "version")
    private String version="2.1.0";
	private InfoTributaria infoTributaria;
	private InfoFactura infoFactura;
	private Detalles detalles;
	private List<CampoAdicional> infoAdicional;
}
