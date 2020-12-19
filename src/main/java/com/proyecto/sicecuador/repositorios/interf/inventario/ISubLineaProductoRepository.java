package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.SubLineaProducto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubLineaProductoRepository extends JpaRepository<SubLineaProducto, Long>, JpaSpecificationExecutor<SubLineaProducto> {
	@Query(value="SELECT slp FROM SubLineaProducto slp WHERE slp.nombre = :nombre")
    Optional<SubLineaProducto> findByNombre(
            @Param("nombre") String nombre);
}
