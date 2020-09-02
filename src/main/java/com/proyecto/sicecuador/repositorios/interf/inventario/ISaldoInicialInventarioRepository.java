package com.proyecto.sicecuador.repositorios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.SaldoInicialInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaldoInicialInventarioRepository extends JpaRepository<SaldoInicialInventario, Long>, JpaSpecificationExecutor<SaldoInicialInventario> {
}
