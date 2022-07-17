package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.CelularDependiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICelularAuxiliarRepository extends JpaRepository<CelularDependiente, Long>, JpaSpecificationExecutor<CelularDependiente> {
}
