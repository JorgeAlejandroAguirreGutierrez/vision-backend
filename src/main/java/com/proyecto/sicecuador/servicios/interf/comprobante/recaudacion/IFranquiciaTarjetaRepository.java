package com.proyecto.sicecuador.servicios.interf.comprobante.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.FranquiciaTarjeta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IFranquiciaTarjetaRepository extends JpaRepository<FranquiciaTarjeta, Long>, JpaSpecificationExecutor<FranquiciaTarjeta> {
	@Query(value = "select ft from FranquiciaTarjeta ft where ft.estado=:estado")
    List<FranquiciaTarjeta> consultarPorEstado(String estado);
}