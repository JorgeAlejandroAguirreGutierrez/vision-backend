package com.proyecto.sicecuador.modelos.comprobante.electronico.factura;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Data
@XmlRootElement(name = "campoAdicional")
public class CampoAdicional {
    @XmlAttribute(name = "nombre")
    private String nombre;
    @XmlValue
    private String valor;
}
