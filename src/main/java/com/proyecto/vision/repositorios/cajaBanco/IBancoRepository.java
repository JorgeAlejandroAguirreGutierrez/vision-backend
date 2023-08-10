package com.proyecto.vision.repositorios.cajaBanco;

import com.proyecto.vision.modelos.cajaBanco.Banco;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBancoRepository extends JpaRepository<Banco, Long>, JpaSpecificationExecutor<Banco> {
    @Query(value = "select b from Banco b order by b.codigo desc")
    List<Banco> consultar();
    @Query(value = "select b from Banco b where b.estado=:estado order by b.codigo desc")
    List<Banco> consultarPorEstado(String estado);
}
