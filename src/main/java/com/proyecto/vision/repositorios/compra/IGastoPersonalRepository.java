package com.proyecto.vision.repositorios.compra;

import com.proyecto.vision.modelos.compra.GastoPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGastoPersonalRepository extends JpaRepository<GastoPersonal, Long>, JpaSpecificationExecutor<GastoPersonal> {
    @Query(value = "select gp from GastoPersonal gp order by gp.id desc")
    List<GastoPersonal> consultar();
    @Query(value = "select gp from GastoPersonal gp where gp.estado = :estado order by gp.id desc")
    List<GastoPersonal> consultarPorEstado(String estado);
    @Query(value = "select gp from GastoPersonal gp where gp.empresa.id = :empresaId order by gp.id desc")
    List<GastoPersonal> consultarPorEmpresa(long empresaId);
    @Query(value = "select gp from GastoPersonal gp where gp.empresa.id = :empresaId and gp.estado = :estado order by gp.id desc")
    List<GastoPersonal> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select gp from GastoPersonal gp where gp.proveedor.id = :proveedorId and gp.empresa.id = :empresaId and gp.estado = :estado order by gp.id desc")
    List<GastoPersonal> consultarPorProveedorYEmpresaYEstado(long proveedorId, long empresaId, String estado);
    @Query(value = "select gp from GastoPersonal gp where gp.establecimiento = :establecimiento and gp.puntoVenta = :puntoVenta and gp.secuencial = :secuencial and gp.proveedor.id = :proveedorId")
    Optional<GastoPersonal> obtenerPorEstableciminetoYEstacionYSecuencialYProveedor(String establecimiento, String puntoVenta, String secuencial, long proveedorId);
    @Query(value = "select gp from GastoPersonal gp where gp.numeroComprobante = :numeroComprobante and gp.empresa.id = :empresaId")
    Optional<GastoPersonal> obtenerPorNumeroComprobanteYEmpresa(String numeroComprobante, long empresaId);
}