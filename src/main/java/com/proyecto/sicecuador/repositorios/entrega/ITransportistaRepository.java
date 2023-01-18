package com.proyecto.sicecuador.repositorios.entrega;

import com.proyecto.sicecuador.modelos.entrega.Transportista;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransportistaRepository extends JpaRepository<Transportista, Long>, JpaSpecificationExecutor<Transportista> {
	@Query(value = "select t from Transportista t where t.estado= :estado")
    List<Transportista> consultarPorEstado(String estado);
}
