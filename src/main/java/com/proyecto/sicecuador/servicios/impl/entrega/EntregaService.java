package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.entrega.Entrega;
import com.proyecto.sicecuador.repositorios.entrega.IEntregaRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.IEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EntregaService implements IEntregaService {
    @Autowired
    private IEntregaRepository rep;
    
    @Override
    public Entrega crear(Entrega entrega) {
    	if(!entrega.isInhabilitar()) {
    		Optional<String>codigo=Util.generarCodigo(Constantes.tabla_entrega);
        	if (codigo.isEmpty()) {
        		throw new CodigoNoExistenteException();
        	}
        	entrega.setCodigo(codigo.get());
    	}
    	
    	return rep.save(entrega);
    }

    @Override
    public Entrega actualizar(Entrega entrega) {
        return rep.save(entrega);
    }

    @Override
    public Entrega eliminar(Entrega entrega) {
        rep.deleteById(entrega.getId());
        return entrega;
    }

    @Override
    public Optional<Entrega> obtener(Entrega entrega) {
        return rep.findById(entrega.getId());
    }
    
    @Override
    public Optional<Entrega> obtenerPorFactura(long facturaId){
    	return rep.obtenerPorFactura(facturaId);
    }

    @Override
    public List<Entrega> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Entrega> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Entrega> guias_remisiones=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,0);
            for (List<String> datos: info) {
                Entrega guia_remision = new Entrega(datos);
                guias_remisiones.add(guia_remision);
            }
            if (guias_remisiones.isEmpty()){
                return false;
            }
            rep.saveAll(guias_remisiones);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
