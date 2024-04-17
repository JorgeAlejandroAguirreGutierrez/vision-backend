package com.proyecto.vision.repositorios.acceso;

import com.proyecto.vision.modelos.acceso.Permiso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermisoRepository extends JpaRepository<Permiso, Long>, JpaSpecificationExecutor<Permiso> {
    @Query(value = "select pr from Permiso pr order by pr.id desc")
    List<Permiso> consultar();
    @Query(value = "select pr from Permiso pr where pr.estado=:estado order by pr.id desc")
    List<Permiso> consultarPorEstado(String estado);
    @Query(value = "select pr from Permiso pr where pr.perfil.id=:perfilId order by pr.menuOpcion.id desc")
    List<Permiso> consultarPorPerfil(long perfilId);
}
