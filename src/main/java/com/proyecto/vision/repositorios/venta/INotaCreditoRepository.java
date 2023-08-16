package com.proyecto.vision.repositorios.venta;

import com.proyecto.vision.modelos.venta.NotaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaCreditoRepository extends JpaRepository<NotaCredito, Long>, JpaSpecificationExecutor<NotaCredito> {
    @Query(value = "select nc from NotaCredito nc order by nc.codigo desc")
    List<NotaCredito> consultar();
    @Query(value = "select nc from NotaCredito nc where nc.estado = :estado order by nc.codigo desc")
    List<NotaCredito> consultarPorEstado(String estado);
    @Query(value = "select nc from NotaCredito nc where nc.empresa.id = :empresaId order by nc.codigo desc")
    List<NotaCredito> consultarPorEmpresa(long empresaId);
    @Query(value = "select nc from NotaCredito nc where nc.empresa.id = :empresaId and nc.estado = :estado order by nc.codigo asc")
    List<NotaCredito> consultarPorEmpresaYEstado(long empresaId, String estado);
}
