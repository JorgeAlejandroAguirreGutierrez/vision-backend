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
    @Query(value = "select p from Producto p where p.estado=:estado order by p.codigo asc")
    List<Producto> consultarPorEstado(String estado);
	@Query(value = "select p from Producto p where p.nombre=:nombre order by p.codigo asc")
    Optional<Producto> obtenerPorNombre(String nombre);
    @Query(value = "select p from Producto p where p.categoriaProducto.descripcion = :bien and p.estado = :estado order by p.codigo asc")
    List<Producto> consultarPorBien(String bien, String estado);
    @Query(value = "select p from Producto p where p.categoriaProducto.descripcion = :bien and p.proveedor.id = :proveedorId and p.estado = :estado order by p.codigo asc")
    List<Producto> consultarBienPorProveedor(String bien, long proveedorId, String estado);
    @Query(value = "select p from Producto p where p.proveedor.id = :proveedorId and p.estado = :estado order by p.codigo asc")
    List<Producto> consultarPorProveedor(long proveedorId, String estado);
}
