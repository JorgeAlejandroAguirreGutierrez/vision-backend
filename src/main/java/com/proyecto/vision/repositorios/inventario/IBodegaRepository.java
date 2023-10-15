package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.Bodega;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBodegaRepository extends JpaRepository<Bodega, Long>, JpaSpecificationExecutor<Bodega> {
    @Query(value = "select b from Bodega b order by b.id desc")
    List<Bodega> consultar();
    @Query(value = "select b from Bodega b where b.estado=:estado order by b.id desc")
    List<Bodega> consultarPorEstado(String estado);
    @Query(value = "select b from Bodega b where b.empresa.id = :empresaId and b.estado = :estado order by b.id asc")
    List<Bodega> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select b from Bodega b where b.empresa.id = :empresaId order by b.id desc")
    List<Bodega> consultarPorEmpresa(long empresaId);
}
