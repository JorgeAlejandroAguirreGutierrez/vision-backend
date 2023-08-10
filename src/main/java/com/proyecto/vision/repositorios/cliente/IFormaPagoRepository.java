package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.FormaPago;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormaPagoRepository extends JpaRepository<FormaPago, Long>, JpaSpecificationExecutor<FormaPago> {
    @Query(value = "select fp from FormaPago fp order by fp.codigo desc")
    List<FormaPago> consultar();
    @Query(value = "select fp from FormaPago fp where fp.estado=:estado order by fp.codigo desc")
    List<FormaPago> consultarPorEstado(String estado);
	@Query(value = "select fp from FormaPago fp where fp.codigo like '%'||:codigo||'%' and fp.descripcion like '%'||:descripcion||'%' and fp.abreviatura like '%'||:abreviatura||'%' order by fp.codigo desc")
    List<FormaPago> buscar(String codigo, String descripcion, String abreviatura);
}
