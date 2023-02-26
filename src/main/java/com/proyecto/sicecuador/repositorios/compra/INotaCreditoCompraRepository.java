package com.proyecto.sicecuador.repositorios.compra;

import com.proyecto.sicecuador.modelos.compra.NotaCreditoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaCreditoCompraRepository extends JpaRepository<NotaCreditoCompra, Long>, JpaSpecificationExecutor<NotaCreditoCompra> {
	@Query(value = "select ncc from FacturaCompra ncc where ncc.estado=:estado")
    List<NotaCreditoCompra> consultarPorEstado(String estado);

}
