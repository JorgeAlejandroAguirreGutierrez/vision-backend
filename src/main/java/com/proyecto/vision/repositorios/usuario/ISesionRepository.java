package com.proyecto.vision.repositorios.usuario;

import com.proyecto.vision.modelos.usuario.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ISesionRepository extends JpaRepository<Sesion, Long>, JpaSpecificationExecutor<Sesion> {
}
