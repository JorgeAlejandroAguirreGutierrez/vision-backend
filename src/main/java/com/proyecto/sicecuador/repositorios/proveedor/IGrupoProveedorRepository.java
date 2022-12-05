package com.proyecto.sicecuador.repositorios.proveedor;

import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.modelos.proveedor.GrupoProveedor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoProveedorRepository extends JpaRepository<GrupoProveedor, Long>, JpaSpecificationExecutor<GrupoProveedor> {
	@Query(value = "select gp from GrupoProveedor gp where gp.estado=:estado")
    List<Impuesto> consultarPorEstado(String estado);

}
