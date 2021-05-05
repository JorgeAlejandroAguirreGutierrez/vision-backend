package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IAmortizacionRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IAmortizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AmortizacionService implements IAmortizacionService {
    @Autowired
    private IAmortizacionRepository rep;
    
    @Override
    public Amortizacion crear(Amortizacion amortizacion) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_amortizacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	amortizacion.setCodigo(codigo.get());
    	return rep.save(amortizacion);
    }

    @Override
    public Amortizacion actualizar(Amortizacion amortizacion) {
        return rep.save(amortizacion);
    }

    @Override
    public Amortizacion eliminar(Amortizacion amortizacion) {
        rep.deleteById(amortizacion.getId());
        return amortizacion;
    }

    @Override
    public Optional<Amortizacion> obtener(Amortizacion banco) {
        return rep.findById(banco.getId());
    }

    @Override
    public List<Amortizacion> consultar() {
        return rep.findAll();
    }
    
    @Override
    public Page<Amortizacion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }


    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Amortizacion> amortizaciones=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,0);
            for (List<String> datos: info) {
                Amortizacion amortizacion = new Amortizacion(datos);
                amortizaciones.add(amortizacion);
            }
            if(amortizaciones.isEmpty()){
                return false;
            }
            rep.saveAll(amortizaciones);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
