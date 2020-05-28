package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.BodegaProducto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface IBodegaProductoService extends IGenericoService<BodegaProducto> {
    List<BodegaProducto> consultarProducto(BodegaProducto bodega_producto);
    Optional<BodegaProducto> obtenerProductoBodega(BodegaProducto bodega_producto);
}
