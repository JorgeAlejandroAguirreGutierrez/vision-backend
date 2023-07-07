package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrecioRepository extends JpaRepository<Precio, Long>, JpaSpecificationExecutor<Precio> {
}
