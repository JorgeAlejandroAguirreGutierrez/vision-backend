package com.proyecto.vision.repositorios.usuario;

import com.proyecto.vision.modelos.usuario.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ISuscripcionRepository extends JpaRepository<Suscripcion, Long>, JpaSpecificationExecutor<Suscripcion> {
    @Query(value = "select s from Suscripcion s order by s.codigo desc")
    List<Suscripcion> consultar();
    @Query(value = "select s from Suscripcion s where s.estado = :estado order by s.codigo desc")
    List<Suscripcion> consultarPorEstado(String estado);
    @Query(value = "select s from Suscripcion s where s.empresa.id = :empresaId order by s.codigo desc")
    List<Suscripcion> consultarPorEmpresa(long empresaId);
    @Query(value = "select s from Suscripcion s where s.empresa.id = :empresaId and s.estado = :estado order by s.codigo desc")
    List<Suscripcion> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select s from Suscripcion s where s.empresa.id = :empresaId and s.estado = :estado")
    Optional<Suscripcion> obtenerPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select * from suscripcion s where s.empresa_id = :empresaId order by s.fecha_final desc limit 1", nativeQuery = true)
    Optional<Suscripcion> obtenerUltimoPorEmpresa(long empresaId);
}
