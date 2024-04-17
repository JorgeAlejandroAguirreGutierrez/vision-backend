package com.proyecto.vision.repositorios.acceso;

import com.proyecto.vision.modelos.acceso.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ISesionRepository extends JpaRepository<Sesion, Long>, JpaSpecificationExecutor<Sesion> {
}
