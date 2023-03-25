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
    public List<Segmento> consultar();
    @Query(value = "select s from Segmento s where s.estado=:estado order by s.codigo asc")
    public List<Segmento> consultarPorEstado(String estado);
}
