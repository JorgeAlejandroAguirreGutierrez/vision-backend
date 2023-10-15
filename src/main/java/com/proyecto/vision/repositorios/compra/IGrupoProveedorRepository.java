package com.proyecto.vision.repositorios.compra;

import com.proyecto.vision.modelos.compra.GrupoProveedor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoProveedorRepository extends JpaRepository<GrupoProveedor, Long>, JpaSpecificationExecutor<GrupoProveedor> {
    @Query(value = "select gp from GrupoProveedor gp order by gp.id desc")
    List<GrupoProveedor> consultar();
    @Query(value = "select gp from GrupoProveedor gp where gp.estado=:estado order by gp.id desc")
    List<GrupoProveedor> consultarPorEstado(String estado);
    @Query(value = "select gp from GrupoProveedor gp where gp.empresa.id = :empresaId order by gp.id desc")
    List<GrupoProveedor> consultarPorEmpresa(long empresaId);
    @Query(value = "select gp from GrupoProveedor gp where gp.empresa.id = :empresaId and gp.estado = :estado order by gp.id asc")
    List<GrupoProveedor> consultarPorEmpresaYEstado(long empresaId, String estado);
}
