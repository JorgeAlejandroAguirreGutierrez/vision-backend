package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICaracteristicaRepository extends JpaRepository<Caracteristica, Long>, JpaSpecificationExecutor<Caracteristica> {
    @Query(value = "SELECT * FROM caracteristica c, producto p, bodega b WHERE p.id=c.producto_id and c.bodega_id=b.id and p.id = :producto_id and c.bodega_id = :bodega_id", nativeQuery = true)
    List<Caracteristica> consultarBienExistenciasBodega(@Param("producto_id") long producto_id, @Param("bodega_id") long bodega_id);
    @Query(value = "SELECT * FROM caracteristica c, producto p WHERE p.id=c.producto_id and p.id = :producto_id", nativeQuery = true)
    List<Caracteristica> consultarBienExistencias(@Param("producto_id") long producto_id);
}
