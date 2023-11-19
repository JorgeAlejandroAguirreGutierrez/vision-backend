package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductoProveedorRepository extends JpaRepository<ProductoProveedor, Long>, JpaSpecificationExecutor<ProductoProveedor> {
    @Query(value = "select pp from ProductoProveedor pp order by pp.id desc")
    List<ProductoProveedor> consultar();
    @Query(value = "select pp from ProductoProveedor pp where pp.estado=:estado order by pp.id desc")
    List<ProductoProveedor> consultarPorEstado(String estado);
    @Query(value = "select pp from ProductoProveedor pp where pp.producto.empresa.id=:empresaId order by pp.id desc")
    List<ProductoProveedor> consultarPorEmpresa(long empresaId);
    @Query(value = "select pp from ProductoProveedor pp where pp.producto.empresa.id=:empresaId and pp.estado = :estado order by pp.id desc")
    List<ProductoProveedor> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select pp from ProductoProveedor pp where pp.proveedor.id = :proveedorId and pp.estado = :estado order by pp.id desc")
    List<ProductoProveedor> consultarPorProveedorYEstado(long proveedorId, String estado);
    @Query(value = "select pp from ProductoProveedor pp where pp.producto.id = :producto and pp.estado = :estado order by pp.id desc")
    List<ProductoProveedor> consultarPorProductoYEstado(long producto, String estado);
    //@Query(value = "select pp from ProductoProducto pp where pp.codigoEquivalente = :codigoEquivalente and pp.producto.empresa.id = :empresaId")
    //Optional<ProductoProveedor> obtenerPorCodigoEquivalenteYEmpresa(String codigoEquivalente, long empresaId);
}
