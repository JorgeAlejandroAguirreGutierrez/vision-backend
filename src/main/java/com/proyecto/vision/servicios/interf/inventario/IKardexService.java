package com.proyecto.vision.servicios.interf.inventario;

import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.Date;
import java.util.List;

public interface IKardexService extends IGenericoService<Kardex> {
    List<Kardex> consultarPorProducto(long productoId);
    List<Kardex> consultarPorProductoYEstado(long productoId, String estado);
    List<Kardex> consultarPorTipoComprobanteYReferencia(long tipoComprobanteId, String referencia);
    Kardex obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYIdLinea(long productoId, long bodegaId, long tipoComprobanteId, String comprobante, long idLinea);
    Kardex obtenerUltimoPorProductoYBodegaYEstado(long productoId, long bodegaId, String estado);
    Kardex obtenerSaldoInicialPorProductoYBodega(long productoId, long bodegaId);
    Kardex obtenerUltimoPorProductoYBodegaYFecha(long productoId, long bodegaId, Date fecha);
    Kardex obtenerUltimoPorProductoYBodegaYTablaTipoComprobante(long productoId, long bodegaId, String tablaTipoComprobante);
    Kardex obtenerPenultimoPorProductoYBodegaYMismaFechaYId(long productoId, long bodegaId, Date fecha, long id);
    Kardex obtenerPenultimoPorProductoYBodegaYMenorFecha(long productoId, long bodegaId, Date fecha);
    void eliminar(long tipoComprobanteId, long tipoOperacionId, String secuencial);
    void recalcularPorProductoYBodegaYFecha(long productoId, long bodegaId, Date fecha);
}
