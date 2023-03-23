package com.proyecto.sicecuador.modelos.comprobante.electronico.notacredito;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "infoAdicional")
@Data
public class InfoAdicional {
	List<CampoAdicional> campoAdicional;
}