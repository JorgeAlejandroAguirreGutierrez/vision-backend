package com.proyecto.sicecuador.repositorios.interf.usuario;

import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IPuntoVentaRepository extends JpaRepository<PuntoVenta, Long>, JpaSpecificationExecutor<PuntoVenta> {
}
