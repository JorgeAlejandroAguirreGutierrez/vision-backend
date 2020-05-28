package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.BodegaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBodegaProductoRepository extends JpaRepository<BodegaProducto, Long>, JpaSpecificationExecutor<BodegaProducto> {
}
