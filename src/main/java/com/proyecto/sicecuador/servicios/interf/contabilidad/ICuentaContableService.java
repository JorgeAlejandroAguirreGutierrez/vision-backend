package com.proyecto.sicecuador.servicios.interf.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface ICuentaContableService extends IGenericoService<CuentaContable> {
	void validar(CuentaContable cuentaContable);
	CuentaContable activar(CuentaContable cuentacontable);
	CuentaContable inactivar(CuentaContable cuentacontable);
	List<CuentaContable> consultarPorEstado(String estado);
	List<CuentaContable> consultarPorEmpresa(long empresaId);
	List<CuentaContable> consultarPorEmpresaYEstado(long empresaId, String estado);
    List<CuentaContable> buscar(CuentaContable cuentaContable);
}
