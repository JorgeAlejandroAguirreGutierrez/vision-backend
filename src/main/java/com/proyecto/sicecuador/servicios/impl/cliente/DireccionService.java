package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IDireccionRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IDireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DireccionService implements IDireccionService {
    @Autowired
    private IDireccionRepository rep;
    
    @Override
    public Direccion crear(Direccion direccion) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_direccion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	direccion.setCodigo(codigo.get());
    	return rep.save(direccion);
    }

    @Override
    public Direccion actualizar(Direccion direccion) {
        return rep.save(direccion);
    }

    @Override
    public Direccion eliminar(Direccion direccion) {
        rep.deleteById(direccion.getId());
        return direccion;
    }

    @Override
    public Optional<Direccion> obtener(Direccion direccion) {
        return rep.findById(direccion.getId());
    }

    @Override
    public List<Direccion> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Direccion> direcciones=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,7);
            for (List<String> datos: info) {
                Direccion direccion = new Direccion(datos);
                direcciones.add(direccion);
            }
            if(direcciones.isEmpty()){
                return false;
            }
            rep.saveAll(direcciones);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
