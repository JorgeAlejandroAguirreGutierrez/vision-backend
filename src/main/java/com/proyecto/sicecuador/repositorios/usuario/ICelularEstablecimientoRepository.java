package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.CelularEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICelularEstablecimientoRepository extends JpaRepository<CelularEstablecimiento, Long>, JpaSpecificationExecutor<CelularEstablecimiento> {

}
