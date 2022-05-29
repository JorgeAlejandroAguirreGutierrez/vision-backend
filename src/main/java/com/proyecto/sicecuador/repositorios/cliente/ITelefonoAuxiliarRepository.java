package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.TelefonoDependiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITelefonoAuxiliarRepository extends JpaRepository<TelefonoDependiente, Long>, JpaSpecificationExecutor<TelefonoDependiente> {
}
