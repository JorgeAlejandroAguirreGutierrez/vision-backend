package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.TipoGasto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoGastoRepository extends JpaRepository<TipoGasto, Long>, JpaSpecificationExecutor<TipoGasto> {
    @Query(value = "select tg from TipoGasto tg order by tg.codigo asc")
    List<TipoGasto> consultar();
    @Query(value = "select tg from TipoGasto tg where tg.estado=:estado order by tg.codigo asc")
    List<TipoGasto> consultarPorEstado(String estado);
}
