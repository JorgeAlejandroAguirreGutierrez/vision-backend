package com.proyecto.vision.servicios.interf.inventario;

import java.util.List;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IBodegaService extends IGenericoService<Bodega> {
	void validar(Bodega bodega);
	Bodega activar(Bodega bodega);
	Bodega inactivar(Bodega bodega);
	List<Bodega> consultarPorEstado(String estado);
	List<Bodega> consultarPorEmpresa(long empresaId);
	List<Bodega> consultarPorEmpresaYEstado(long empresaId, String estado);
}
