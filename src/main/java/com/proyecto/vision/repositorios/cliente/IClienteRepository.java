package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.repositorios.IGenericoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IClienteRepository extends IGenericoRepository<Cliente> {
    @Query(value = "select c from Cliente c order by c.id desc")
    List<Cliente> consultar();
    @Query(value = "select c from Cliente c order by c.id desc")
    Page<Cliente> consultarPagina(Pageable pageable);
    @Query(value = "select c from Cliente c where c.empresa.id = :empresaId order by c.id desc")
    Page<Cliente> consultarPorEmpresa(long empresaId, Pageable pageable);
    @Query(value = "select c from Cliente c where (c.razonSocial like %:filtro% or c.identificacion like %:filtro% or c.direccion like %:filtro%) and c.empresa.id = :empresaId order by c.id desc")
    Page<Cliente> consultarFiltroPorEmpresa(String filtro, long empresaId, Pageable pageable);
    @Query(value = "select c from Cliente c where c.estado = :estado order by c.id desc")
    List<Cliente> consultarPorEstado(String estado);
    @Query(value = "select c from Cliente c where c.empresa.id = :empresaId and c.estado = :estado order by c.id desc")
    List<Cliente> consultarPorEmpresaYEstado(long empresaId, String estado);
	@Query(value = "select c from Cliente c where c.empresa.id = :empresaId and c.identificacion=:identificacion and c.estado=:estado")
    Optional<Cliente> obtenerPorEmpresaYIdentificacion(long empresaId, String identificacion, String estado);
	@Query(value = "select c from Cliente c where c.razonSocial = :razonSocial and c.empresa.id = :empresaId and c.estado = :estado")
    Optional<Cliente> obtenerPorRazonSocialYEmpresaYEstado(String razonSocial, long empresaId, String estado);
    @Query(value = "select c from Cliente c where c.identificacion = :identificacion and c.empresa.id = :empresaId and c.estado = :estado")
    Optional<Cliente> obtenerPorIdentificacionYEmpresaYEstado(String identificacion, long empresaId, String estado);
}
