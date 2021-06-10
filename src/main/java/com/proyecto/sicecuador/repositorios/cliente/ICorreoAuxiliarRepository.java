package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.CorreoAuxiliar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface ICorreoAuxiliarRepository extends JpaRepository<CorreoAuxiliar, Long>, JpaSpecificationExecutor<CorreoAuxiliar> {
}
