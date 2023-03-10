package com.proyecto.sicecuador.repositorios.compra;

import com.proyecto.sicecuador.modelos.compra.NotaDebitoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaDebitoCompraRepository extends JpaRepository<NotaDebitoCompra, Long>, JpaSpecificationExecutor<NotaDebitoCompra> {
	@Query(value = "select ndc from NotaDebitoCompra ndc where ndc.estado = :estado")
    List<NotaDebitoCompra> consultarPorEstado(String estado);

}
