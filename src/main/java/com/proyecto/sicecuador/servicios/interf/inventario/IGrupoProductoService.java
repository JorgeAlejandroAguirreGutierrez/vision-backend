package com.proyecto.sicecuador.servicios.interf.inventario;

import java.util.List;
import java.util.Optional;

import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IGrupoProductoService extends IGenericoService<GrupoProducto> {
	List<GrupoProducto> buscar(GrupoProducto grupo_producto);
	List<String> consultarGrupos();
	List<String> consultarSubgrupos(String grupo);
	List<String> consultarCategorias(String grupo, String subgrupo);
	List<String> consultarLineas(String grupo, String subgrupo, String categoria);
	List<String> consultarSublineas(String grupo, String subgrupo, String categoria, String linea);
	List<String> consultarPresentaciones(String grupo, String subgrupo, String categoria, String linea, String sublinea);
	Optional<GrupoProducto> obtenerGrupoProducto(String grupo, String subgrupo, String categoria, String linea, String sublinea, String presentacion);
}
