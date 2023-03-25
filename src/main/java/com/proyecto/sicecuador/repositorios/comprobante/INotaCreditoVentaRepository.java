package com.proyecto.sicecuador.repositorios.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.NotaCreditoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaCreditoVentaRepository extends JpaRepository<NotaCreditoVenta, Long>, JpaSpecificationExecutor<NotaCreditoVenta> {
    @Query(value = "select ncv from NotaCreditoVenta ncv order by ncv.codigo asc")
    List<NotaCreditoVenta> consultar();
    @Query(value = "select ncv from NotaCreditoVenta ncv where ncv.estado = :estado order by ncv.codigo asc")
    List<NotaCreditoVenta> consultarPorEstado(String estado);

}
