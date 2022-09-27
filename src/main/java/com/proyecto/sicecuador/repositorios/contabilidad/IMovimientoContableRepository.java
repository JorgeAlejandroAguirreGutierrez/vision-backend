package com.proyecto.sicecuador.repositorios.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.MovimientoContable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovimientoContableRepository extends JpaRepository<MovimientoContable, Long>, JpaSpecificationExecutor<MovimientoContable> {
}
