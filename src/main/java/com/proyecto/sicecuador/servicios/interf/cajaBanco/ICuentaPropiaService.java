package com.proyecto.sicecuador.servicios.interf.cajaBanco;

import java.util.List;

import com.proyecto.sicecuador.modelos.cajaBanco.CuentaPropia;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ICuentaPropiaService extends IGenericoService<CuentaPropia> {
	void validar(CuentaPropia cuentaPropia);
	CuentaPropia activar(CuentaPropia cuentaPropia);
	CuentaPropia inactivar(CuentaPropia cuentaPropia);
	List<CuentaPropia> consultarActivos();
	
}