package com.proyecto.sicecuador.repositorios.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVenta;
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

}
