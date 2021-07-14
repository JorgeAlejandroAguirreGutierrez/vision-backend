package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoProveedorRepository extends JpaRepository<ProductoProveedor, Long>, JpaSpecificationExecutor<ProductoProveedor> {
}
