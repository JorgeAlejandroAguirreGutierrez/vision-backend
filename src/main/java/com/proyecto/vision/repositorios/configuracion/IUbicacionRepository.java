package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IUbicacionRepository extends JpaRepository<Ubicacion, Long>, JpaSpecificationExecutor<Ubicacion> {
    @Query(value = "select u from Ubicacion u order by u.codigo desc")
    List<Ubicacion> consultar();
    @Query(value = "select u from Ubicacion u where u.estado = :estado order by u.codigo desc")
    List<Ubicacion> consultarPorEstado(String estado);
	@Query(value = "select u from Ubicacion u where u.codigoNorma like '%'||:codigoNorma||'%' and u.provincia like '%'||:provincia||'%' and u.canton like '%'||:canton||'%' and u.parroquia like '%'||:parroquia||'%' order by u.codigo desc")
	List<Ubicacion> buscar(String codigoNorma, String provincia, String canton, String parroquia);
	@Query(value = "select distinct u.provincia from ubicacion u where u.estado = :estado order by u.provincia", nativeQuery = true)
    List<String> consultarProvincias(String estado);
    @Query(value = "select distinct u.canton from ubicacion u where u.provincia =:provincia and u.estado= :estado order by u.canton" ,nativeQuery = true)
    List<String> consultarCantones(String provincia, String estado);
    @Query(value = "select * from ubicacion u where u.canton = :canton and u.estado= :estado order by u.parroquia", nativeQuery = true)
    List<Ubicacion> consultarParroquias(String canton, String estado);
    @Query(value = "select u from Ubicacion u where u.provincia = :provincia and u.canton = :canton and u.parroquia = :parroquia and u.estado= :estado")
    Optional<Ubicacion> obtenerPorProvinciaYCantonYParroquia(String provincia, String canton, String parroquia, String estado);
}
