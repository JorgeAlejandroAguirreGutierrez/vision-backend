package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    @Query(value = "SELECT * FROM usuario u WHERE u.identificacion = :identificacion and u.contrasena=:contrasena", nativeQuery = true)
    Usuario findByIdentificacionContrasena(@Param("identificacion") String identificacion,
                                            @Param("contrasena") String contrasena);
}
