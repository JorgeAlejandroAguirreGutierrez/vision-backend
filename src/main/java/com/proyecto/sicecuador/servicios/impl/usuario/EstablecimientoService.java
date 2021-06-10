package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.repositorios.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EstablecimientoService implements IEstablecimientoService {
    @Autowired
    private IEstablecimientoRepository rep;
    
    @Override
    public Establecimiento crear(Establecimiento establecimiento) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_establecimiento);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	establecimiento.setCodigo(codigo.get());
    	return rep.save(establecimiento);
    }

    @Override
    public Establecimiento actualizar(Establecimiento establecimiento) {
        return rep.save(establecimiento);
    }

    @Override
    public Establecimiento eliminar(Establecimiento establecimiento) {
        rep.deleteById(establecimiento.getId());
        return establecimiento;
    }

    @Override
    public Optional<Establecimiento> obtener(Establecimiento establecimiento) {
        return rep.findById(establecimiento.getId());
    }

    @Override
    public List<Establecimiento> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Establecimiento> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Establecimiento> establecimientos=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,0);
            for (List<String> datos: info) {
                Establecimiento establecimiento = new Establecimiento(datos);
                establecimientos.add(establecimiento);
            }
            if(establecimientos.isEmpty()){
                return false;
            }
            rep.saveAll(establecimientos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
