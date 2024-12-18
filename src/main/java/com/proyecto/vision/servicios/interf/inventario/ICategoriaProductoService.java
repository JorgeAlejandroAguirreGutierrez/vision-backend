package com.proyecto.vision.servicios.interf.inventario;

import java.util.List;
import com.proyecto.vision.modelos.inventario.CategoriaProducto;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface ICategoriaProductoService extends IGenericoService<CategoriaProducto> {
	void validar(CategoriaProducto categoriaProducto);
	CategoriaProducto activar(CategoriaProducto categoriaProducto);
	CategoriaProducto inactivar(CategoriaProducto categoriaProducto);
	List<CategoriaProducto> consultarPorEstado(String estado);
	List<CategoriaProducto> consultarPorEmpresa(long empresaId);
	List<CategoriaProducto> consultarPorEmpresaYEstado(long empresaId, String estado);
	CategoriaProducto obtenerPorAbreviatura(String descripcion);
}
