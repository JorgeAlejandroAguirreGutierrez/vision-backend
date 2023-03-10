package com.proyecto.sicecuador.servicios.interf.comprobante.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditoRepository extends JpaRepository<Credito, Long>, JpaSpecificationExecutor<Credito> {
}
