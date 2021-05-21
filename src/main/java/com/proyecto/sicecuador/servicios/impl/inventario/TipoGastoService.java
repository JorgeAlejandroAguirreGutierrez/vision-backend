package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.repositorios.inventario.ITipoGastoRepository;
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
    
    @Override
    public TipoGasto crear(TipoGasto tipo_gasto) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_gasto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipo_gasto.setCodigo(codigo.get());
    	return rep.save(tipo_gasto);
    }

    @Override
    public TipoGasto actualizar(TipoGasto tipo_gasto) {
        return rep.save(tipo_gasto);
    }

    @Override
    public TipoGasto eliminar(TipoGasto tipo_gasto) {
        rep.deleteById(tipo_gasto.getId());
        return tipo_gasto;
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
