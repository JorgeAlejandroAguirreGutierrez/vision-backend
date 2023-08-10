package com.proyecto.vision.repositorios.usuario;

import com.proyecto.vision.modelos.usuario.Empresa;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IEmpresaRepository extends JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {
    @Query(value = "select e from Empresa e order by e.codigo desc")
    List<Empresa> consultar();
    @Query(value = "select e from Empresa e where e.estado=:estado order by e.codigo desc")
    List<Empresa> consultarPorEstado(String estado);
    @Query(value = "select e from Empresa e where e.identificacion=:identificacion and e.estado=:estado order by e.codigo desc")
    Optional<Empresa> obtenerPorIdentificacion(String identificacion, String estado);
}
