package com.proyecto.sicecuador.repositorios.cajaBanco;

import com.proyecto.sicecuador.modelos.cajaBanco.CuentaPropia;

import java.util.List;

import com.proyecto.sicecuador.modelos.configuracion.MenuOpcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ICuentaPropiaRepository extends JpaRepository<CuentaPropia, Long>, JpaSpecificationExecutor<CuentaPropia> {
    @Query(value = "select cp from CuentaPropia cp order by cp.codigo asc")
    List<CuentaPropia> consultar();

    @Query(value = "select cp from CuentaPropia cp where cp.estado=:estado order by cp.codigo asc")
    List<CuentaPropia> consultarPorEstado(String estado);
    @Query(value = "select distinct cp.banco.abreviatura from CuentaPropia cp where cp.estado = :estado order by cp.banco.abreviatura")
    List<String> consultarBancos(String estado);
    @Query(value = "select cp from CuentaPropia cp where cp.banco.abreviatura = :banco and cp.estado = :estado order by cp.numero")
    List<CuentaPropia> consultarPorBanco(String banco, String estado);
}
