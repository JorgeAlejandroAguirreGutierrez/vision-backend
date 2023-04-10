package com.proyecto.sicecuador.modelos.venta.electronico.notadebito;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "campoAdicional")
@Data
public class CampoAdicional {
    @XmlAttribute(name = "nombre")
    private String nombre;
    @XmlValue
    private String valor;
}
