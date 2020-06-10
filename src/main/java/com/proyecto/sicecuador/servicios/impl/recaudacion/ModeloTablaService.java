package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.ModeloTabla;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IModeloTablaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IModeloTablaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ModeloTablaService implements IModeloTablaService {
    @Autowired
    private IModeloTablaRepository rep;
    @Override
    public ModeloTabla crear(ModeloTabla modelo_tabla) {
        return rep.save(modelo_tabla);
    }

    @Override
    public ModeloTabla actualizar(ModeloTabla modelo_tabla) {
        return rep.save(modelo_tabla);
    }

    @Override
    public ModeloTabla eliminar(ModeloTabla operador_tarjeta) {
        rep.deleteById(operador_tarjeta.getId());
        return operador_tarjeta;
    }

    @Override
    public Optional<ModeloTabla> obtener(ModeloTabla modelo_tabla) {
        return rep.findById(modelo_tabla.getId());
    }

    @Override
    public List<ModeloTabla> consultar() {
        return rep.findAll();
    }
}
