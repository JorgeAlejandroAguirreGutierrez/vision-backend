package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICalificacionClienteRepository extends JpaRepository<CalificacionCliente, Long>, JpaSpecificationExecutor<CalificacionCliente> {
}
