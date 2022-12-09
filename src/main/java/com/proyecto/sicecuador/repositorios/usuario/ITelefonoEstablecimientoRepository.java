package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.TelefonoEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITelefonoEstablecimientoRepository extends JpaRepository<TelefonoEstablecimiento, Long>, JpaSpecificationExecutor<TelefonoEstablecimiento> {

}
