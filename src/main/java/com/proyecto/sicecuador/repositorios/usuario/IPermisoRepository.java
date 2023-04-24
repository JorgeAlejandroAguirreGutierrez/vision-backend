package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.Permiso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IPermisoRepository extends JpaRepository<Permiso, Long>, JpaSpecificationExecutor<Permiso> {
    @Query(value = "select pr from Permiso pr order by pr.codigo asc")
    List<Permiso> consultar();
    @Query(value = "select pr from Permiso pr where pr.estado=:estado order by pr.codigo asc")
    List<Permiso> consultarPorEstado(String estado);
}
