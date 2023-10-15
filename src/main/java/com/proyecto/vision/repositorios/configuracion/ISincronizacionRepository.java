package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.Sincronizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISincronizacionRepository extends JpaRepository<Sincronizacion, Long>, JpaSpecificationExecutor<Sincronizacion> {
	@Query(value = "select s from Sincronizacion s order by s.id desc")
	List<Sincronizacion> consultar();
	@Query(value = "select s from Sincronizacion s where s.estado = :estado order by s.id desc")
    List<Sincronizacion> consultarPorEstado(String estado);
}
