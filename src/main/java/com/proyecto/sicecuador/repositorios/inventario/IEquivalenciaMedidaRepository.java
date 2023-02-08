package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEquivalenciaMedidaRepository extends JpaRepository<EquivalenciaMedida, Long>, JpaSpecificationExecutor<EquivalenciaMedida> {	
	@Query(value = "select em from EquivalenciaMedida em where em.estado=:estado")
    List<EquivalenciaMedida> consultarPorEstado(String estado);
    @Query(value = "select em from EquivalenciaMedida em where em.medidaIni.id=:id and em.estado=:estado")
    List<EquivalenciaMedida> obtenerMedidasEquivalentes(long id, String estado);
}
