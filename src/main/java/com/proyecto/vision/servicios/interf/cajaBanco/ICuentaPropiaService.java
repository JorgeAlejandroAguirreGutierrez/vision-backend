package com.proyecto.vision.servicios.interf.cajaBanco;

import java.util.List;

import com.proyecto.vision.modelos.cajaBanco.CuentaPropia;
import com.proyecto.vision.modelos.configuracion.MenuOpcion;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface ICuentaPropiaService extends IGenericoService<CuentaPropia> {
	void validar(CuentaPropia cuentaPropia);
	CuentaPropia activar(CuentaPropia cuentaPropia);
	CuentaPropia inactivar(CuentaPropia cuentaPropia);
	List<CuentaPropia> consultarPorEstado(String estado);
	List<CuentaPropia> consultarPorEmpresa(long empresaId);
	List<CuentaPropia> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<String> consultarBancoDistintoPorEmpresaYEstado(long empresaId, String estado);
	List<CuentaPropia> consultarPorEmpresaYBanco(long empresaId, String banco);

}
