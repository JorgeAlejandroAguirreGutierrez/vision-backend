package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
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
    public TipoRetencion crear(TipoRetencion tipo_retencion) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_retencion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipo_retencion.setCodigo(codigo.get());
    	return rep.save(tipo_retencion);
    }

    @Override
    public TipoRetencion actualizar(TipoRetencion tipo_retencion) {
        return rep.save(tipo_retencion);
    }

    @Override
    public TipoRetencion eliminar(TipoRetencion tipo_retencion) {
        rep.deleteById(tipo_retencion.getId());
        return tipo_retencion;
    }

    @Override
    public Optional<TipoRetencion> obtener(TipoRetencion tipo_retencion) {
        return rep.findById(tipo_retencion.getId());
    }

    @Override
    public List<TipoRetencion> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<TipoRetencion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<TipoRetencion> tipos_retenciones=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal, 2);
            for (List<String> datos: info) {
                TipoRetencion tipo_retencion = new TipoRetencion(datos);
                tipos_retenciones.add(tipo_retencion);
            }
            if(tipos_retenciones.isEmpty()){
                return false;
            }
            rep.saveAll(tipos_retenciones);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<TipoRetencion> consultarIvaBien() {
        return rep.findByImpuestoAndTipo("IVA", "BIEN");
    }

    @Override
    public List<TipoRetencion> consultarIvaServicio() {
        return rep.findByImpuestoAndTipo("IVA", "SERVICIO");
    }

    @Override
    public List<TipoRetencion> consultarRentaBien() {
        return rep.findByImpuestoAndTipo("RENTA", "BIEN");
    }

    @Override
    public List<TipoRetencion> consultarRentaServicio() {
        return rep.findByImpuestoAndTipo("RENTA", "SERVICIO");
    }
}
