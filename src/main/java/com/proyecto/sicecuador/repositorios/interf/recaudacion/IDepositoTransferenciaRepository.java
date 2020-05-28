package com.proyecto.sicecuador.repositorios.interf.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.DepositoTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepositoTransferenciaRepository extends JpaRepository<DepositoTransferencia, Long>, JpaSpecificationExecutor<DepositoTransferencia> {
}
