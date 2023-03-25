package com.proyecto.sicecuador.repositorios.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ICuentaPropiaRepository extends JpaRepository<CuentaPropia, Long>, JpaSpecificationExecutor<CuentaPropia> {
    @Query(value = "select cp from CuentaPropia cp order by cp.codigo asc")
    List<CuentaPropia> consultar();
    @Query(value = "select cp from CuentaPropia cp where cp.estado=:estado order by cp.codigo asc")
    List<CuentaPropia> consultarPorEstado(String estado);
}
