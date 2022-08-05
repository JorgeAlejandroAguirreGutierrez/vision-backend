package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoEmision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoEmisionRepository extends JpaRepository<TipoEmision, Long>, JpaSpecificationExecutor<TipoEmision> {
}
