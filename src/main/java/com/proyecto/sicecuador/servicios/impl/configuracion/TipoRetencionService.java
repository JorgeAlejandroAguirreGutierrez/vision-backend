package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.repositorios.interf.configuracion.ITipoRetencionRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.ITipoRetencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TipoRetencionService implements ITipoRetencionService {
    @Autowired
    private ITipoRetencionRepository rep;
    @Override
    public TipoRetencion crear(TipoRetencion tipo_retencion) {
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
