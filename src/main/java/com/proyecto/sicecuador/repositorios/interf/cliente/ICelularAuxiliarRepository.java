package com.proyecto.sicecuador.repositorios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.CelularAuxiliar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICelularAuxiliarRepository extends JpaRepository<CelularAuxiliar, Long>, JpaSpecificationExecutor<CelularAuxiliar> {
}
