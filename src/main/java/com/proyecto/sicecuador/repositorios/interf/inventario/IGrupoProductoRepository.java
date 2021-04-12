package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoProductoRepository extends JpaRepository<GrupoProducto, Long>, JpaSpecificationExecutor<GrupoProducto> {
	@Query(value = "SELECT DISTINCT gp.grupo FROM grupo_producto gp", nativeQuery = true)
    List<String> findGrupos();

    @Query(value = "SELECT DISTINCT gp.subgrupo FROM grupo_producto gp WHERE gp.grupo=:grupo" ,nativeQuery = true)
    List<String> findSubgrupos(String grupo);

    @Query(value = "SELECT DISTINCT gp.categoria FROM grupo_producto gp WHERE gp.grupo=:grupo AND gp.subgrupo=:subgrupo", nativeQuery = true)
    List<String> findCategorias(String grupo, String subgrupo);
    
    @Query(value = "SELECT DISTINCT gp.linea FROM grupo_producto gp WHERE gp.grupo=:grupo AND gp.subgrupo=:subgrupo AND gp.categoria=:categoria", nativeQuery = true)
    List<String> findLineas(String grupo, String subgrupo, String categoria);
    
    @Query(value = "SELECT DISTINCT gp.sublinea FROM grupo_producto gp WHERE gp.grupo=:grupo AND gp.subgrupo=:subgrupo AND gp.categoria=:categoria AND gp.linea=:linea", nativeQuery = true)
    List<String> findSublineas(String grupo, String subgrupo, String categoria, String linea);
    
    @Query(value = "SELECT DISTINCT gp.presentacion FROM grupo_producto gp WHERE gp.grupo=:grupo AND gp.subgrupo=:subgrupo AND gp.categoria=:categoria AND gp.linea=:linea AND gp.sublinea=:sublinea", nativeQuery = true)
    List<String> findPresentaciones(String grupo, String subgrupo, String categoria, String linea, String sublinea);
    
    @Query(value = "SELECT * FROM grupo_producto gp WHERE gp.grupo=:grupo AND gp.subgrupo=:subgrupo AND gp.categoria=:categoria AND gp.linea=:linea AND gp.sublinea=:sublinea AND gp.presentacion=:presentacion", nativeQuery = true)
    Optional<GrupoProducto> findGrupoProducto(String grupo, String subgrupo, String categoria, String linea, String sublinea, String presentacion);

}
