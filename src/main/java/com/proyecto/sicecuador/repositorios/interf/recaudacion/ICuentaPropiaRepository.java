package com.proyecto.sicecuador.repositorios.interf.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ICuentaPropiaRepository extends JpaRepository<CuentaPropia, Long>, JpaSpecificationExecutor<CuentaPropia> {
}
