package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.repositorios.inventario.IMedidaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MedidaService implements IMedidaService {
    @Autowired
    private IMedidaRepository rep;
    
    @Override
    public Medida crear(Medida medida) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_medida);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	medida.setCodigo(codigo.get());
    	return rep.save(medida);
    }

    @Override
    public Medida actualizar(Medida medida) {
        return rep.save(medida);
    }

    @Override
    public Medida activar(Medida medida) {
        medida.setEstado(Constantes.activo);
        medida=rep.save(medida);
        return medida;
    }

    @Override
    public Medida inactivar(Medida medida) {
        medida.setEstado(Constantes.inactivo);
        medida=rep.save(medida);
        return medida;
    }

    @Override
    public Medida obtener(long id) {
        Optional<Medida> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.medida);
    }

    @Override
    public List<Medida> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Medida> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Medida> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Medida> medidas=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,5);
            for (List<String> datos: info) {
                Medida medida = new Medida(datos);
                medidas.add(medida);
            }
            rep.saveAll(medidas);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
