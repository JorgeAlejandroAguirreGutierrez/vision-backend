package com.proyecto.sicecuador.servicios.interf.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface ICuentaContableService extends IGenericoService<CuentaContable> {
	void validar(CuentaContable cuentaContable);
	CuentaContable activar(CuentaContable cuentacontable);
	CuentaContable inactivar(CuentaContable cuentacontable);
	List<CuentaContable> consultarActivos();
    List<CuentaContable> buscar(CuentaContable cuentaContable);
}
