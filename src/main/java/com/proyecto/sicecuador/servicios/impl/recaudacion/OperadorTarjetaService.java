package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.sicecuador.repositorios.recaudacion.IOperadorTarjetaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IOperadorTarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperadorTarjetaService implements IOperadorTarjetaService {
    @Autowired
    private IOperadorTarjetaRepository rep;
    
    @Override
    public OperadorTarjeta crear(OperadorTarjeta operadorTarjeta) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_operador_tarjeta);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	operadorTarjeta.setCodigo(codigo.get());
    	return rep.save(operadorTarjeta);
    }

    @Override
    public OperadorTarjeta actualizar(OperadorTarjeta operadorTarjeta) {
        return rep.save(operadorTarjeta);
    }

    @Override
    public OperadorTarjeta activar(OperadorTarjeta operadorTarjeta) {
        operadorTarjeta.setEstado(Constantes.activo);
        return rep.save(operadorTarjeta);
    }

    @Override
    public OperadorTarjeta inactivar(OperadorTarjeta operadorTarjeta) {
        operadorTarjeta.setEstado(Constantes.inactivo);
        return rep.save(operadorTarjeta);
    }

    @Override
    public OperadorTarjeta obtener(long id) {
        Optional<OperadorTarjeta> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.operador_tarjeta);
    }

    @Override
    public List<OperadorTarjeta> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<OperadorTarjeta> consultarActivos() {
        return rep.consultarPorEstado(Constantes.activo);
    }
    
    @Override
    public Page<OperadorTarjeta> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<OperadorTarjeta> consultarPorTipo(OperadorTarjeta operadorTarjeta) {
        return rep.consultarPorTipo(operadorTarjeta.getTipo(), Constantes.activo);
    }
    
    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<OperadorTarjeta> operadoresTarjetas=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal,5);
            for (List<String> datos: info) {
                OperadorTarjeta operadorTarjeta = new OperadorTarjeta(datos);
                operadoresTarjetas.add(operadorTarjeta);
            }
            rep.saveAll(operadoresTarjetas);
        }catch (Exception e){
        	System.err.println(e.getMessage());
        }
    }
}
