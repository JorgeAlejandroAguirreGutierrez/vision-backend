package com.proyecto.vision.repositorios.contabilidad;

import com.proyecto.vision.modelos.contabilidad.AfectacionContable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAfectacionContableRepository extends JpaRepository<AfectacionContable, Long>, JpaSpecificationExecutor<AfectacionContable> {
    @Query(value = "select ac from AfectacionContable ac order by ac.id desc")
    List<AfectacionContable> consultar();
    @Query(value = "select ac from AfectacionContable ac where ac.estado=:estado order by ac.id desc")
    List<AfectacionContable> consultarPorEstado(String estado);
}
