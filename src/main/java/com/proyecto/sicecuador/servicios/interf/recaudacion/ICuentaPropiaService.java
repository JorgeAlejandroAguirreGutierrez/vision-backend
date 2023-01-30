package com.proyecto.sicecuador.servicios.interf.recaudacion;

import java.util.List;

import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ICuentaPropiaService extends IGenericoService<CuentaPropia> {
	void validar(CuentaPropia cuentaPropia);
	CuentaPropia activar(CuentaPropia cuentaPropia);
	CuentaPropia inactivar(CuentaPropia cuentaPropia);
	List<CuentaPropia> consultarActivos();
	
}
