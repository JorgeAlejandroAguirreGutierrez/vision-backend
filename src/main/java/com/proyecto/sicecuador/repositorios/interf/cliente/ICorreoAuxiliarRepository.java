package com.proyecto.sicecuador.repositorios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.CorreoAuxiliar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ICorreoAuxiliarRepository extends JpaRepository<CorreoAuxiliar, Long>, JpaSpecificationExecutor<CorreoAuxiliar> {
}
