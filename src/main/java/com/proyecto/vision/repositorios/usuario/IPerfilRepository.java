package com.proyecto.vision.repositorios.usuario;

import com.proyecto.vision.modelos.usuario.Perfil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IPerfilRepository extends JpaRepository<Perfil, Long>, JpaSpecificationExecutor<Perfil> {
    @Query(value = "select p from Perfil p order by p.codigo desc")
    List<Perfil> consultar();
    @Query(value = "select p from Perfil p where p.estado=:estado order by p.codigo desc")
    List<Perfil> consultarPorEstado(String estado);
}
