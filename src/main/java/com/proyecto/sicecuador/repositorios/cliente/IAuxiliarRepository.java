package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuxiliarRepository extends JpaRepository<Dependiente, Long>, JpaSpecificationExecutor<Dependiente> {
}
