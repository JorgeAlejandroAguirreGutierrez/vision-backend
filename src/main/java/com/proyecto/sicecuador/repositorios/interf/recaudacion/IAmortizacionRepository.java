package com.proyecto.sicecuador.repositorios.interf.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IAmortizacionRepository extends JpaRepository<Amortizacion, Long>, JpaSpecificationExecutor<Amortizacion> {
}
