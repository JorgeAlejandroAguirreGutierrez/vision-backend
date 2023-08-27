package com.proyecto.vision.repositorios.compra;

import com.proyecto.vision.modelos.compra.FacturaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFacturaCompraRepository extends JpaRepository<FacturaCompra, Long>, JpaSpecificationExecutor<FacturaCompra> {
    @Query(value = "select fc from FacturaCompra fc order by fc.codigo desc")
    List<FacturaCompra> consultar();
    @Query(value = "select fc from FacturaCompra fc where fc.estado = :estado order by fc.codigo desc")
    List<FacturaCompra> consultarPorEstado(String estado);
    @Query(value = "select fc from FacturaCompra fc where fc.empresa.id = :empresaId order by fc.codigo desc")
    List<FacturaCompra> consultarPorEmpresa(long empresaId);
    @Query(value = "select fc from FacturaCompra fc where fc.empresa.id = :empresaId and fc.estado = :estado order by fc.codigo asc")
    List<FacturaCompra> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select fc from FacturaCompra fc where fc.empresa.id = :empresaId and fc.proveedor.id = :proveedorId and fc.estado = :estado order by fc.codigo desc")
    List<FacturaCompra> consultarPorEmpresaYProveedorYEstado(long empresaId, long proveedorId, String estado);
    @Query(value = "select fc from FacturaCompra fc where fc.empresa.id = :empresaId and fc.proveedor.id = :proveedorId and fc.estado != :estado order by fc.codigo desc")
    List<FacturaCompra> consultarPorEmpresaYProveedorYEstadoDiferente(long empresaId, long proveedorId, String estado);
    @Query(value = "select fc from FacturaCompra fc where fc.establecimiento = :establecimiento and fc.puntoVenta = :puntoVenta and fc.secuencial = :secuencial and fc.proveedor.id = :proveedorId")
    Optional<FacturaCompra> obtenerPorEstableciminetoYEstacionYSecuencialYProveedor(String establecimiento, String puntoVenta, String secuencial, long proveedorId);
}