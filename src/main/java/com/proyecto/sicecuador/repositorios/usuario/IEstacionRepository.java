package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstacionRepository extends JpaRepository<Estacion, Long>, JpaSpecificationExecutor<Estacion> {

}
