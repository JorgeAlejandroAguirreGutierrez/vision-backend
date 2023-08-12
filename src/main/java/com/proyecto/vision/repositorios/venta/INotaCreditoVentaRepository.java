package com.proyecto.vision.repositorios.venta;

import com.proyecto.vision.modelos.venta.NotaCreditoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaCreditoVentaRepository extends JpaRepository<NotaCreditoVenta, Long>, JpaSpecificationExecutor<NotaCreditoVenta> {
    @Query(value = "select ncv from NotaCreditoVenta ncv order by ncv.codigo desc")
    List<NotaCreditoVenta> consultar();
    @Query(value = "select ncv from NotaCreditoVenta ncv where ncv.estado = :estado order by ncv.codigo desc")
    List<NotaCreditoVenta> consultarPorEstado(String estado);
    @Query(value = "select ncv from NotaCreditoVenta ncv where ncv.empresa.id = :empresaId order by ncv.codigo desc")
    List<NotaCreditoVenta> consultarPorEmpresa(long empresaId);
    @Query(value = "select ncv from NotaCreditoVenta ncv where ncv.empresa.id = :empresaId and ncv.estado = :estado order by ncv.codigo asc")
    List<NotaCreditoVenta> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select ncv from NotaCreditoVenta ncv where ncv.factura.id = :facturaId and ncv.estadoSri = :estadoSri and ncv.estadoInterno = :estadoInterno and ncv.estado = :estado order by ncv.codigo desc")
    List<NotaCreditoVenta> consultarPorFacturaYEstadoSriYEstadoInternoYEstado(long facturaId, String estadoSri, String  estadoInterno, String estado);

}
