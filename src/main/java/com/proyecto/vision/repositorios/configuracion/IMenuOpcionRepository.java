package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.MenuOpcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IMenuOpcionRepository extends JpaRepository<MenuOpcion, Long>, JpaSpecificationExecutor<MenuOpcion> {
    @Query(value = "select m from MenuOpcion m order by m.id desc")
    List<MenuOpcion> consultar();
    @Query(value = "select m from MenuOpcion m where m.estado=:estado order by m.id desc")
    List<MenuOpcion> consultarPorEstado(String estado);
    @Query(value = "select distinct m.modulo from MenuOpcion m where m.estado = :estado order by m.modulo desc")
    List<String> consultarModulos(String estado);
    @Query(value = "select m from MenuOpcion m where m.modulo = :modulo and m.menu = :menu and m.estado = :estado order by m.opcion desc")
    List<MenuOpcion> consultarPorModulo(String modulo, String menu, String estado);
    @Query(value = "select m from MenuOpcion m where m.modulo = :modulo and m.menu = :menu and m.estado = :estado order by m.opcion desc")
    List<MenuOpcion> consultarOpciones(String modulo,String estado);
    @Query(value = "select m from MenuOpcion m where m.operacion = :operacion and m.estado = :estado")
    Optional<MenuOpcion> findByOperacion(String operacion, String estado);
    @Query(value = "select m from MenuOpcion m where m.tabla = :tabla and m.operacion = :operacion and m.estado = :estado")
    Optional<MenuOpcion> findByTablaAndOperacion(String tabla, String operacion, String estado);
}
