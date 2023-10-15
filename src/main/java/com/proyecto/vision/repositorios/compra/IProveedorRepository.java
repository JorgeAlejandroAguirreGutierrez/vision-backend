package com.proyecto.vision.repositorios.compra;

import com.proyecto.vision.modelos.compra.Proveedor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long>, JpaSpecificationExecutor<Proveedor> {
    @Query(value = "select p from Proveedor p order by p.id desc")
    List<Proveedor> consultar();
    @Query(value = "select p from Proveedor p where p.estado=:estado order by p.id desc")
    List<Proveedor> consultarPorEstado(String estado);
    @Query(value = "select p from Proveedor p where p.empresa.id=:empresaId order by p.id desc")
    List<Proveedor> consultarPorEmpresa(long empresaId);
    @Query(value = "select p from Proveedor p where p.empresa.id=:empresaId and p.estado = :estado order by p.id desc")
    List<Proveedor> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select p from Proveedor p where p.identificacion = :identificacion and p.empresa.id = :empresaId order by p.id desc")
    Optional<Proveedor> obtenerPorIdentificacionYEmpresa(String identificacion, long empresaId);
    @Query(value = "select p from Proveedor p where p.identificacion = :identificacion and p.empresa.id = :empresaId and p.estado = :estado order by p.id desc")
    Optional<Proveedor> obtenerPorIdentificacionYEmpresaYEstado(String identificacion, long empresaId, String estado);
    @Query(value = "select p from Proveedor p where p.razonSocial like '%' || :razonSocial || '%' and p.estado = :estado order by p.id desc")
    List<Proveedor> consultarPorRazonSocial(String razonSocial, String estado);
}
