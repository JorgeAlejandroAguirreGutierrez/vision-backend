package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IOrigenIngresoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IOrigenIngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class OrigenIngresoService implements IOrigenIngresoService {
    @Autowired
    private IOrigenIngresoRepository rep;
    
    @Override
    public OrigenIngreso crear(OrigenIngreso origenIngreso) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_origen_ingreso);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	origenIngreso.setCodigo(codigo.get());
    	origenIngreso.setEstado(Constantes.activo);
    	return rep.save(origenIngreso);
    }

    @Override
    public OrigenIngreso actualizar(OrigenIngreso origenIngreso) {
        return rep.save(origenIngreso);
    }

    @Override
    public OrigenIngreso activar(OrigenIngreso origenIngreso) {
        origenIngreso.setEstado(Constantes.activo);
        return rep.save(origenIngreso);
    }

    @Override
    public OrigenIngreso inactivar(OrigenIngreso origenIngreso) {
        origenIngreso.setEstado(Constantes.inactivo);
        return rep.save(origenIngreso);
    }

    @Override
    public OrigenIngreso obtener(long id) {
        Optional<OrigenIngreso> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.origen_ingreso);
    }

    @Override
    public List<OrigenIngreso> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<OrigenIngreso> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }
    
    @Override
    public Page<OrigenIngreso> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<OrigenIngreso> buscar(OrigenIngreso origenIngreso) {
        return  rep.buscar(origenIngreso.getCodigo(), origenIngreso.getDescripcion(), origenIngreso.getAbreviatura());
    }

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<OrigenIngreso> origenesIngresos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal, 13);
            for (List<String> datos: info) {
                OrigenIngreso origenIngreso = new OrigenIngreso(datos);
                origenesIngresos.add(origenIngreso);
            }
            rep.saveAll(origenesIngresos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
