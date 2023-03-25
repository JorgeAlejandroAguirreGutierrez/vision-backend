package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoClienteRepository extends JpaRepository<GrupoCliente, Long>, JpaSpecificationExecutor<GrupoCliente> {
	@Query(value = "select gc from GrupoCliente gc order by gc.codigo asc")
	List<GrupoCliente> consultar();
	@Query(value = "select gc from GrupoCliente gc where gc.estado=:estado order by gc.codigo asc")
    List<GrupoCliente> consultarPorEstado(String estado);

	@Query(value = "select gc from GrupoCliente gc where gc.codigo like '%'||:codigo||'%' and gc.descripcion like '%'||:descripcion||'%' and gc.abreviatura like '%'||:abreviatura||'%' order by gc.codigo asc")
	List<GrupoCliente> buscar(String codigo, String descripcion, String abreviatura);
}
