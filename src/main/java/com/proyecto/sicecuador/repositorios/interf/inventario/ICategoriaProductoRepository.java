package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long>, JpaSpecificationExecutor<CategoriaProducto> {
	@Query(value="SELECT cp FROM CategoriaProducto cp WHERE cp.nombre = :nombre")
    Optional<CategoriaProducto> findByNombre(
            @Param("nombre") String nombre);
}
