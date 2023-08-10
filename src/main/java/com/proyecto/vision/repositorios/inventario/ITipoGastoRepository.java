package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.TipoGasto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoGastoRepository extends JpaRepository<TipoGasto, Long>, JpaSpecificationExecutor<TipoGasto> {
    @Query(value = "select tg from TipoGasto tg order by tg.codigo desc")
    List<TipoGasto> consultar();
}
