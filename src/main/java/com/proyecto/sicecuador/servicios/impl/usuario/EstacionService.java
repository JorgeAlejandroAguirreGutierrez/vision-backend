package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.repositorios.usuario.IEstacionRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EstacionService implements IEstacionService {
    @Autowired
    private IEstacionRepository rep;

    @Override
    public void validar(Estacion estacion) {
        if(estacion.getCodigoSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.codigoSRI);
        if(estacion.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(estacion.getEstablecimiento().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.establecimiento);
    }
    
    @Override
    public Estacion crear(Estacion estacion) {
        validar(estacion);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_estacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	estacion.setCodigo(codigo.get());
    	estacion.setEstado(Constantes.activo);
    	Estacion res = rep.save(estacion);
        res.normalizar();
        return res;
    }

    @Override
    public Estacion actualizar(Estacion estacion) {
        validar(estacion);
        Estacion res = rep.save(estacion);
        res.normalizar();
        return res;
    }

    @Override
    public Estacion activar(Estacion estacion) {
        validar(estacion);
        estacion.setEstado(Constantes.activo);
        Estacion res = rep.save(estacion);
        res.normalizar();
        return res;
    }

    @Override
    public Estacion inactivar(Estacion estacion) {
        validar(estacion);
        estacion.setEstado(Constantes.inactivo);
        Estacion res = rep.save(estacion);
        res.normalizar();
        return res;
    }

    @Override
    public Estacion obtener(long id) {
        Optional<Estacion> estacion= rep.findById(id);
        if(estacion.isPresent()) {
        	Estacion res = estacion.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.estacion);
    }

    @Override
    public List<Estacion> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Estacion> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Estacion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Estacion> consultarPorEstablecimiento(long establecimientoId) {
        return rep.consultarPorEstablecimiento(establecimientoId, Constantes.activo);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Estacion> puntos_ventas=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,3);
            for (List<String> datos: info) {
                Estacion punto_venta = new Estacion(datos);
                puntos_ventas.add(punto_venta);
            }
            rep.saveAll(puntos_ventas);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
