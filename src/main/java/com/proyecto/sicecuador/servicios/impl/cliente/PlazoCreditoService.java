package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IPlazoCreditoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IPlazoCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PlazoCreditoService implements IPlazoCreditoService {
    @Autowired
    private IPlazoCreditoRepository rep;
    
    @Override
    public PlazoCredito crear(PlazoCredito plazoCredito) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_plazo_credito);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	plazoCredito.setCodigo(codigo.get());
    	plazoCredito.setEstado(Constantes.activo);
    	return rep.save(plazoCredito);
    }

    @Override
    public PlazoCredito actualizar(PlazoCredito plazoCredito) {
        return rep.save(plazoCredito);
    }

    @Override
    public PlazoCredito activar(PlazoCredito plazoCredito) {
        plazoCredito.setEstado(Constantes.activo);
        return rep.save(plazoCredito);
    }

    @Override
    public PlazoCredito inactivar(PlazoCredito plazoCredito) {
        plazoCredito.setEstado(Constantes.inactivo);
        return rep.save(plazoCredito);
    }

    @Override
    public PlazoCredito obtener(long id) {
        Optional<PlazoCredito> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.plazo_credito);
    }

    @Override
    public List<PlazoCredito> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<PlazoCredito> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<PlazoCredito> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<PlazoCredito> plazosCreditos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal, 14);
            for (List<String> datos: info) {
                PlazoCredito plazoCredito = new PlazoCredito(datos);
                plazosCreditos.add(plazoCredito);
            }
            rep.saveAll(plazosCreditos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
