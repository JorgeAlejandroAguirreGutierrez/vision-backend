package com.proyecto.sicecuador.repositorios.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.FranquiciaTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFranquiciaTarjetaRepository extends JpaRepository<FranquiciaTarjeta, Long>, JpaSpecificationExecutor<FranquiciaTarjeta> {
}
