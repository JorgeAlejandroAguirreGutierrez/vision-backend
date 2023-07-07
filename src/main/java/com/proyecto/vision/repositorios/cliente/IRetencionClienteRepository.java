package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.RetencionCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IRetencionClienteRepository extends JpaRepository<RetencionCliente, Long>, JpaSpecificationExecutor<RetencionCliente> {
}
