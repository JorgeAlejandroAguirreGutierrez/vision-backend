package com.proyecto.vision.repositorios.venta;

import com.proyecto.vision.modelos.venta.CierreCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICierreCajaRepository extends JpaRepository<CierreCaja, Long>, JpaSpecificationExecutor<CierreCaja> {
    @Query(value = "select cc from CierreCaja cc order by cc.codigo desc")
    List<CierreCaja> consultar();
    @Query(value = "select cc from CierreCaja cc where cc.empresa.id=:empresaId order by cc.codigo desc")
    List<CierreCaja> consultarPorEmpresa(long empresaId);
    @Query(value = "select cc from CierreCaja cc where cc.estado=:estado order by cc.codigo desc")
    List<CierreCaja> consultarPorEstado(String estado);
    @Query(value = "select cc from CierreCaja cc where cc.empresa.id=:empresaId and cc.estado=:estado order by cc.codigo desc")
    List<CierreCaja> consultarPorEmpresaYEstado(long empresaId, String estado);
}
