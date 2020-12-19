package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoProductoRepository extends JpaRepository<GrupoProducto, Long>, JpaSpecificationExecutor<GrupoProducto> {
	@Query(value="SELECT gp FROM GrupoProducto gp WHERE gp.nombre = :nombre")
    Optional<GrupoProducto> findByNombre(
            @Param("nombre") String nombre);
}
