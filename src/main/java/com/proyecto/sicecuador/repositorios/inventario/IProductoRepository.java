package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.Producto;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
	@Query(value = "select p from Producto p where p.estado=:estado")
    List<Producto> consultarPorEstado(String estado);
	
	@Query(value = "select p from Producto p where p.nombre=:nombre")
    public Optional<Producto> obtenerPorNombre(String nombre);
}
