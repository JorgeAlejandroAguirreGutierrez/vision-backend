package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IEstablecimientoRepository extends JpaRepository<Establecimiento, Long>, JpaSpecificationExecutor<Establecimiento> {
	@Query(value = "select e from Establecimiento e where e.estado=:estado")
    List<Establecimiento> consultarPorEstado(String estado);
	
	@Query(value = "select e from Establecimiento e where e.empresa.id = :empresaId and e.estado=:estado")
    List<Establecimiento> consultarPorEmpresa(long empresaId, String estado);
}
