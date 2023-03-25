package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.cliente.TipoPago;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoPagoRepository extends JpaRepository<TipoPago, Long>, JpaSpecificationExecutor<TipoPago> {
    @Query(value = "select tp from TipoPago tp order by tp.codigo desc")
    List<TipoPago> consultar();
    @Query(value = "select tp from TipoPago tp where tp.estado=:estado order by tp.codigo desc")
    List<TipoPago> consultarPorEstado(String estado);
}
