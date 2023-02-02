package com.proyecto.sicecuador.servicios.interf.inventario;

import java.util.List;
import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IGrupoProductoService extends IGenericoService<GrupoProducto> {
	void validar(GrupoProducto grupoProducto);
	GrupoProducto activar(GrupoProducto grupoProducto);
	GrupoProducto inactivar(GrupoProducto grupoProducto);
	List<GrupoProducto> consultarActivos();
	List<GrupoProducto> buscar(GrupoProducto grupo_producto);
	List<String> consultarGrupos();
	List<String> consultarSubgrupos(String grupo);
	List<String> consultarSecciones(String grupo, String subgrupo);
	List<String> consultarLineas(String grupo, String subgrupo, String seccion);
	List<String> consultarSublineas(String grupo, String subgrupo, String seccion, String linea);
	List<String> consultarPresentaciones(String grupo, String subgrupo, String seccion, String linea, String sublinea);
	GrupoProducto obtenerGrupoProducto(String grupo, String subgrupo, String seccion, String linea, String sublinea, String presentacion);
}
