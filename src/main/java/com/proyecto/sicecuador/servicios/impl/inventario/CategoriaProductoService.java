package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
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
    public CategoriaProducto crear(CategoriaProducto categoria_producto) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_categoria_producto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	categoria_producto.setCodigo(codigo.get());
    	return rep.save(categoria_producto);
    }

    @Override
    public CategoriaProducto actualizar(CategoriaProducto categoria_producto) {
        return rep.save(categoria_producto);
    }

    @Override
    public CategoriaProducto eliminar(CategoriaProducto categoria_producto) {
        rep.deleteById(categoria_producto.getId());
        return categoria_producto;
    }

    @Override
    public Optional<CategoriaProducto> obtener(CategoriaProducto categoria_producto) {
        return rep.findById(categoria_producto.getId());
    }

    @Override
    public List<CategoriaProducto> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<CategoriaProducto> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<CategoriaProducto> categorias_productos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,10);
            for (List<String> datos: info) {
                CategoriaProducto categoria_producto = new CategoriaProducto(datos);
                categorias_productos.add(categoria_producto);

            }
            if(categorias_productos.isEmpty()){
                return false;
            }
            rep.saveAll(categorias_productos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
