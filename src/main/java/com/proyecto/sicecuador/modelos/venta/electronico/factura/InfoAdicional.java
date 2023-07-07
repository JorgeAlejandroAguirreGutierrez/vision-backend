package com.proyecto.vision.modelos.venta.electronico.factura;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "infoAdicional")
@Getter
@Setter
public class InfoAdicional {
	List<CampoAdicional> campoAdicional;
}
