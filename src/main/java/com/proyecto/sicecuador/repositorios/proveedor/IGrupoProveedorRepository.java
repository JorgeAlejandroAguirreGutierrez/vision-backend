package com.proyecto.sicecuador.repositorios.proveedor;

import com.proyecto.sicecuador.modelos.proveedor.GrupoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoProveedorRepository extends JpaRepository<GrupoProveedor, Long>, JpaSpecificationExecutor<GrupoProveedor> {

}
