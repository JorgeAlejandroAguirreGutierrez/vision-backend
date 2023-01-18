package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.Segmento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISegmentoRepository extends JpaRepository<Segmento, Long>, JpaSpecificationExecutor<Segmento> {
	@Query(value = "select s from Segmento s where s.estado=:estado")
    public List<Segmento> consultarPorEstado(String estado);
}
