package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICalificacionClienteRepository extends JpaRepository<CalificacionCliente, Long>, JpaSpecificationExecutor<CalificacionCliente> {
    @Query(value = "select cc from CalificacionCliente cc order by cc.codigo asc")
    List<CalificacionCliente> consultar();
    @Query(value = "select cc from CalificacionCliente cc where cc.estado=:estado order by cc.codigo asc")
    List<CalificacionCliente> consultarPorEstado(String estado);
	@Query(value = "select cc from CalificacionCliente cc where cc.codigo like '%'||:codigo||'%' and cc.descripcion = '%'||:descripcion||'%' and cc.abreviatura = '%'||:abreviatura||'%' order by cc.codigo desc")
    List<CalificacionCliente> buscar(String codigo, String descripcion, String abreviatura);
}
