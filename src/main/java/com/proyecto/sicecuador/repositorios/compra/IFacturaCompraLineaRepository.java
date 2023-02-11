package com.proyecto.sicecuador.repositorios.compra;

import com.proyecto.sicecuador.modelos.compra.FacturaCompraLinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacturaCompraLineaRepository extends JpaRepository<FacturaCompraLinea, Long>, JpaSpecificationExecutor<FacturaCompraLinea> {

}
