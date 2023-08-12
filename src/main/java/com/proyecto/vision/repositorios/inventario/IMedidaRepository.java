package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.Medida;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedidaRepository extends JpaRepository<Medida, Long>, JpaSpecificationExecutor<Medida> {
    @Query(value = "select m from Medida m order by m.codigo desc")
    List<Medida> consultar();
    @Query(value = "select m from Medida m where m.estado=:estado order by m.codigo desc")
    List<Medida> consultarPorEstado(String estado);
    @Query(value = "select m from Medida m where m.empresa.id = :empresaId order by m.codigo desc")
    List<Medida> consultarPorEmpresa(long empresaId);
    @Query(value = "select m from Medida m where m.empresa.id = :empresaId and m.estado=:estado order by m.codigo asc")
    List<Medida> consultarPorEmpresaYEstado(long empresaId, String estado);
}
