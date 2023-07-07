package com.proyecto.vision.modelos.venta.electronico.guiaremision;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "campoAdicional")
@Getter
@Setter
public class CampoAdicional {
    @XmlAttribute
    private String nombre;
    @XmlValue
    private String valor;
}
