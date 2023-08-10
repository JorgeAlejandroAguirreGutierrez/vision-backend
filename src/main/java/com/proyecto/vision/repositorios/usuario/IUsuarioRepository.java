package com.proyecto.vision.repositorios.usuario;

import com.proyecto.vision.modelos.usuario.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    @Query(value = "select u from Usuario u order by u.codigo desc")
    List<Usuario> consultar();
    @Query(value = "select u from Usuario u where u.estado=:estado order by u.codigo desc")
    List<Usuario> consultarPorEstado(String estado);
    @Query(value = "select u from Usuario u where u.apodo = :apodo and u.estado = :estado order by u.codigo desc")
    Optional<Usuario> obtenerPorApodoYEstado(String apodo, String estado);
    @Query(value = "select u from Usuario u where u.apodo = :apodo and u.contrasena = :contrasena and u.estado = :estado order by u.codigo desc")
    Optional<Usuario> obtenerPorApodoYContrasenaYEstado(String apodo, String contrasena, String estado);
}
