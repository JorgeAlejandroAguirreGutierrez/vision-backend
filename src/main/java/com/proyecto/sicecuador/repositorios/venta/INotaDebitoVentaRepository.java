package com.proyecto.sicecuador.repositorios.venta;

import com.proyecto.sicecuador.modelos.venta.NotaDebitoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaDebitoVentaRepository extends JpaRepository<NotaDebitoVenta, Long>, JpaSpecificationExecutor<NotaDebitoVenta> {
    @Query(value = "select ndv from NotaDebitoVenta ndv order by ndv.codigo asc")
    List<NotaDebitoVenta> consultar();
    @Query(value = "select ndv from NotaDebitoVenta ndv where ndv.estado = :estado order by ndv.codigo asc")
    List<NotaDebitoVenta> consultarPorEstado(String estado);
    @Query(value = "select ndv from NotaDebitoVenta ndv where ndv.empresa.id = :empresaId order by ndv.codigo asc")
    List<NotaDebitoVenta> consultarPorEmpresa(long empresaId);
    @Query(value = "select ndv from NotaDebitoVenta ndv where ndv.empresa.id = :empresaId and ndv.estado = :estado order by ndv.codigo asc")
    List<NotaDebitoVenta> consultarPorEmpresaYEstado(long empresaId, String estado);

}
