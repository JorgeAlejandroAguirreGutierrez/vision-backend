package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.SubGrupoProducto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubGrupoProductoRepository extends JpaRepository<SubGrupoProducto, Long>, JpaSpecificationExecutor<SubGrupoProducto> {
	@Query(value="SELECT sgp FROM SubGrupoProducto sgp WHERE sgp.nombre = :nombre")
    Optional<SubGrupoProducto> findByNombre(
            @Param("nombre") String nombre);
}
