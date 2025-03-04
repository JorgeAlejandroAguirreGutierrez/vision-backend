package com.proyecto.vision.repositorios.contabilidad;

import com.proyecto.vision.modelos.contabilidad.MovimientoContable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovimientoContableRepository extends JpaRepository<MovimientoContable, Long>, JpaSpecificationExecutor<MovimientoContable> {
    @Query(value = "select mc from MovimientoContable mc order by mc.id desc")
    List<MovimientoContable> consultar();
    @Query(value = "select mc from MovimientoContable mc where mc.estado=:estado order by mc.id desc")
    List<MovimientoContable> consultarPorEstado(String estado);
    @Query(value = "select mc from MovimientoContable mc where mc.empresa.id = :empresaId order by mc.id asc")
    List<MovimientoContable> consultarPorEmpresa(long empresaId);
    @Query(value = "select mc from MovimientoContable mc where mc.empresa.id = :empresaId and mc.estado = :estado order by mc.id asc")
    List<MovimientoContable> consultarPorEmpresaYEstado(long empresaId, String estado);
}
