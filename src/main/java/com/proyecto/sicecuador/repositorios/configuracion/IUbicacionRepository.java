package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IUbicacionRepository extends JpaRepository<Ubicacion, Long>, JpaSpecificationExecutor<Ubicacion> {
    @Query(value = "SELECT DISTINCT u.provincia FROM ubicacion u", nativeQuery = true)
    List<String> findProvincias();

    @Query(value = "SELECT DISTINCT u.canton FROM ubicacion u WHERE u.provincia = :provincia" ,nativeQuery = true)
    List<String> findCantones(@Param("provincia") String provincia);

    @Query(value = "SELECT * FROM ubicacion u WHERE u.canton = :canton", nativeQuery = true)
    List<Ubicacion> findParroquias(@Param("canton") String canton);

    @Query(value = "SELECT u FROM Ubicacion u WHERE u.provincia = :provincia and u.canton = :canton and u.parroquia = :parroquia")
    Optional<Ubicacion> findByProvinciaAndCantonAndParroquia(
            @Param("provincia") String provincia,
            @Param("canton") String canton,
            @Param("parroquia") String parroquia);
}
