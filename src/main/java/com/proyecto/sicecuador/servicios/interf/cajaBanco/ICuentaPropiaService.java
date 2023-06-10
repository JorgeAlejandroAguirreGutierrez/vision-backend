package com.proyecto.sicecuador.servicios.interf.cajaBanco;

import java.util.List;

import com.proyecto.sicecuador.modelos.cajaBanco.CuentaPropia;
import com.proyecto.sicecuador.modelos.configuracion.MenuOpcion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ICuentaPropiaService extends IGenericoService<CuentaPropia> {
	void validar(CuentaPropia cuentaPropia);
	CuentaPropia activar(CuentaPropia cuentaPropia);
	CuentaPropia inactivar(CuentaPropia cuentaPropia);
	List<CuentaPropia> consultarPorEstado(String estado);
	List<CuentaPropia> consultarPorEmpresa(long empresaId);
	List<CuentaPropia> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<CuentaPropia> consultarPorBanco(String banco);
	List<String> consultarPorEstadoDistintoBancoAbreviatura(String estado);
}
