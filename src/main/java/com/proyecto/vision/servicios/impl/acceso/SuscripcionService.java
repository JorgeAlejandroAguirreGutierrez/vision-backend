package com.proyecto.vision.servicios.impl.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.acceso.Suscripcion;
import com.proyecto.vision.repositorios.acceso.ISuscripcionRepository;
import com.proyecto.vision.servicios.interf.acceso.ISuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SuscripcionService implements ISuscripcionService {
    @Autowired
    private ISuscripcionRepository rep;

    @Override
    public void validar(Suscripcion suscripcion) {
        if(suscripcion.getPaquete().getId() ==  Constantes.ceroId) throw new DatoInvalidoException(Constantes.paquete);
        if(suscripcion.getEmpresa().getId() ==  Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
    }

    @Override
    public Suscripcion crear(Suscripcion suscripcion) {
        validar(suscripcion);
    	Optional<String>codigo = Util.generarCodigo(Constantes.tabla_suscripcion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        suscripcion.setCodigo(codigo.get());
    	Date fechaInicial = new Date();
    	suscripcion.setFechaInicial(fechaInicial);
        if(suscripcion.getPaquete().getTipo().equals(Constantes.mensual)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicial);
            calendar.add(Calendar.DAY_OF_YEAR, 30);
            Date fechaFinal = calendar.getTime();
            suscripcion. setFechaFinal(fechaFinal);
        }
        if(suscripcion.getPaquete().getTipo().equals(Constantes.anual)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicial);
            calendar.add(Calendar.DAY_OF_YEAR, 365);
            Date fechaFinal = calendar.getTime();
            suscripcion. setFechaFinal(fechaFinal);
        }
        suscripcion.setEstado(Constantes.estadoActivo);
        Optional<Suscripcion> opcionalUltimaSuscripcion = rep.obtenerPorEmpresaYEstado(suscripcion.getEmpresa().getId(), Constantes.estadoActivo);
        if(!opcionalUltimaSuscripcion.isEmpty()){
            Suscripcion ultimaSuscripcion = opcionalUltimaSuscripcion.get();
            ultimaSuscripcion.setEstado(Constantes.estadoInactivo);
            rep.save(ultimaSuscripcion);
        }
    	Suscripcion res = rep.save(suscripcion);
        res.normalizar();
        return res;
    }

    @Override
    public Suscripcion actualizar(Suscripcion suscripcion) {
        validar(suscripcion);
        Suscripcion res = rep.save(suscripcion);
        res.normalizar();
        return res;
    }

    @Override
    public Suscripcion activar(Suscripcion suscripcion) {
        validar(suscripcion);
        suscripcion.setEstado(Constantes.estadoActivo);
        Suscripcion res = rep.save(suscripcion);
        res.normalizar();
        return res;
    }

    @Override
    public Suscripcion inactivar(Suscripcion suscripcion) {
        validar(suscripcion);
        suscripcion.setEstado(Constantes.estadoInactivo);
        Suscripcion res = rep.save(suscripcion);
        res.normalizar();
        return res;
    }

    @Override
    public Suscripcion obtener(long id) {
        Optional<Suscripcion> suscripcion = rep.findById(id);
        if(suscripcion.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Suscripcion res = suscripcion.get();
        res.normalizar();
        return res;
    }

    @Override
    public List<Suscripcion> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Suscripcion> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Suscripcion> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Suscripcion> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<Suscripcion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public Suscripcion obtenerUltimoPorEmpresa(long empresaId){
        Optional<Suscripcion> suscripcion = rep.obtenerUltimoPorEmpresa(empresaId);
        if(suscripcion.isEmpty()){
            return null;
        }
        return suscripcion.get();
    }

    @Override
    public boolean verificar(long empresaId){
        Optional<Suscripcion> opcionalSuscripcion = rep.obtenerPorEmpresaYEstado(empresaId, Constantes.estadoActivo);
        if(opcionalSuscripcion.isEmpty()){
            return false;
        }
        Suscripcion suscripcion = opcionalSuscripcion.get();
        Date hoy = new Date();
        if(suscripcion.getEstado().equals(Constantes.estadoActivo) && suscripcion.getFechaInicial().before(hoy) && suscripcion.getFechaFinal().after(hoy) &&
                suscripcion.getConteoComprobantes() < suscripcion.getPaquete().getMaximoComprobantes()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Suscripcion aumentarConteo(long empresaId){
        Optional<Suscripcion> opcionalSuscripcion = rep.obtenerPorEmpresaYEstado(empresaId, Constantes.estadoActivo);
        if(opcionalSuscripcion.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.suscripcion);
        }
        Suscripcion suscripcion = opcionalSuscripcion.get();
        Date hoy = new Date();
        if(suscripcion.getEstado().equals(Constantes.estadoActivo) && suscripcion.getConteoComprobantes() >= suscripcion.getPaquete().getMaximoComprobantes()) {
            suscripcion.setEstado(Constantes.estadoInactivo);
            rep.save(suscripcion);
            throw new SuscripcionInvalidaException();
        }
        if(suscripcion.getEstado().equals(Constantes.estadoActivo) && suscripcion.getFechaInicial().before(hoy) && suscripcion.getFechaFinal().after(hoy)
                && suscripcion.getConteoComprobantes() < suscripcion.getPaquete().getMaximoComprobantes()){
            long conteoComprobantes = suscripcion.getConteoComprobantes();
            suscripcion.setConteoComprobantes(conteoComprobantes + 1);
            return rep.save(suscripcion);
        }
        throw new SuscripcionInvalidaException();
    }

    @Override
    public Suscripcion renovar(long empresaId){
        Optional<Suscripcion> opcionalUltimaSuscripcion = rep.obtenerPorEmpresaYEstado(empresaId, Constantes.estadoActivo);
        if(opcionalUltimaSuscripcion.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.suscripcion);
        }
        Suscripcion ultimaSuscripcion = opcionalUltimaSuscripcion.get();
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setPaquete(ultimaSuscripcion.getPaquete());
        suscripcion.setEmpresa(ultimaSuscripcion.getEmpresa());
        suscripcion.setConteoComprobantes(Constantes.ceroId);
        Date fechaInicial = new Date();
        suscripcion.setFechaInicial(fechaInicial);
        if(ultimaSuscripcion.getPaquete().getTipo().equals(Constantes.mensual)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicial);
            calendar.add(Calendar.DAY_OF_YEAR, 30);
            Date fechaFinal = calendar.getTime();
            suscripcion. setFechaFinal(fechaFinal);
        }
        if(ultimaSuscripcion.getPaquete().getTipo().equals(Constantes.anual)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicial);
            calendar.add(Calendar.DAY_OF_YEAR, 365);
            Date fechaFinal = calendar.getTime();
            suscripcion. setFechaFinal(fechaFinal);
        }
        ultimaSuscripcion.setEstado(Constantes.estadoInactivo);
        rep.save(ultimaSuscripcion);
        return rep.save(suscripcion);
    }
}
