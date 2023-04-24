package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Secuencial;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ISecuencialRepository extends JpaRepository<Secuencial, Long>, JpaSpecificationExecutor<Secuencial> {
    @Query(value = "select s from Secuencial s order by s.codigo asc")
    List<Secuencial> consultar();
    @Query(value = "select s from Secuencial s where s.estado=:estado order by s.codigo asc")
    List<Secuencial> consultarPorEstado(String estado);
}
