package com.proyecto.sicecuador.modelos.venta.electronico.notacredito;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "campoAdicional")
@Data
public class CampoAdicional {
    @XmlAttribute
    private String nombre;
    @XmlValue
    private String valor;
}
