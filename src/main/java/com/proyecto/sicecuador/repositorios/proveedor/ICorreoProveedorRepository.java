package com.proyecto.sicecuador.repositorios.proveedor;

import com.proyecto.sicecuador.modelos.proveedor.CorreoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICorreoProveedorRepository extends JpaRepository<CorreoProveedor, Long>, JpaSpecificationExecutor<CorreoProveedor> {
}
