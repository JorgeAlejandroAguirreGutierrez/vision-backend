package com.proyecto.sicecuador.repositorios.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecaudacionRepository extends JpaRepository<Recaudacion, Long>, JpaSpecificationExecutor<Recaudacion> {
	@Query(value = "select r from Recaudacion r where r.factura.id=:facturaId")
    public Optional<Recaudacion> obtenerPorFactura(long facturaId);
}
