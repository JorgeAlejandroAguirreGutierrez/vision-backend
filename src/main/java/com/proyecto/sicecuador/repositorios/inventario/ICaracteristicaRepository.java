package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICaracteristicaRepository extends JpaRepository<Caracteristica, Long>, JpaSpecificationExecutor<Caracteristica> {
    @Query(value = "select * from caracteristica c, producto p, bodega b WHERE p.id=c.producto_id and c.bodega_id=b.id and p.id = :producto_id and c.bodega_id = :bodega_id and c.factura_detalle_id is null", nativeQuery = true)
    List<Caracteristica> consultarBienExistenciasBodega(long producto_id, long bodega_id);
    @Query(value = "select * from caracteristica c, producto p WHERE p.id=c.producto_id and p.id = :producto_id", nativeQuery = true)
    List<Caracteristica> consultarBienExistencias(long producto_id);
}
