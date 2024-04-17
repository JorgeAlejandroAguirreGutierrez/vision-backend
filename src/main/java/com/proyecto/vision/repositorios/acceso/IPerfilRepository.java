package com.proyecto.vision.repositorios.acceso;

import com.proyecto.vision.modelos.acceso.Perfil;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPerfilRepository extends JpaRepository<Perfil, Long>, JpaSpecificationExecutor<Perfil> {
    @Query(value = "select p from Perfil p order by p.id desc")
    List<Perfil> consultar();
    @Query(value = "select p from Perfil p where p.estado = :estado order by p.id desc")
    List<Perfil> consultarPorEstado(String estado);
    @Query(value = "select p from Perfil p where p.descripcion = :descripcion and p.estado = :estado")
    Optional<Perfil> obtenerPorDescripcionYEstado(String descripcion, String estado);
}
