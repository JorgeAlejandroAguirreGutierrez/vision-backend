package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IImpuestoRepository extends JpaRepository<Impuesto, Long>, JpaSpecificationExecutor<Impuesto> {
    @Query(value = "SELECT * FROM impuesto u WHERE u.porcentaje = :porcentaje", nativeQuery = true)
    Optional<Impuesto> findByPorcentaje(@Param("porcentaje") double porcentaje);
}
