package com.proyecto.vision.modelos.venta.electronico.notadebito;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "motivo")
@XmlType(propOrder={"razon", "valor"})
@Getter
@Setter
public class Motivo {
	private String razon;
	private double valor;
}
