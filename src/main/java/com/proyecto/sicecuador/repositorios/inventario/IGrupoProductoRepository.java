package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.GrupoProducto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoProductoRepository extends JpaRepository<GrupoProducto, Long>, JpaSpecificationExecutor<GrupoProducto> {

    @Query(value = "select gp from GrupoProducto gp order by gp.codigo asc")
    List<GrupoProducto> consultar();
    @Query(value = "select gp from GrupoProducto gp where gp.estado=:estado order by gp.codigo asc")
    List<GrupoProducto> consultarPorEstado(String estado);
    @Query(value = "select gp from GrupoProducto gp where gp.empresa.id = :empresaId order by gp.codigo asc")
    List<GrupoProducto> consultarPorEmpresa(long empresaId);
    @Query(value = "select gp from GrupoProducto gp where gp.empresa.id =:empresaId and gp.estado=:estado order by gp.codigo asc")
    List<GrupoProducto> consultarPorEmpresaYEstado(long empresaId, String estado);
	@Query(value = "select distinct gp.grupo from grupo_producto gp where gp.empresa_id=:empresaId and gp.estado=:estado", nativeQuery = true)
    List<String> findGrupos(long empresaId, String estado);
    @Query(value = "select distinct gp.subgrupo from grupo_producto gp where gp.empresa_id=:empresaId and gp.grupo=:grupo and gp.estado=:estado" ,nativeQuery = true)
    List<String> findSubgrupos(long empresaId, String estado, String grupo);
    @Query(value = "select distinct gp.seccion from grupo_producto gp where gp.empresa_id=:empresaId and gp.grupo=:grupo AND gp.subgrupo=:subgrupo and gp.estado=:estado", nativeQuery = true)
    List<String> findSecciones(long empresaId, String estado, String grupo, String subgrupo);
    @Query(value = "select distinct gp.linea from grupo_producto gp where gp.empresa_id=:empresaId and gp.grupo=:grupo and gp.subgrupo=:subgrupo and gp.seccion=:seccion and gp.estado=:estado", nativeQuery = true)
    List<String> findLineas(long empresaId, String estado, String grupo, String subgrupo, String seccion);
    @Query(value = "select distinct gp.sublinea from grupo_producto gp where gp.empresa_id=:empresaId and gp.grupo=:grupo AND gp.subgrupo=:subgrupo and gp.seccion=:seccion and gp.linea=:linea and gp.estado=:estado", nativeQuery = true)
    List<String> findSublineas(long empresaId, String estado, String grupo, String subgrupo, String seccion, String linea);
    @Query(value = "select distinct gp.presentacion from grupo_producto gp where gp.empresa_id=:empresaId and gp.grupo=:grupo AND gp.subgrupo=:subgrupo and gp.seccion=:seccion and gp.linea=:linea and gp.sublinea=:sublinea and gp.estado=:estado", nativeQuery = true)
    List<String> findPresentaciones(long empresaId, String estado, String grupo, String subgrupo, String seccion, String linea, String sublinea);
    @Query(value = "select * from grupo_producto gp WHERE gp.grupo=:grupo AND gp.subgrupo=:subgrupo AND gp.seccion=:seccion AND gp.linea=:linea AND gp.sublinea=:sublinea AND gp.presentacion=:presentacion and gp.estado=:estado order by gp.codigo asc", nativeQuery = true)
    Optional<GrupoProducto> findGrupoProducto(String grupo, String subgrupo, String seccion, String linea, String sublinea, String presentacion, String estado);

}
