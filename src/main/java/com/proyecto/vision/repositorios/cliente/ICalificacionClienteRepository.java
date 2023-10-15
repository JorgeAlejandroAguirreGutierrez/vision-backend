package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.CalificacionCliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICalificacionClienteRepository extends JpaRepository<CalificacionCliente, Long>, JpaSpecificationExecutor<CalificacionCliente> {
    @Query(value = "select cc from CalificacionCliente cc order by cc.id desc")
    List<CalificacionCliente> consultar();
    @Query(value = "select cc from CalificacionCliente cc where cc.empresa.id = :empresaId order by cc.id desc")
    List<CalificacionCliente> consultarPorEmpresa(long empresaId);
    @Query(value = "select cc from CalificacionCliente cc where cc.estado = :estado order by cc.id desc")
    List<CalificacionCliente> consultarPorEstado(String estado);
    @Query(value = "select cc from CalificacionCliente cc where cc.empresa.id = :empresaId and cc.estado = :estado order by cc.id desc")
    List<CalificacionCliente> consultarPorEmpresaYEstado(long empresaId, String estado);
}
