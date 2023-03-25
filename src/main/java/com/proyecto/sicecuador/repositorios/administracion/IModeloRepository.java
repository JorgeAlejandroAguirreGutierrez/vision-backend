package com.proyecto.sicecuador.repositorios.administracion;

import com.proyecto.sicecuador.modelos.administracion.Modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IModeloRepository extends JpaRepository<Modelo, Long>, JpaSpecificationExecutor<Modelo> {
    @Query(value = "select m from Modelo m order by m.codigo")
    List<Modelo> consultar();
    @Query(value = "select m from Modelo m where m.estado=:estado order by m.codigo")
    List<Modelo> consultarPorEstado(String estado);
}
