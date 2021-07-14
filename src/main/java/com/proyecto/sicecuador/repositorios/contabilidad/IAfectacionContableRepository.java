package com.proyecto.sicecuador.repositorios.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IAfectacionContableRepository extends JpaRepository<AfectacionContable, Long>, JpaSpecificationExecutor<AfectacionContable> {
}
