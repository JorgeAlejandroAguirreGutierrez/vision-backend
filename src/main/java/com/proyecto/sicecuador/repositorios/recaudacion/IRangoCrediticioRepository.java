package com.proyecto.sicecuador.repositorios.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IRangoCrediticioRepository extends JpaRepository<RangoCrediticio, Long>, JpaSpecificationExecutor<RangoCrediticio> {
    @Query(value = "SELECT * FROM rango_crediticio WHERE rango_crediticio.rango_inicial < :saldo AND rango_crediticio.rango_final > :saldo", nativeQuery = true)
    Optional<RangoCrediticio> findSaldoRangoCrediticio(@Param("saldo") double saldo);
}
