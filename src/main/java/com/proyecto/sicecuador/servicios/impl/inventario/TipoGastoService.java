package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.repositorios.inventario.ITipoGastoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.ITipoGastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void validar(TipoGasto tipoGasto) {
        if(tipoGasto.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(tipoGasto.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public TipoGasto crear(TipoGasto tipoGasto) {
        validar(tipoGasto);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_gasto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipoGasto.setCodigo(codigo.get());
    	tipoGasto.setEstado(Constantes.activo);
    	return rep.save(tipoGasto);
    }

    @Override
    public TipoGasto actualizar(TipoGasto tipoGasto) {
        validar(tipoGasto);
        return rep.save(tipoGasto);
    }

    @Override
    public TipoGasto activar(TipoGasto tipoGasto) {
        validar(tipoGasto);
        tipoGasto.setEstado(Constantes.activo);
        return rep.save(tipoGasto);
    }

    @Override
    public TipoGasto inactivar(TipoGasto tipoGasto) {
        validar(tipoGasto);
        tipoGasto.setEstado(Constantes.inactivo);
        return rep.save(tipoGasto);
    }

    @Override
    public TipoGasto obtener(long id) {
        Optional<TipoGasto> res=rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.tipo_gasto);
    }

    @Override
    public List<TipoGasto> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<TipoGasto> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<TipoGasto> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<TipoGasto> tipos_gastos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,9);
            for (List<String> datos: info) {
                TipoGasto tipo_gasto = new TipoGasto(datos);
                tipos_gastos.add(tipo_gasto);
            }
            rep.saveAll(tipos_gastos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
