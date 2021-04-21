package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.TablaEquivalenciaMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITablaEquivalenciaMedidaRepository extends JpaRepository<TablaEquivalenciaMedida, Long>, JpaSpecificationExecutor<TablaEquivalenciaMedida> {
}
