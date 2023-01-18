package com.proyecto.sicecuador.repositorios.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Proforma;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProformaRepository extends JpaRepository<Proforma, Long>, JpaSpecificationExecutor<Proforma> {
	@Query(value = "select p from Proforma p where p.estado=:estado")
    List<Proforma> consultarPorEstado(String estado);
}
