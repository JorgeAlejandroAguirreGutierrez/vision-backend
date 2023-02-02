package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.TipoPago;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.repositorios.configuracion.ITipoRetencionRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.ITipoRetencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    	tipoRetencion.setEstado(Constantes.activo);
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
        tipoRetencion.setEstado(Constantes.activo);
        return rep.save(tipoRetencion);
    }

    @Override
    public TipoRetencion inactivar(TipoRetencion tipoRetencion) {
        validar(tipoRetencion);
        tipoRetencion.setEstado(Constantes.inactivo);
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
        return rep.findAll();
    }
    
    @Override
    public List<TipoRetencion> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<TipoRetencion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<TipoRetencion> tiposRetenciones=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal, 2);
            for (List<String> datos: info) {
                TipoRetencion tipoRetencion = new TipoRetencion(datos);
                tiposRetenciones.add(tipoRetencion);
            }
            rep.saveAll(tiposRetenciones);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<TipoRetencion> consultarIvaBien() {
        return rep.findByImpuestoAndTipo(Constantes.iva, Constantes.bien, Constantes.activo);
    }

    @Override
    public List<TipoRetencion> consultarIvaServicio() {
        return rep.findByImpuestoAndTipo(Constantes.iva, Constantes.servicio, Constantes.activo);
    }

    @Override
    public List<TipoRetencion> consultarRentaBien() {
        return rep.findByImpuestoAndTipo(Constantes.renta, Constantes.bien, Constantes.activo);
    }

    @Override
    public List<TipoRetencion> consultarRentaServicio() {
        return rep.findByImpuestoAndTipo(Constantes.renta, Constantes.servicio, Constantes.activo);
    }
}
