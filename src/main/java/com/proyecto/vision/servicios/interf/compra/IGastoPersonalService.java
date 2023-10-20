package com.proyecto.vision.servicios.interf.compra;

import com.proyecto.vision.modelos.compra.GastoPersonal;
import com.proyecto.vision.modelos.compra.GastoPersonalLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IGastoPersonalService extends IGenericoService<GastoPersonal> {
	void validar(GastoPersonal facturaCompra);
	GastoPersonal anular(GastoPersonal facturaCompra);
	List<GastoPersonal> consultarPorEstado(String estado);
	List<GastoPersonal> consultarPorEmpresa(long empresaId);
	List<GastoPersonal> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<GastoPersonal> consultarPorProveedorYEmpresaYEstado(long proveedorId, long empresaId, String estado);
	void validarLinea(GastoPersonalLinea GastoPersonalLinea);
}