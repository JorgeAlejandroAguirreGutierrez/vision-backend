package com.proyecto.sicecuador.repositorios.compra;

import com.proyecto.sicecuador.modelos.compra.TelefonoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITelefonoProveedorRepository extends JpaRepository<TelefonoProveedor, Long>, JpaSpecificationExecutor<TelefonoProveedor> {
}
