package com.proyecto.vision.servicios.interf.inventario;

import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.Date;
import java.util.List;

public interface IKardexService extends IGenericoService<Kardex> {
    List<Kardex> consultarPorProducto(long productoId);
    Kardex obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYPosicion(long productoId, long bodegaId, long tipoComprobanteId, String comprobante, long posicion);
    Kardex obtenerUltimoPorProductoYBodega(long productoId, long bodegaId);
    Kardex obtenerUltimoPorProductoYBodegaYFecha(long productoId, long bodegaId, Date fecha);
    Kardex obtenerUltimoPorProductoYBodegaYTablaTipoComprobante(long productoId, long bodegaId, String tablaTipoComprobante);
    Kardex obtenerPenultimoPorProductoYBodegaYMismaFechaYId(long productoId, long bodegaId, Date fecha, long id);
    Kardex obtenerPenultimoPorProductoYBodegaYMenorFecha(long productoId, long bodegaId, Date fecha);
    void eliminar(long tipoComprobanteId, long tipoOperacionId, String secuencial);
    void recalcularPorProductoYBodegaYFecha(long productoId, long bodegaId, Date fecha);
}
