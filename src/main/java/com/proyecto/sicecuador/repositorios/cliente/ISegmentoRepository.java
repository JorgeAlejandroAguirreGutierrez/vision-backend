package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.Segmento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISegmentoRepository extends JpaRepository<Segmento, Long>, JpaSpecificationExecutor<Segmento> {
    @Query(value = "select s from Segmento s order by s.codigo asc")
    List<Segmento> consultar();
    @Query(value = "select s from Segmento s where s.empresa.id = :empresaId and s.estado=:estado order by s.codigo asc")
    List<Segmento> consultarPorEstado(String estado);
    @Query(value = "select s from Segmento s where s.empresa.id=:empresaId order by s.codigo asc")
    List<Segmento> consultarPorEmpresa(long empresaId);
    @Query(value = "select s from Segmento s where s.empresa.id=:empresaId and s.estado = :estado order by s.codigo asc")
    List<Segmento> consultarPorEmpresaYEstado(long empresaId, String estado);
}
