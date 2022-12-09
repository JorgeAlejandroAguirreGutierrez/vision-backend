package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.CorreoEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICorreoEstablecimientoRepository extends JpaRepository<CorreoEstablecimiento, Long>, JpaSpecificationExecutor<CorreoEstablecimiento> {

}
