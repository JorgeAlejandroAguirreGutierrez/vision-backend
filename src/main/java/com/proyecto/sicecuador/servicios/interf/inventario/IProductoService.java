package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IProductoService extends IGenericoService<Producto> {
    List<Producto> consultarBien();
    List<Producto> consultarServicio();
    List<Producto> consultarActivoFijo();
    List<Producto> consultarBodega();
    List<Producto> buscar(Producto producto);
}
