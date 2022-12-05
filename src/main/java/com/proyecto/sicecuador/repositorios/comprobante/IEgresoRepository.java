package com.proyecto.sicecuador.repositorios.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Egreso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEgresoRepository extends JpaRepository<Egreso, Long>, JpaSpecificationExecutor<Egreso> {
	@Query(value = "select e from Egreso e where e.estado=:estado")
    List<Egreso> consultarPorEstado(String estado);
}
