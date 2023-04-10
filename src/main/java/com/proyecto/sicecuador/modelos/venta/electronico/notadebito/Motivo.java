package com.proyecto.sicecuador.modelos.venta.electronico.notadebito;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "motivo")
@XmlType(propOrder={"razon", "valor"})
@Data
public class Motivo {
	private String razon;
	private double valor;
}
