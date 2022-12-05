package com.proyecto.sicecuador.servicios.interf.inventario;

import java.util.List;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IBodegaService extends IGenericoService<Bodega> {
	Bodega activar(Bodega bodega);
	Bodega inactivar(Bodega bodega);
	List<Bodega> consultarActivos();
}
