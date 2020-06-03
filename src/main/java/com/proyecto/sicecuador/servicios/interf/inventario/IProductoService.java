package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface IProductoService extends IGenericoService<Producto> {
    List<Producto> consultarBien();
    List<Producto> consultarServicio();
    List<Producto> consultarActivoFijo();
    List<Producto> consultarBodega();
    Optional<Producto> consultarBienExitencias(Producto producto);
}
