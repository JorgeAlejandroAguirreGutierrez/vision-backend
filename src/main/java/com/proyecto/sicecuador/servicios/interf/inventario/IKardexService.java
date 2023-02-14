package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IKardexService extends IGenericoService<Kardex> {
    List<Kardex> consultarPorProducto(long productoId);
    Kardex obtenerUltimoPorFecha(long bodegaId, long productoId);
    void eliminar(String documento, String operacion, String secuencia);
}
