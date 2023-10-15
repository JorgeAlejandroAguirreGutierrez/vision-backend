package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.ClienteBase;
import com.proyecto.vision.repositorios.IGenericoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IClienteBaseRepository extends IGenericoRepository<ClienteBase> {
	@Query(value = "select cb from ClienteBase cb where cb.identificacion = :identificacion and cb.estado = :estado")
    Optional<ClienteBase> obtenerPorIdentificacion(String identificacion, String estado);
	@Query(value = "select cb from ClienteBase cb where cb.apellidos = :apellidos and cb.estado = :estado")
    Optional<ClienteBase> obtenerPorApellido(String apellidos, String estado);
	@Query(value = "select cb from ClienteBase cb where cb.estado = :estado")
    List<ClienteBase> consultarPorEstado(String estado);
}
