package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.CategoriaProducto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long>, JpaSpecificationExecutor<CategoriaProducto> {
    @Query(value = "select cp from CategoriaProducto cp order by cp.id desc")
    List<CategoriaProducto> consultar();
    @Query(value = "select cp from CategoriaProducto cp where cp.estado=:estado order by cp.id desc")
    List<CategoriaProducto> consultarPorEstado(String estado);
    @Query(value = "select cp from CategoriaProducto cp where cp.abreviatura = :abreviatura and cp.estado=:estado order by cp.id desc")
    Optional<CategoriaProducto> obtenerPorAbreviatura(String abreviatura, String estado);
}
