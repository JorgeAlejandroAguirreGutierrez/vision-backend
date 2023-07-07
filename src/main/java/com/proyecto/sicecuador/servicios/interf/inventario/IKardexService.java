package com.proyecto.vision.servicios.interf.inventario;

import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IKardexService extends IGenericoService<Kardex> {
    List<Kardex> consultarPorProducto(long productoId);
    Kardex obtenerUltimoPorBodega(long bodegaId, long productoId);
    void eliminar(long tipoComprobanteId, long tipoOperacionId, String secuencial);
}
