package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.modelos.inventario.MedidaPrecio;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.IMedidaPrecioRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IMedidaPrecioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedidaPrecioService implements IMedidaPrecioService {
    @Autowired
    private IMedidaPrecioRepository rep;
    @Autowired
    private static IParametroRepository parametroRep;
    
    @Override
    public MedidaPrecio crear(MedidaPrecio medida_precio) {
        return rep.save(medida_precio);
    }

    @Override
    public MedidaPrecio actualizar(MedidaPrecio medida_precio) {
        return rep.save(medida_precio);
    }

    @Override
    public MedidaPrecio eliminar(MedidaPrecio medida_precio) {
        rep.deleteById(medida_precio.getId());
        return medida_precio;
    }

    @Override
    public Optional<MedidaPrecio> obtener(MedidaPrecio medida_precio) {
        return rep.findById(medida_precio.getId());
    }

    @Override
    public List<MedidaPrecio> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<MedidaPrecio> medidas_precios=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,5);
            for (List<String> datos: info) {
                MedidaPrecio medida_precio = new MedidaPrecio(datos);
                medidas_precios.add(medida_precio);
            }
            if(medidas_precios.isEmpty()){
                return false;
            }
            rep.saveAll(medidas_precios);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
