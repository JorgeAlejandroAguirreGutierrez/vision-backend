package com.proyecto.sicecuador.repositorios.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IAmortizacionRepository extends JpaRepository<Amortizacion, Long>, JpaSpecificationExecutor<Amortizacion> {
}
