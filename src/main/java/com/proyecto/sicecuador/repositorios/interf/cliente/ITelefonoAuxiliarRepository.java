package com.proyecto.sicecuador.repositorios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.TelefonoAuxiliar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ITelefonoAuxiliarRepository extends JpaRepository<TelefonoAuxiliar, Long>, JpaSpecificationExecutor<TelefonoAuxiliar> {
}
