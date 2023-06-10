package com.proyecto.sicecuador.repositorios.inventario;

import com.proyecto.sicecuador.modelos.inventario.Producto;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
    @Query(value = "select p from Producto p order by p.codigo asc")
    List<Producto> consultar();
    @Query(value = "select p from Producto p where p.nombre=:nombre order by p.codigo asc")
    Optional<Producto> obtenerPorNombre(String nombre);
    @Query(value = "select p from Producto p where p.estado=:estado order by p.codigo asc")
    List<Producto> consultarPorEstado(String estado);
    @Query(value = "select p from Producto p where p.empresa.id=:empresaId order by p.codigo asc")
    List<Producto> consultarPorEmpresa(long empresaId);
    @Query(value = "select p from Producto p where p.empresa.id=:empresaId and p.estado = :estado order by p.codigo asc")
    List<Producto> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select p from Producto p where p.proveedor.id = :proveedorId and p.estado = :estado order by p.codigo asc")
    List<Producto> consultarPorProveedor(long proveedorId, String estado);
    @Query(value = "select p from Producto p where p.categoriaProducto.descripcion = :categoriaProducto and p.estado = :estado order by p.codigo asc")
    List<Producto> consultarPorCategoriaProductoYEstado(String categoriaProducto, String estado);
    @Query(value = "select p from Producto p where p.categoriaProducto.descripcion = :categoriaProducto and p.proveedor.id = :proveedorId and p.estado = :estado order by p.codigo asc")
    List<Producto> consultarPorCategoriaProductoYProveedorYEstado(String categoriaProducto, long proveedorId, String estado);
    @Query(value = "select p from Producto p where p.categoriaProducto.descripcion = :categoriaProducto and p.empresa.id = :empresaId and p.estado = :estado order by p.codigo asc")
    List<Producto> consultarPorCategoriaProductoYEmpresaYEstado(String categoriaProducto, long empresaId, String estado);
    @Query(value = "select p from Producto p where p.categoriaProducto.descripcion = :categoriaProducto and p.proveedor.id = :proveedorId and p.empresa.id = :empresaId and p.estado = :estado order by p.codigo asc")
    List<Producto> consultarPorCategoriaProductoYProveedorYEmpresaYEstado(String categoriaProducto, long proveedorId, long empresaId, String estado);
}
