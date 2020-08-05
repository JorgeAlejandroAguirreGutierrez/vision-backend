package com.proyecto.sicecuador.repositorios.interf.administracion;

import com.proyecto.sicecuador.modelos.administracion.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IModeloRepository extends JpaRepository<Modelo, Long>, JpaSpecificationExecutor<Modelo> {
}
