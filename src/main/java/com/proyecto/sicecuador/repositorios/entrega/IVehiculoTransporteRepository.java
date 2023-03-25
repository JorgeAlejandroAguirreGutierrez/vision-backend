package com.proyecto.sicecuador.repositorios.entrega;

import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehiculoTransporteRepository extends JpaRepository<VehiculoTransporte, Long>, JpaSpecificationExecutor<VehiculoTransporte> {
    @Query(value = "select vt from VehiculoTransporte vt order by vt.codigo asc")
    List<VehiculoTransporte> consultar();
    @Query(value = "select vt from VehiculoTransporte vt where vt.estado=:estado order by vt.codigo asc")
    List<VehiculoTransporte> consultarPorEstado(String estado);
}
