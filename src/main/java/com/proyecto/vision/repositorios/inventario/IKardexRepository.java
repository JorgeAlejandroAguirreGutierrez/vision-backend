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
    @Query(value = "select * from kardex k where k.producto_id = :productoId order by k.fecha desc", nativeQuery = true)
    List<Kardex> consultarPorProducto(long productoId);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId order by k.fecha desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerUltimoPorBodegaYProducto(long bodegaId, long productoId);
    @Modifying
    @Transactional
    @Query(value = "delete from kardex k where k.tipo_comprobante_id = :tipoComprobanteId and k.tipo_operacion_id = :tipoOperacionId and k.referencia = :referencia", nativeQuery = true)
    void eliminar(long tipoComprobanteId, long tipoOperacionId, String referencia);
    @Query(value = "select k from Kardex k where date(k.fecha) between :fechaInicio and :fechaFinal and k.producto.id = :productoId order by k.codigo asc")
    List<Kardex> consultarPorFechaInicioYFechaFinalYProducto(Date fechaInicio, Date fechaFinal, long productoId);
}
