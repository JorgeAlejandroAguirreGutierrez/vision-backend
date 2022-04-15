package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstablecimientoRepository extends JpaRepository<Establecimiento, Long>, JpaSpecificationExecutor<Establecimiento> {
}
