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
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.estado = :estado order by date(k.fecha), k.id", nativeQuery = true)
    List<Kardex> consultarPorProductoYEstado(long productoId, String estado);
    @Query(value = "select * from kardex k where k.tipo_comprobante_id = :tipoComprobanteId and k.referencia = :referencia order by date(k.fecha), k.id", nativeQuery = true)
    List<Kardex> consultarPorTipoComprobanteYReferencia(long tipoComprobanteId, String referencia);
    @Query(value = "select k from Kardex k where date(k.fecha) between :fechaInicio and :fechaFinal and k.producto.id = :productoId order by k.codigo desc")
    List<Kardex> consultarPorFechaInicioYFechaFinalYProducto(Date fechaInicio, Date fechaFinal, long productoId);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and date(k.fecha) > :fecha and k.estado = :estado order by date(k.fecha) asc, k.id", nativeQuery = true)
    List<Kardex> consultarPorProductoYBodegaYFechaMayorYEstado(long productoId, long bodegaId, Date fecha, String estado);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and k.tipo_comprobante_id = :tipoComprobanteId and k.referencia = :comprobante and k.id_linea = :idLinea order by k.fecha desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYIdLinea(long productoId, long bodegaId, long tipoComprobanteId, String comprobante, long idLinea);
    @Query(value = "select * from kardex k where k.tipo_comprobante_id = :tipoComprobanteId and k.producto_id = :productoId and k.bodega_id = :bodegaId order by date(k.fecha) desc, k.id  limit 1", nativeQuery = true)
    Optional<Kardex> obtenerSaldoInicialPorProductoYBodega(long tipoComprobanteId, long productoId, long bodegaId);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and k.estado = :estado order by date(k.fecha) desc, k.id desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerUltimoPorProductoYBodegaYEstado(long productoId, long bodegaId, String estado);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and date(k.fecha) <= :fecha and k.estado = :estado order by date(k.fecha) desc, k.id desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerUltimoPorProductoYBodegaYFechaYEstado(long productoId, long bodegaId, Date fecha, String estado);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and k.tipo_comprobante_id = :tipoComprobanteId order by date(k.fecha) desc, k.id desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerUltimoPorProductoYBodegaYTablaTipoComprobante(long productoId, long bodegaId, long tipoComprobanteId);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and date(k.fecha) = :fecha and k.id < :id order by date(k.fecha) desc, k.id desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerPenultimoPorProductoYBodegaYMismaFechaYId(long productoId, long bodegaId, Date fecha, long id);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId and date(k.fecha) < :fecha order by date(k.fecha) desc, k.id desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerPenultimoPorProductoYBodegaYMenorFecha(long productoId, long bodegaId, Date fecha);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and date(k.fecha) <= :fecha and k.estado = :estado order by date(k.fecha) desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerUltimoPorProductoYFechaYEstado(long productoId, Date fecha, String estado);
    @Modifying
    @Transactional
    @Query(value = "delete from kardex k where k.tipo_comprobante_id = :tipoComprobanteId and k.tipo_operacion_id = :tipoOperacionId and k.referencia = :referencia", nativeQuery = true)
    void eliminar(long tipoComprobanteId, long tipoOperacionId, String referencia);

}
