package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITipoComprobanteRepository extends JpaRepository<TipoComprobante, Long>, JpaSpecificationExecutor<TipoComprobante> {
    @Query(value = "select tc from TipoComprobante tc order by tc.id desc")
    List<TipoComprobante> consultar();
    @Query(value = "select tc from TipoComprobante tc where tc.estado = :estado order by tc.id desc")
    List<TipoComprobante> consultarPorEstado(String estado);
    @Query(value = "select tc from TipoComprobante tc where tc.electronica=:electronica and tc.estado = :estado order by tc.id desc")
    List<TipoComprobante> consultarPorElectronica(String electronica, String estado);
    @Query(value = "select tc from TipoComprobante tc where tc.nombreTabla=:nombreTabla")
    Optional<TipoComprobante> obtenerPorNombreTabla(String nombreTabla);
}
