package com.proyecto.sicecuador.repositorios.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.MovimientoContable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovimientoContableRepository extends JpaRepository<MovimientoContable, Long>, JpaSpecificationExecutor<MovimientoContable> {
	@Query(value = "select mc from MovimientoContable mc where mc.estado=:estado")
    List<MovimientoContable> consultarPorEstado(String estado);
}
