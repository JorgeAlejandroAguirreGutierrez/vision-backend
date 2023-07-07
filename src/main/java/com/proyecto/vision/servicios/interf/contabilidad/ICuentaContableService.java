package com.proyecto.vision.servicios.interf.contabilidad;

import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface ICuentaContableService extends IGenericoService<CuentaContable> {
	void validar(CuentaContable cuentaContable);
	List<CuentaContable> consultarPorEmpresa(long empresa);
	List<CuentaContable> consultarPorEstado(String estado);
	List<CuentaContable> consultarPorEmpresaYEstado(long empresa, String estado);
	CuentaContable activar(CuentaContable cuentacontable);
	CuentaContable inactivar(CuentaContable cuentacontable);
    List<CuentaContable> buscar(CuentaContable cuentaContable);
}
