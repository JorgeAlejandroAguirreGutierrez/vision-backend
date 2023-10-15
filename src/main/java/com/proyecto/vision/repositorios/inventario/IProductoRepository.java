package com.proyecto.vision.repositorios.inventario;

import com.proyecto.vision.modelos.inventario.Producto;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
    @Query(value = "select p from Producto p order by p.id desc")
    List<Producto> consultar();
    @Query(value = "select p from Producto p where p.nombre=:nombre order by p.id desc")
    Optional<Producto> obtenerPorNombre(String nombre);
    @Query(value = "select p from Producto p where p.estado=:estado order by p.id desc")
    List<Producto> consultarPorEstado(String estado);
    @Query(value = "select p from Producto p where p.empresa.id=:empresaId order by p.id desc")
    List<Producto> consultarPorEmpresa(long empresaId);
    @Query(value = "select p from Producto p where p.empresa.id=:empresaId and p.estado = :estado order by p.id desc")
    List<Producto> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select p from Producto p where p.proveedor.id = :proveedorId and p.estado = :estado order by p.id desc")
    List<Producto> consultarPorProveedor(long proveedorId, String estado);
    @Query(value = "select p from Producto p where p.categoriaProducto.abreviatura = :categoriaProducto and p.estado = :estado order by p.id desc")
    List<Producto> consultarPorCategoriaProductoYEstado(String categoriaProducto, String estado);
    @Query(value = "select p from Producto p where p.categoriaProducto.abreviatura = :categoriaProducto and p.proveedor.id = :proveedorId and p.estado = :estado order by p.id desc")
    List<Producto> consultarPorCategoriaProductoYProveedorYEstado(String categoriaProducto, long proveedorId, String estado);
    @Query(value = "select p from Producto p where p.categoriaProducto.abreviatura = :categoriaProducto and p.empresa.id = :empresaId and p.estado = :estado order by p.id desc")
    List<Producto> consultarPorCategoriaProductoYEmpresaYEstado(String categoriaProducto, long empresaId, String estado);
    @Query(value = "select p from Producto p where p.categoriaProducto.abreviatura = :categoriaProducto and p.proveedor.id = :proveedorId and p.empresa.id = :empresaId and p.estado = :estado order by p.id desc")
    List<Producto> consultarPorCategoriaProductoYProveedorYEmpresaYEstado(String categoriaProducto, long proveedorId, long empresaId, String estado);
}
