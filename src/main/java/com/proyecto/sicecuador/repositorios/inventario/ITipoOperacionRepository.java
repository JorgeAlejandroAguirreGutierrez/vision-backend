package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.modelos.inventario.TipoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITipoOperacionRepository extends JpaRepository<TipoOperacion, Long>, JpaSpecificationExecutor<TipoOperacion> {
    @Query(value = "select to from TipoOperacion to order by to.codigo asc")
    List<TipoOperacion> consultar();
    @Query(value = "select to from TipoOperacion to where to.estado=:estado order by to.codigo asc")
    List<TipoOperacion> consultarPorEstado(String estado);
}
