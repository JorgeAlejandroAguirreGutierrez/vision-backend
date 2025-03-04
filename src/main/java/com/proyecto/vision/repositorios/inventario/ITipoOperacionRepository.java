package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.TipoGasto;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITipoOperacionRepository extends JpaRepository<TipoOperacion, Long>, JpaSpecificationExecutor<TipoOperacion> {
    @Query(value = "select to from TipoOperacion to order by to.id desc")
    List<TipoOperacion> consultar();
    @Query(value = "select to from TipoOperacion to where to.estado = :estado order by to.id desc")
    List<TipoOperacion> consultarPorEstado(String estado);
    @Query(value = "select to from TipoOperacion to where to.abreviatura = :abreviatura and to.estado = :estado")
    Optional<TipoOperacion> obtenerPorAbreviaturaYEstado(String abreviatura, String estado);

}
