package com.proyecto.vision.servicios.interf.inventario;

import java.util.List;
import com.proyecto.vision.modelos.inventario.GrupoProducto;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IGrupoProductoService extends IGenericoService<GrupoProducto> {
	void validar(GrupoProducto grupoProducto);
	GrupoProducto activar(GrupoProducto grupoProducto);
	GrupoProducto inactivar(GrupoProducto grupoProducto);
	List<GrupoProducto> consultarPorEstado(String estado);
	List<GrupoProducto> consultarPorEmpresa(long empresaId);
	List<GrupoProducto> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<GrupoProducto> buscar(GrupoProducto grupo_producto);
	List<String> consultarGrupos(long empresaId, String estado);
	List<String> consultarSubgrupos(long empresaId, String estado, String grupo);
	List<String> consultarSecciones(long empresaId, String estado, String grupo, String subgrupo);
	List<String> consultarLineas(long empresaId, String estado, String grupo, String subgrupo, String seccion);
	List<String> consultarSublineas(long empresaId, String estado, String grupo, String subgrupo, String seccion, String linea);
	List<String> consultarPresentaciones(long empresaId, String estado, String grupo, String subgrupo, String seccion, String linea, String sublinea);
	GrupoProducto obtenerGrupoProducto(String grupo, String subgrupo, String seccion, String linea, String sublinea, String presentacion);
}
