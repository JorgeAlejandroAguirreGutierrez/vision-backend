package com.proyecto.sicecuador.repositorios.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.TarjetaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long>, JpaSpecificationExecutor<TarjetaCredito> {
}
