package com.proyecto.sicecuador.repositorios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.FacturaCaracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFacturaCaracteristicaRepository extends JpaRepository<FacturaCaracteristica, Long>, JpaSpecificationExecutor<FacturaCaracteristica> {
}
