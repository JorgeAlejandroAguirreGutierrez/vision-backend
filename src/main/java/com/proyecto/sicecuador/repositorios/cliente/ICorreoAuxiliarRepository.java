package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.CorreoDependiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface ICorreoAuxiliarRepository extends JpaRepository<CorreoDependiente, Long>, JpaSpecificationExecutor<CorreoDependiente> {
}
