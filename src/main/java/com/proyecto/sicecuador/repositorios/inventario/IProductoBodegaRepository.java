package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.ProductoBodega;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoBodegaRepository extends JpaRepository<ProductoBodega, Long>, JpaSpecificationExecutor<ProductoBodega> {

	@Query(value = "select p from ProductoBodega p "
            + "WHERE p.cantidad=:cantidad")
    public Optional<ProductoBodega> obtenerPorNombre(String cantidad);
}
