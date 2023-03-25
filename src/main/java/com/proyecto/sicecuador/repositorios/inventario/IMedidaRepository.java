package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.Medida;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedidaRepository extends JpaRepository<Medida, Long>, JpaSpecificationExecutor<Medida> {
    @Query(value = "select m from Medida m order by m.codigo asc")
    List<Medida> consultar();
    @Query(value = "select m from Medida m where m.estado=:estado order by m.codigo asc")
    List<Medida> consultarPorEstado(String estado);
}
