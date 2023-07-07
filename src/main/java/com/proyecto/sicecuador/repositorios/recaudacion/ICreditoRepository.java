package com.proyecto.vision.repositorios.recaudacion;

import com.proyecto.vision.modelos.recaudacion.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditoRepository extends JpaRepository<Credito, Long>, JpaSpecificationExecutor<Credito> {
}
