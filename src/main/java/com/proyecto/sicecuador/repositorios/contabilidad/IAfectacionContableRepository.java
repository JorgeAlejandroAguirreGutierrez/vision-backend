package com.proyecto.sicecuador.repositorios.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAfectacionContableRepository extends JpaRepository<AfectacionContable, Long>, JpaSpecificationExecutor<AfectacionContable> {
    @Query(value = "select ac from AfectacionContable ac order by ac.codigo desc")
    List<AfectacionContable> consultar();
    @Query(value = "select ac from AfectacionContable ac where ac.estado=:estado order by ac.codigo desc")
    List<AfectacionContable> consultarPorEstado(String estado);
	@Query(value = "select ac from AfectacionContable ac where ac.descripcion like '%'||:descripcion||'%' and ac.estado=:estado order by ac.codigo desc")
    List<AfectacionContable> buscar(String descripcion, String estado);
}
