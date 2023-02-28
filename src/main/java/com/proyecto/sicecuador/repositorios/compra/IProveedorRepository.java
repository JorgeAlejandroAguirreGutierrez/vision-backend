package com.proyecto.sicecuador.repositorios.compra;

import com.proyecto.sicecuador.modelos.compra.Proveedor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long>, JpaSpecificationExecutor<Proveedor> {
	@Query(value = "select p from Proveedor p where p.estado=:estado")
    List<Proveedor> consultarPorEstado(String estado);

    @Query(value = "select p from Proveedor p where p.razonSocial like '%'||:razonSocial||'%' and p.estado=:estado")
    List<Proveedor> consultarPorRazonSocial(String razonSocial, String estado);
}