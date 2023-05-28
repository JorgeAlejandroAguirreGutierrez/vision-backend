package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IKardexService extends IGenericoService<Kardex> {
    List<Kardex> consultarPorProducto(long productoId);
    Kardex obtenerUltimoPorBodega(long bodegaId, long productoId);
    void eliminar(long tipoComprobanteId, long tipoOperacionId, String secuencial);
}
