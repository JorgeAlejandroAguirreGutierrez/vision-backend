package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.PlazoCredito;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlazoCreditoRepository extends JpaRepository<PlazoCredito, Long>, JpaSpecificationExecutor<PlazoCredito> {
    @Query(value = "select pc from PlazoCredito pc order by pc.id desc")
    List<PlazoCredito> consultar();
    @Query(value = "select pc from PlazoCredito pc where pc.empresa.id=:empresaId order by pc.id desc")
    List<PlazoCredito> consultarPorEmpresa(long empresaId);
    @Query(value = "select pc from PlazoCredito pc where pc.estado=:estado order by pc.id desc")
    List<PlazoCredito> consultarPorEstado(String estado);
    @Query(value = "select pc from PlazoCredito pc where pc.empresa.id = :empresaId and pc.estado = :estado order by pc.id desc")
    List<PlazoCredito> consultarPorEmpresaYEstado(long empresaId, String estado);
}
