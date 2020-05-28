package com.proyecto.sicecuador.repositorios.interf;

import com.proyecto.sicecuador.modelos.Entidad;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("Generico")
public interface IGenericoRepository<T extends Entidad> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
