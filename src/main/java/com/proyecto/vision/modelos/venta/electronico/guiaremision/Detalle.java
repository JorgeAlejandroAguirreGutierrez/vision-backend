package com.proyecto.vision.modelos.venta.electronico.guiaremision;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "detalle")
@XmlType(propOrder={"codigoInterno", "descripcion", "cantidad"})
@Getter
@Setter
public class Detalle {
	private String codigoInterno;
	private String descripcion;
	private String cantidad;
}
