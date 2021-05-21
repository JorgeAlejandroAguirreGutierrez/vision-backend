package com.proyecto.sicecuador.repositorios.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Egreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEgresoRepository extends JpaRepository<Egreso, Long>, JpaSpecificationExecutor<Egreso> {
}
