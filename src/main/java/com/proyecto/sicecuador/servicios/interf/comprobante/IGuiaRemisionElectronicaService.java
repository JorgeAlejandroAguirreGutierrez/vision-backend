package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;

public interface IGuiaRemisionElectronicaService {
	GuiaRemision enviar(long guiaRemisionId);
}
