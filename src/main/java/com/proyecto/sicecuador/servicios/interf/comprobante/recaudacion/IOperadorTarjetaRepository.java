package com.proyecto.sicecuador.servicios.interf.comprobante.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.OperadorTarjeta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOperadorTarjetaRepository extends JpaRepository<OperadorTarjeta, Long>, JpaSpecificationExecutor<OperadorTarjeta> {
	@Query(value = "select ot from OperadorTarjeta ot where ot.estado=:estado")
    List<OperadorTarjeta> consultarPorEstado(String estado);
	
	@Query(value = "select ot from OperadorTarjeta ot where ot.tipo=:tipo and ot.estado=:estado")
    List<OperadorTarjeta> consultarPorTipo(String tipo, String estado);
}
