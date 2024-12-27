package com.proyecto.vision.servicios.impl.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.configuracion.TipoRetencion;
import com.proyecto.vision.repositorios.configuracion.ITipoRetencionRepository;
import com.proyecto.vision.servicios.interf.configuracion.ITipoRetencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TipoRetencionService implements ITipoRetencionService {
    @Autowired
    private ITipoRetencionRepository rep;

    @Override
    public void validar(TipoRetencion tipoRetencion) {
        if(tipoRetencion.getImpuestoRetencion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(tipoRetencion.getTipoRetencion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipoRetencion);
        if(tipoRetencion.getCodigoSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.codigoSRI);
        if(tipoRetencion.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(tipoRetencion.getPorcentaje() == Constantes.cero) throw new DatoInvalidoException(Constantes.porcentaje);
    }
    
    @Override
    public TipoRetencion crear(TipoRetencion tipoRetencion) {
        validar(tipoRetencion);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_retencion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipoRetencion.setCodigo(codigo.get());
    	tipoRetencion.setEstado(Constantes.estadoActivo);
    	return rep.save(tipoRetencion);
    }

    @Override
    public TipoRetencion actualizar(TipoRetencion tipoRetencion) {
        validar(tipoRetencion);
        return rep.save(tipoRetencion);
    }

    @Override
    public TipoRetencion activar(TipoRetencion tipoRetencion) {
        validar(tipoRetencion);
        tipoRetencion.setEstado(Constantes.estadoActivo);
        return rep.save(tipoRetencion);
    }

    @Override
    public TipoRetencion inactivar(TipoRetencion tipoRetencion) {
        validar(tipoRetencion);
        tipoRetencion.setEstado(Constantes.estadoInactivo);
        return rep.save(tipoRetencion);
    }

    @Override
    public TipoRetencion obtener(long id) {
        Optional<TipoRetencion> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.tipo_retencion);
    }

    @Override
    public List<TipoRetencion> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<TipoRetencion> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<TipoRetencion> consultarIvaBien() {
        return rep.findByImpuestoAndTipo(Constantes.iva, Constantes.bien, Constantes.estadoActivo);
    }

    @Override
    public List<TipoRetencion> consultarIvaServicio() {
        return rep.findByImpuestoAndTipo(Constantes.iva, Constantes.servicio, Constantes.estadoActivo);
    }

    @Override
    public List<TipoRetencion> consultarRentaBien() {
        return rep.findByImpuestoAndTipo(Constantes.renta, Constantes.bien, Constantes.estadoActivo);
    }

    @Override
    public List<TipoRetencion> consultarRentaServicio() {
        return rep.findByImpuestoAndTipo(Constantes.renta, Constantes.servicio, Constantes.estadoActivo);
    }
}
