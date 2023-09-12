package com.proyecto.vision.modelos.venta.electronico.factura;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "factura")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"infoTributaria", "infoFactura", "detalles", "infoAdicional"})
@Getter
@Setter
public class FacturaElectronica {
	//DATOS DEL SRI CAMPOS EXACTAMENTE COMO LOS TIENE
	@XmlAttribute(name = "id")
    private String id="comprobante";
	@XmlAttribute(name = "version")
    private String version="1.1.0";
	private InfoTributaria infoTributaria;
	private InfoFactura infoFactura;
	private Detalles detalles;
	private InfoAdicional infoAdicional;
}
