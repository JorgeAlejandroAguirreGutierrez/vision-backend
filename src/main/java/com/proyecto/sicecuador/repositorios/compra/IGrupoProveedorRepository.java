package com.proyecto.sicecuador.repositorios.compra;

import com.proyecto.sicecuador.modelos.compra.GrupoProveedor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoProveedorRepository extends JpaRepository<GrupoProveedor, Long>, JpaSpecificationExecutor<GrupoProveedor> {
    @Query(value = "select gp from GrupoProveedor gp order by gp.codigo desc")
    List<GrupoProveedor> consultar();
    @Query(value = "select gp from GrupoProveedor gp where gp.estado=:estado order by gp.codigo desc")
    List<GrupoProveedor> consultarPorEstado(String estado);
    @Query(value = "select gp from GrupoProveedor gp where gp.codigo like '%'||:codigo||'%' and gp.descripcion like '%'||:descripcion||'%' and gp.abreviatura like '%'||:abreviatura||'%' order by gp.estado desc")
    List<GrupoProveedor> buscar(String codigo, String descripcion, String abreviatura);
}
