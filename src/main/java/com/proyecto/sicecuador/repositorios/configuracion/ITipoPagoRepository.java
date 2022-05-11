package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.cliente.TipoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoPagoRepository extends JpaRepository<TipoPago, Long>, JpaSpecificationExecutor<TipoPago> {
}
