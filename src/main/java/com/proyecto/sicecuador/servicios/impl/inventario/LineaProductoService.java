package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.modelos.inventario.LineaProducto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IGrupoProductoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.ILineaProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IGrupoProductoService;
import com.proyecto.sicecuador.servicios.interf.inventario.ILineaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LineaProductoService implements ILineaProductoService {
    @Autowired
    private ILineaProductoRepository rep;
    @Override
    public LineaProducto crear(LineaProducto linea_producto) {
        return rep.save(linea_producto);
    }

    @Override
    public LineaProducto actualizar(LineaProducto linea_producto) {
        return rep.save(linea_producto);
    }

    @Override
    public LineaProducto eliminar(LineaProducto linea_producto) {
        rep.deleteById(linea_producto.getId());
        return linea_producto;
    }

    @Override
    public Optional<LineaProducto> obtener(LineaProducto linea_producto) {
        return rep.findById(linea_producto.getId());
    }

    @Override
    public List<LineaProducto> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<LineaProducto> lineas_productos=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,4);
            for (List<String> datos: info) {
                LineaProducto linea_producto = new LineaProducto(datos);
                lineas_productos.add(linea_producto);
            }
            if(lineas_productos.isEmpty()){
                return false;
            }
            rep.saveAll(lineas_productos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
