package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.repositorios.inventario.ICategoriaProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.ICategoriaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaProductoService implements ICategoriaProductoService {
    @Autowired
    private ICategoriaProductoRepository rep;
    
    @Override
    public CategoriaProducto crear(CategoriaProducto categoriaProducto) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_categoria_producto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	categoriaProducto.setCodigo(codigo.get());
    	categoriaProducto.setEstado(Constantes.activo);
    	return rep.save(categoriaProducto);
    }

    @Override
    public CategoriaProducto actualizar(CategoriaProducto categoriaProducto) {
        return rep.save(categoriaProducto);
    }

    @Override
    public CategoriaProducto activar(CategoriaProducto categoriaProducto) {
        categoriaProducto.setEstado(Constantes.activo);
        return rep.save(categoriaProducto);
    }

    @Override
    public CategoriaProducto inactivar(CategoriaProducto categoriaProducto) {
        categoriaProducto.setEstado(Constantes.inactivo);
        return rep.save(categoriaProducto);
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
        return rep.findAll();
    }
    
    @Override
    public List<CategoriaProducto> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<CategoriaProducto> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<CategoriaProducto> categoriasProductos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,10);
            for (List<String> datos: info) {
                CategoriaProducto categoria_producto = new CategoriaProducto(datos);
                categoriasProductos.add(categoria_producto);

            }
            rep.saveAll(categoriasProductos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
