package com.proyecto.sicecuador.repositorios.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITipoComprobanteRepository extends JpaRepository<TipoComprobante, Long>, JpaSpecificationExecutor<TipoComprobante> {
    @Query(value = "select tc from TipoComprobante tc where tc.nombreTabla = :nombreTabla")
    Optional<TipoComprobante> obtenerPorNombreTabla(String nombreTabla);
}
