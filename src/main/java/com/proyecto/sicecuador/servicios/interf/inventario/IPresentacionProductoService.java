package com.proyecto.sicecuador.servicios.interf.inventario;

import java.util.List;

import com.proyecto.sicecuador.modelos.inventario.PresentacionProducto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IPresentacionProductoService extends IGenericoService<PresentacionProducto> {
	List<PresentacionProducto> buscar(PresentacionProducto presentacion_producto);
}
