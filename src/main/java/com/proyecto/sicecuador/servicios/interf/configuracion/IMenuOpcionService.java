package com.proyecto.vision.servicios.interf.configuracion;

import com.proyecto.vision.modelos.configuracion.MenuOpcion;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IMenuOpcionService extends IGenericoService<MenuOpcion> {
	void validar(MenuOpcion menuOpcion);
    MenuOpcion activar(MenuOpcion menuOpcion);
    MenuOpcion inactivar(MenuOpcion menuOpcion);
	List<MenuOpcion> consultarPorEstado(String estado);
    List<String> consultarModulos();
    MenuOpcion obtenerPorOperacion(String operacion);
    MenuOpcion obtenerPorOperacionTabla(MenuOpcion menuOpcion);
    List<MenuOpcion> consultarPorModulo(String modulo);
    List<MenuOpcion> consultarPorOperacion(String operacion);
}
