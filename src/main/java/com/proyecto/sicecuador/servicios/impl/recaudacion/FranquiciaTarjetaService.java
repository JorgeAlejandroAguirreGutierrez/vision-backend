package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.modelos.recaudacion.FranquiciaTarjeta;
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
    public void validar(FranquiciaTarjeta franquiciaTarjeta) {
        if(franquiciaTarjeta.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(franquiciaTarjeta.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(franquiciaTarjeta.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public FranquiciaTarjeta crear(FranquiciaTarjeta franquiciaTarjeta) {
        validar(franquiciaTarjeta);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_franquicia_tarjeta);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	franquiciaTarjeta.setCodigo(codigo.get());
    	franquiciaTarjeta.setEstado(Constantes.activo);
    	return rep.save(franquiciaTarjeta);
    }

    @Override
    public FranquiciaTarjeta actualizar(FranquiciaTarjeta franquiciaTarjeta) {
        validar(franquiciaTarjeta);
        return rep.save(franquiciaTarjeta);
    }

    @Override
    public FranquiciaTarjeta activar(FranquiciaTarjeta franquiciaTarjeta) {
        validar(franquiciaTarjeta);
        franquiciaTarjeta.setEstado(Constantes.activo);
        return rep.save(franquiciaTarjeta);
    }

    @Override
    public FranquiciaTarjeta inactivar(FranquiciaTarjeta franquiciaTarjeta) {
        validar(franquiciaTarjeta);
        franquiciaTarjeta.setEstado(Constantes.inactivo);
        return rep.save(franquiciaTarjeta);
    }

    @Override
    public FranquiciaTarjeta obtener(long id) {
        Optional<FranquiciaTarjeta> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.franquicia_tarjeta);
    }

    @Override
    public List<FranquiciaTarjeta> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<FranquiciaTarjeta> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<FranquiciaTarjeta> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<FranquiciaTarjeta> franquicias_tarjetas=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,4);
            for (List<String> datos: info) {
                FranquiciaTarjeta franquicia_tarjeta = new FranquiciaTarjeta(datos);
                franquicias_tarjetas.add(franquicia_tarjeta);
            }
            rep.saveAll(franquicias_tarjetas);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
