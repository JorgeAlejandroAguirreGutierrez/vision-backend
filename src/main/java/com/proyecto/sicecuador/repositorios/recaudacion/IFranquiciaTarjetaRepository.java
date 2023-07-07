package com.proyecto.vision.repositorios.recaudacion;

import com.proyecto.vision.modelos.recaudacion.FranquiciaTarjeta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IFranquiciaTarjetaRepository extends JpaRepository<FranquiciaTarjeta, Long>, JpaSpecificationExecutor<FranquiciaTarjeta> {
    @Query(value = "select ft from FranquiciaTarjeta ft order by ft.codigo asc")
    List<FranquiciaTarjeta> consultar();
    @Query(value = "select ft from FranquiciaTarjeta ft where ft.estado=:estado order by ft.codigo asc")
    List<FranquiciaTarjeta> consultarPorEstado(String estado);
}
