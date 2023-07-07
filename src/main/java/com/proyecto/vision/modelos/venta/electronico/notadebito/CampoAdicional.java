package com.proyecto.vision.modelos.venta.electronico.notadebito;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "campoAdicional")
@Getter
@Setter
public class CampoAdicional {
    @XmlAttribute(name = "nombre")
    private String nombre;
    @XmlValue
    private String valor;
}
