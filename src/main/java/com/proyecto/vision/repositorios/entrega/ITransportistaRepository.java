package com.proyecto.vision.repositorios.entrega;

import com.proyecto.vision.modelos.entrega.Transportista;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransportistaRepository extends JpaRepository<Transportista, Long>, JpaSpecificationExecutor<Transportista> {
    @Query(value = "select t from Transportista t order by t.codigo desc")
    List<Transportista> consultar();
    @Query(value = "select t from Transportista t where t.estado= :estado order by t.codigo desc")
    List<Transportista> consultarPorEstado(String estado);
    @Query(value = "select t from Transportista t where t.empresa.id= :empresaId order by t.codigo desc")
    List<Transportista> consultarPorEmpresa(long empresaId);
    @Query(value = "select t from Transportista t where t.empresa.id = :empresaId and t.estado= :estado order by t.codigo asc")
    List<Transportista> consultarPorEmpresaYEstado(long empresaId, String estado);
}
