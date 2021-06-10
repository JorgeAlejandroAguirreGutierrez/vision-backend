package com.proyecto.sicecuador.repositorios.entrega;

import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehiculoTransporteRepository extends JpaRepository<VehiculoTransporte, Long>, JpaSpecificationExecutor<VehiculoTransporte> {
}
