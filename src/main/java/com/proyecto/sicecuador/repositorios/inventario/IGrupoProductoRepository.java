package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoProductoRepository extends JpaRepository<GrupoProducto, Long>, JpaSpecificationExecutor<GrupoProducto> {

    @Query(value = "select gp from GrupoProducto gp order by gp.codigo desc")
    List<GrupoProducto> consultar();
    @Query(value = "select gp from GrupoProducto gp where gp.estado=:estado order by gp.codigo desc")
    List<GrupoProducto> consultarPorEstado(String estado);
	
	@Query(value = "select distinct gp.grupo from grupo_producto gp where gp.estado=:estado order by gp.codigo desc", nativeQuery = true)
    List<String> findGrupos(String estado);

    @Query(value = "select distinct gp.subgrupo from grupo_producto gp where gp.grupo=:grupo and gp.estado=:estado order by gp.codigo desc" ,nativeQuery = true)
    List<String> findSubgrupos(String grupo, String estado);

    @Query(value = "select distinct gp.seccion from grupo_producto gp where gp.grupo=:grupo AND gp.subgrupo=:subgrupo and gp.estado=:estado order by gp.codigo desc", nativeQuery = true)
    List<String> findSecciones(String grupo, String subgrupo, String estado);
    
    @Query(value = "select distinct gp.linea from grupo_producto gp where gp.grupo=:grupo and gp.subgrupo=:subgrupo and gp.seccion=:seccion and gp.estado=:estado order by gp.codigo desc", nativeQuery = true)
    List<String> findLineas(String grupo, String subgrupo, String seccion, String estado);
    
    @Query(value = "select distinct gp.sublinea from grupo_producto gp where gp.grupo=:grupo AND gp.subgrupo=:subgrupo and gp.seccion=:seccion and gp.linea=:linea and gp.estado=:estado order by gp.codigo desc", nativeQuery = true)
    List<String> findSublineas(String grupo, String subgrupo, String seccion, String linea, String estado);
    
    @Query(value = "select distinct gp.presentacion from grupo_producto gp where gp.grupo=:grupo AND gp.subgrupo=:subgrupo and gp.seccion=:seccion and gp.linea=:linea and gp.sublinea=:sublinea and gp.estado=:estado order by gp.codigo desc", nativeQuery = true)
    List<String> findPresentaciones(String grupo, String subgrupo, String seccion, String linea, String sublinea, String estado);
    
    @Query(value = "select * from grupo_producto gp WHERE gp.grupo=:grupo AND gp.subgrupo=:subgrupo AND gp.seccion=:seccion AND gp.linea=:linea AND gp.sublinea=:sublinea AND gp.presentacion=:presentacion and gp.estado=:estado order by gp.codigo desc", nativeQuery = true)
    Optional<GrupoProducto> findGrupoProducto(String grupo, String subgrupo, String seccion, String linea, String sublinea, String presentacion, String estado);

}
