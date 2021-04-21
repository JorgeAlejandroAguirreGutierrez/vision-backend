package com.proyecto.sicecuador.repositorios.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.RetencionVentaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IRetencionVentaDetalleRepository extends JpaRepository<RetencionVentaDetalle, Long>, JpaSpecificationExecutor<RetencionVentaDetalle> {
}
