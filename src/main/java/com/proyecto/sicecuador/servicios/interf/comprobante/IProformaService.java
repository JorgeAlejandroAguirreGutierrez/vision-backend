package com.proyecto.sicecuador.servicios.interf.comprobante;

import java.util.List;

import com.proyecto.sicecuador.modelos.comprobante.Proforma;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IProformaService extends IGenericoService<Proforma> {
	Proforma activar(Proforma proforma);
	Proforma inactivar(Proforma proforma);
	List<Proforma> consultarActivos();
}
