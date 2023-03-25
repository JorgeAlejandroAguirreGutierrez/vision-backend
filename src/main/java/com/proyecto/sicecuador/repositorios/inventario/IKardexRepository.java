package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface IKardexRepository extends JpaRepository<Kardex, Long>, JpaSpecificationExecutor<Kardex> {
    @Query(value = "select * from kardex k where k.producto_id = :productoId order by k.fecha desc", nativeQuery = true)
    List<Kardex> consultarPorProducto(long productoId);
    @Query(value = "select * from kardex k where k.producto_id = :productoId and k.bodega_id = :bodegaId order by k.fecha desc limit 1", nativeQuery = true)
    Optional<Kardex> obtenerUltimoPorFecha(long bodegaId, long productoId);
    @Modifying
    @Transactional
    @Query(value = "delete from kardex k where k.documento = :documento and k.operacion = :operacion and k.secuencia = :secuencia", nativeQuery = true)
    void eliminar(String documento, String operacion, String secuencia);
}
