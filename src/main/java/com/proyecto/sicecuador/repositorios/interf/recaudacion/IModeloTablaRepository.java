package com.proyecto.sicecuador.repositorios.interf.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.ModeloTabla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IModeloTablaRepository extends JpaRepository<ModeloTabla, Long>, JpaSpecificationExecutor<ModeloTabla> {
}
