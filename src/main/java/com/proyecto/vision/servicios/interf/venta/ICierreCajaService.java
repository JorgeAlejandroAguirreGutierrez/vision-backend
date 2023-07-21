package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.CierreCaja;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface ICierreCajaService extends IGenericoService<CierreCaja> {
	void validar(CierreCaja calificacionCliente);
	List<CierreCaja> consultarPorEmpresa(long empresaId);
	List<CierreCaja> consultarPorEstado(String estado);
	List<CierreCaja> consultarPorEmpresaYEstado(long empresaId, String estado);
	CierreCaja activar(CierreCaja calificacionCliente);
	CierreCaja inactivar(CierreCaja calificacionCliente);
}
