package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.inventario.CategoriaProducto;
import com.proyecto.vision.repositorios.inventario.ICategoriaProductoRepository;
import com.proyecto.vision.servicios.interf.inventario.ICategoriaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaProductoService implements ICategoriaProductoService {
    @Autowired
    private ICategoriaProductoRepository rep;

    @Override
    public void validar(CategoriaProducto categoriaProducto) {
        if(categoriaProducto.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(categoriaProducto.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public CategoriaProducto crear(CategoriaProducto categoriaProducto) {
        validar(categoriaProducto);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_categoria_producto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	categoriaProducto.setCodigo(codigo.get());
    	categoriaProducto.setEstado(Constantes.estadoActivo);
    	return rep.save(categoriaProducto);
    }

    @Override
    public CategoriaProducto actualizar(CategoriaProducto categoriaProducto) {
        validar(categoriaProducto);
        return rep.save(categoriaProducto);
    }

    @Override
    public CategoriaProducto activar(CategoriaProducto categoriaProducto) {
        validar(categoriaProducto);
        categoriaProducto.setEstado(Constantes.estadoActivo);
        return rep.save(categoriaProducto);
    }

    @Override
    public CategoriaProducto inactivar(CategoriaProducto categoriaProducto) {
        validar(categoriaProducto);
        categoriaProducto.setEstado(Constantes.estadoInactivo);
        return rep.save(categoriaProducto);
    }

    @Override
    public CategoriaProducto obtenerPorAbreviatura(String abreviatura) {
        Optional<CategoriaProducto> resp= rep.obtenerPorAbreviatura(abreviatura, Constantes.estadoActivo);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.categoria_producto);
    }
    @Override
    public CategoriaProducto obtener(long id) {
        Optional<CategoriaProducto> resp= rep.findById(id);
        if(resp.isPresent()) {
            return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.categoria_producto);
    }

    @Override
    public List<CategoriaProducto> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<CategoriaProducto> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<CategoriaProducto> consultarPorEmpresa(long empresaId){
        return consultarPorEmpresa(empresaId);
    }

    @Override
    public List<CategoriaProducto> consultarPorEmpresaYEstado(long empresaId, String estado){
        return consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<CategoriaProducto> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
