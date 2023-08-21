package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.venta.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IKardexRepository extends JpaRepository<Kardex, Long>, JpaSpecificationExecutor<Kardex> {
    @Query(value = "select * from kardex k where k.producto_id = :productoId order by date(k.fecha), k.id", nativeQuery = true)
    List<Kardex> consultarPorProducto(long productoId);
    @Query(value = "select k from Kardex k where date(k.fecha) between :fechaInicio and :fechaFinal and k.producto.id = :productoId order by k.codigo desc")
    List<Kardex> consultarPorFechaInicioYFechaFinalYProducto(Date fechaInicio, Date fechaFinal, long productoId);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and k.fecha > :fecha order by k.fecha asc, k.id", nativeQuery = true)
    List<Kardex> consultarPorProductoYBodegaYFechaMayor(long productoId, long bodegaId, Date fecha);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and k.tipo_comprobante_id = :tipoComprobanteId and k.referencia = :comprobante order by k.fecha desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYPosicion(long productoId, long bodegaId, long tipoComprobanteId, String comprobante);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId order by k.fecha desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerUltimoPorProductoYBodega(long productoId, long bodegaId);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and date(k.fecha) <= :fecha order by date(k.fecha) desc, k.id desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerUltimoPorProductoYBodegaYFecha(long productoId, long bodegaId, Date fecha);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and k.tipo_comprobante_id = :tipoComprobanteId order by date(k.fecha) desc, k.id desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerUltimoPorProductoYBodegaYTablaTipoComprobante(long productoId, long bodegaId, long tipoComprobanteId);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and date(k.fecha) = :fecha and k.id < :id order by date(k.fecha) desc, k.id desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerPenultimoPorProductoYBodegaYMismaFechaYId(long productoId, long bodegaId, Date fecha, long id);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and date(k.fecha) < :fecha order by date(k.fecha) desc, k.id desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerPenultimoPorProductoYBodegaYMenorFecha(long productoId, long bodegaId, Date fecha);
    @Modifying
    @Transactional
    @Query(value = "delete from kardex k where k.tipo_comprobante_id = :tipoComprobanteId and k.tipo_operacion_id = :tipoOperacionId and k.referencia = :referencia", nativeQuery = true)
    void eliminar(long tipoComprobanteId, long tipoOperacionId, String referencia);

}
