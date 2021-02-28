package com.proyecto.sicecuador.repositorios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaDetalleRepository extends JpaRepository<DetalleFactura, Long>, JpaSpecificationExecutor<DetalleFactura> {
}
