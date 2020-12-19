package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.LineaProducto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILineaProductoRepository extends JpaRepository<LineaProducto, Long>, JpaSpecificationExecutor<LineaProducto> {
	@Query(value="SELECT lp FROM LineaProducto lp WHERE lp.nombre = :nombre")
    Optional<LineaProducto> findByNombre(
            @Param("nombre") String nombre);
}
