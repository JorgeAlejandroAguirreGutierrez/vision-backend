package com.proyecto.sicecuador.repositorios.compra;

import com.proyecto.sicecuador.modelos.compra.CelularProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICelularProveedorRepository extends JpaRepository<CelularProveedor, Long>, JpaSpecificationExecutor<CelularProveedor> {
}
