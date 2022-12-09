package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.EstacionUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstacionUsuarioRepository extends JpaRepository<EstacionUsuario, Long>, JpaSpecificationExecutor<EstacionUsuario> {

}
