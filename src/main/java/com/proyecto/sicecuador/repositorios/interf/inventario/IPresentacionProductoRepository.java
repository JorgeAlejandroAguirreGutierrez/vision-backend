package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.PresentacionProducto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPresentacionProductoRepository extends JpaRepository<PresentacionProducto, Long>, JpaSpecificationExecutor<PresentacionProducto> {
	@Query(value="SELECT pp FROM LineaProducto pp WHERE pp.nombre = :nombre")
    Optional<PresentacionProducto> findByNombre(
            @Param("nombre") String nombre);
}
