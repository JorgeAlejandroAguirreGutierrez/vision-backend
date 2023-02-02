package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.repositorios.inventario.IImpuestoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IImpuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ImpuestoService implements IImpuestoService {
    @Autowired
    private IImpuestoRepository rep;

    @Override
    public void validar(Impuesto impuesto) {
        if(impuesto.getCodigoSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.codigoSRI);
        if(impuesto.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(impuesto.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
        if(impuesto.getPorcentaje() == Constantes.cero) throw new DatoInvalidoException(Constantes.porcentaje);
    }
    
    @Override
    public Impuesto crear(Impuesto impuesto) {
        validar(impuesto);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_impuesto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	impuesto.setCodigo(codigo.get());
    	impuesto.setEstado(Constantes.activo);
    	return rep.save(impuesto);
    }

    @Override
    public Impuesto actualizar(Impuesto impuesto) {
        validar(impuesto);
        return rep.save(impuesto);
    }

    @Override
    public Impuesto activar(Impuesto impuesto) {
        validar(impuesto);
        impuesto.setEstado(Constantes.activo);
        return rep.save(impuesto);
    }

    @Override
    public Impuesto inactivar(Impuesto impuesto) {
        validar(impuesto);
        impuesto.setEstado(Constantes.inactivo);
        return rep.save(impuesto);
    }

    @Override
    public Impuesto obtener(long id) {
        Optional<Impuesto> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.impuesto);
    }

    @Override
    public List<Impuesto> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Impuesto> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Impuesto> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public Optional<Impuesto> obtenerImpuestoPorcentaje(Impuesto impuesto) {
        return rep.findByPorcentaje(impuesto.getPorcentaje(), Constantes.activo);
    }
    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Impuesto> impuestos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,3);
            for (List<String> datos: info) {
                Impuesto impuesto = new Impuesto(datos);
                impuestos.add(impuesto);
            }
            rep.saveAll(impuestos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
