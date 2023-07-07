package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.entrega.GuiaRemision;

public interface IGuiaRemisionElectronicaService {
	GuiaRemision enviar(long guiaRemisionId);
}
