package com.proyecto.vision.repositorios.usuario;

import com.proyecto.vision.modelos.usuario.Establecimiento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IEstablecimientoRepository extends JpaRepository<Establecimiento, Long>, JpaSpecificationExecutor<Establecimiento> {
    @Query(value = "select e from Establecimiento e order by e.codigo desc")
    List<Establecimiento> consultar();
    @Query(value = "select e from Establecimiento e where e.estado=:estado order by e.codigo desc")
    List<Establecimiento> consultarPorEstado(String estado);
	@Query(value = "select e from Establecimiento e where e.empresa.id = :empresaId order by e.codigo desc")
    List<Establecimiento> consultarPorEmpresa(long empresaId);
    @Query(value = "select e from Establecimiento e where e.empresa.id = :empresaId and e.estado=:estado order by e.codigo asc")
    List<Establecimiento> consultarPorEmpresaYEstado(long empresaId, String estado);
}
