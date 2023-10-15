package com.proyecto.vision.repositorios.entrega;

import com.proyecto.vision.modelos.entrega.Vehiculo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehiculoRepository extends JpaRepository<Vehiculo, Long>, JpaSpecificationExecutor<Vehiculo> {
    @Query(value = "select v from Vehiculo v order by v.id desc")
    List<Vehiculo> consultar();
    @Query(value = "select v from Vehiculo v where v.empresa.id=:empresaId order by v.id desc")
    List<Vehiculo> consultarPorEmpresa(long empresaId);
    @Query(value = "select v from Vehiculo v where v.estado=:estado order by v.id desc")
    List<Vehiculo> consultarPorEstado(String estado);
    @Query(value = "select v from Vehiculo v where v.empresa.id=:empresaId and v.estado=:estado order by v.id asc")
    List<Vehiculo> consultarPorEmpresaYEstado(long empresaId, String estado);

}
