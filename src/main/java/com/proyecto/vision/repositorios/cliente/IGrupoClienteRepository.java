package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.GrupoCliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoClienteRepository extends JpaRepository<GrupoCliente, Long>, JpaSpecificationExecutor<GrupoCliente> {
	@Query(value = "select gc from GrupoCliente gc order by gc.id desc")
	List<GrupoCliente> consultar();
	@Query(value = "select gc from GrupoCliente gc where gc.empresa.id=:empresaId order by gc.id desc")
	List<GrupoCliente> consultarPorEmpresa(long empresaId);
	@Query(value = "select gc from GrupoCliente gc where gc.estado=:estado order by gc.id desc")
    List<GrupoCliente> consultarPorEstado(String estado);
	@Query(value = "select gc from GrupoCliente gc where gc.empresa.id=:empresaId and estado = :estado order by gc.id desc")
	List<GrupoCliente> consultarPorEmpresaYEstado(long empresaId, String estado);
}
