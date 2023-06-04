package com.proyecto.sicecuador.repositorios.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICuentaContableRepository extends JpaRepository<CuentaContable, Long>, JpaSpecificationExecutor<CuentaContable> {
    @Query(value = "select cc from CuentaContable cc order by cc.codigo asc")
    List<CuentaContable> consultar();
    @Query(value = "select cc from CuentaContable cc where cc.empresa.id=:empresaId order by cc.codigo asc")
    List<CuentaContable> consultarPorEmpresa(long empresaId);
    @Query(value = "select cc from CuentaContable cc where cc.estado=:estado order by cc.codigo asc")
    List<CuentaContable> consultarPorEstado(String estado);
    @Query(value = "select cc from CuentaContable cc where cc.empresa.id=:empresaId and cc.estado=:estado order by cc.codigo asc")
    List<CuentaContable> consultarPorEmpresaEstado(long empresaId, String estado);
}
