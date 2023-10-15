package com.proyecto.vision.repositorios.usuario;

import com.proyecto.vision.modelos.usuario.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IPaqueteRepository extends JpaRepository<Paquete, Long>, JpaSpecificationExecutor<Paquete> {
    @Query(value = "select p from Paquete p order by p.id desc")
    List<Paquete> consultar();
    @Query(value = "select p from Paquete p where p.estado = :estado order by p.id desc")
    List<Paquete> consultarPorEstado(String estado);
}
