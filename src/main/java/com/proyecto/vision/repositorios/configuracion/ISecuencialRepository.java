package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.Secuencial;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ISecuencialRepository extends JpaRepository<Secuencial, Long>, JpaSpecificationExecutor<Secuencial> {
    @Query(value = "select s from Secuencial s order by s.estacion.establecimiento.empresa, s.estacion.establecimiento, s.estacion")
    List<Secuencial> consultar();
    @Query(value = "select s from Secuencial s where s.estacion.establecimiento.empresa.id = :empresaId order by s.codigo desc")
    List<Secuencial> consultarPorEmpresa(long empresaId);
    @Query(value = "select s from Secuencial s where s.estado=:estado order by s.codigo desc")
    List<Secuencial> consultarPorEstado(String estado);
    @Query(value = "select s from Secuencial s where s.tipoComprobante.id = :tipoComprobanteId and s.estacion.id = :estacionId and s.estacion.establecimiento.empresa.id = :empresaId and s.estado = :estado order by s.codigo desc")
    Optional<Secuencial> obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(long tipoComprobanteId, long estacionId, long empresaId, String estado);
}
