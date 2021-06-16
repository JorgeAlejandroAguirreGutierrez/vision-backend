package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.inventario.Precio;
import com.proyecto.sicecuador.repositorios.inventario.IPrecioRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IPrecioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrecioService implements IPrecioService {
    @Autowired
    private IPrecioRepository rep;
    
    @Override
    public Precio crear(Precio precio) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_precio);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	precio.setCodigo(codigo.get());
    	return rep.save(precio);
    }

    @Override
    public Precio actualizar(Precio precio) {
        return rep.save(precio);
    }

    @Override
    public Precio eliminar(Precio precio) {
        rep.deleteById(precio.getId());
        return precio;
    }

    @Override
    public Optional<Precio> obtener(Precio bodega) {
        return rep.findById(bodega.getId());
    }

    @Override
    public List<Precio> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Precio> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Precio> precios=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,6);
            for (List<String> datos: info) {
                Precio precio = new Precio(datos);
                precios.add(precio);
            }
            if(precios.isEmpty()){
                return false;
            }
            rep.saveAll(precios);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
