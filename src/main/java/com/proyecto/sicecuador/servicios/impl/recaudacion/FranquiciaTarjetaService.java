package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.recaudacion.FranquiciaTarjeta;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import com.proyecto.sicecuador.repositorios.recaudacion.IFranquiciaTarjetaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IFranquiciaTarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class FranquiciaTarjetaService implements IFranquiciaTarjetaService {
    @Autowired
    private IFranquiciaTarjetaRepository rep;
    
    @Override
    public FranquiciaTarjeta crear(FranquiciaTarjeta franquicia_tarjeta) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_franquicia_tarjeta);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	franquicia_tarjeta.setCodigo(codigo.get());
    	return rep.save(franquicia_tarjeta);
    }

    @Override
    public FranquiciaTarjeta actualizar(FranquiciaTarjeta franquicia_tarjeta) {
        return rep.save(franquicia_tarjeta);
    }

    @Override
    public FranquiciaTarjeta eliminar(FranquiciaTarjeta franquicia_tarjeta) {
        rep.deleteById(franquicia_tarjeta.getId());
        return franquicia_tarjeta;
    }

    @Override
    public Optional<FranquiciaTarjeta> obtener(FranquiciaTarjeta franquicia_tarjeta) {
        return rep.findById(franquicia_tarjeta.getId());
    }

    @Override
    public List<FranquiciaTarjeta> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<FranquiciaTarjeta> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<FranquiciaTarjeta> franquicias_tarjetas=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,4);
            for (List<String> datos: info) {
                FranquiciaTarjeta franquicia_tarjeta = new FranquiciaTarjeta(datos);
                franquicias_tarjetas.add(franquicia_tarjeta);
            }
            if(franquicias_tarjetas.isEmpty()){
                return false;
            }
            rep.saveAll(franquicias_tarjetas);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
