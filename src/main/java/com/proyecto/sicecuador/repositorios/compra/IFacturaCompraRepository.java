package com.proyecto.sicecuador.repositorios.compra;

import com.proyecto.sicecuador.modelos.compra.FacturaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacturaCompraRepository extends JpaRepository<FacturaCompra, Long>, JpaSpecificationExecutor<FacturaCompra> {
	@Query(value = "select fc from FacturaCompra fc order by fc.codigo desc")
    List<FacturaCompra> consultar();
    @Query(value = "select fc from FacturaCompra fc where fc.estado = :estado order by fc.codigo desc")
    List<FacturaCompra> consultarPorEstado(String estado);
    @Query(value = "select fc from FacturaCompra fc where fc.proveedor.id = :proveedorId and fc.estado = :estado order by fc.codigo desc")
    List<FacturaCompra> consultarPorProveedor(long proveedorId, String estado);

}
