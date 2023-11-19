package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.EquivalenciaMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEquivalenciaMedidaRepository extends JpaRepository<EquivalenciaMedida, Long>, JpaSpecificationExecutor<EquivalenciaMedida> {
    @Query(value = "select em from EquivalenciaMedida em order by em.id desc")
    List<EquivalenciaMedida> consultar();
    @Query(value = "select em from EquivalenciaMedida em where em.estado=:estado order by em.id desc")
    List<EquivalenciaMedida> consultarPorEstado(String estado);
    @Query(value = "select * from EquivalenciaMedida em where em.medidaIni = :medidaIniId and em.medidaFin = :medidaFinId order by em.id desc limit 1", nativeQuery = true)
    Optional<EquivalenciaMedida> obtenerPorMedidaIniYMedidaFin(long medidaIniId, long medidaFinId);
}
