package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.repositorios.recaudacion.IRangoCrediticioRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRangoCrediticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class RangoCrediticioService implements IRangoCrediticioService {
    @Autowired
    private IRangoCrediticioRepository rep;
    
    @Override
    public RangoCrediticio crear(RangoCrediticio rango_crediticio) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_rango_crediticio);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	rango_crediticio.setCodigo(codigo.get());
    	return rep.save(rango_crediticio);
    }

    @Override
    public RangoCrediticio actualizar(RangoCrediticio rangoCrediticio) {
        return rep.save(rangoCrediticio);
    }

    @Override
    public RangoCrediticio obtener(long id) {
        Optional<RangoCrediticio> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.rango_crediticio);
    }

    @Override
    public List<RangoCrediticio> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<RangoCrediticio> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<RangoCrediticio> rangos_crediticios=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,6);
            for (List<String> datos: info) {
                RangoCrediticio rango_crediticio = new RangoCrediticio(datos);
                rangos_crediticios.add(rango_crediticio);
            }
            rep.saveAll(rangos_crediticios);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<RangoCrediticio> obtenerSaldo(double saldo) {
        return rep.findSaldoRangoCrediticio(saldo);
    }

}
