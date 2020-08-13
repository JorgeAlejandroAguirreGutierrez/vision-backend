package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.modelos.inventario.SubGrupoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubGrupoProductoRepository extends JpaRepository<SubGrupoProducto, Long>, JpaSpecificationExecutor<SubGrupoProducto> {
}
