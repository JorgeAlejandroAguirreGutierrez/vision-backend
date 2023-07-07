package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.Contribuyente;
import com.proyecto.vision.repositorios.IGenericoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IContribuyenteRepository extends IGenericoRepository<Contribuyente> {
	
	@Query(value = "select co from Contribuyente co where co.identificacion=:identificacion")
    Optional<Contribuyente> obtenerPorIdentificacion(String identificacion);
	
	@Query(value = "select co from Contribuyente co where co.razonSocial=:razonSocial and co.estado=:estado")
    Optional<Contribuyente> obtenerPorRazonSocial(String razonSocial, String estado);
	
	@Query(value = "select co from Contribuyente co where co.estado=:estado")
    List<Contribuyente> consultarPorEstado(String estado);
}
