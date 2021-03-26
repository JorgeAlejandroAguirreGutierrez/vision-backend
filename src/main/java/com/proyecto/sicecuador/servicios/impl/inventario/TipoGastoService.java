package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.ITipoGastoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.ITipoGastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoGastoService implements ITipoGastoService {
    @Autowired
    private ITipoGastoRepository rep;
    @Autowired
    private static IParametroRepository parametroRep;
    
    @Override
    public TipoGasto crear(TipoGasto bodega) {
        return rep.save(bodega);
    }

    @Override
    public TipoGasto actualizar(TipoGasto bodega) {
        return rep.save(bodega);
    }

    @Override
    public TipoGasto eliminar(TipoGasto bodega) {
        rep.deleteById(bodega.getId());
        return bodega;
    }

    @Override
    public Optional<TipoGasto> obtener(TipoGasto tipo_gasto) {
        return rep.findById(tipo_gasto.getId());
    }

    @Override
    public List<TipoGasto> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<TipoGasto> tipos_gastos=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,9);
            for (List<String> datos: info) {
                TipoGasto tipo_gasto = new TipoGasto(datos);
                tipos_gastos.add(tipo_gasto);
            }
            if(tipos_gastos.isEmpty()){
                return false;
            }
            rep.saveAll(tipos_gastos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
