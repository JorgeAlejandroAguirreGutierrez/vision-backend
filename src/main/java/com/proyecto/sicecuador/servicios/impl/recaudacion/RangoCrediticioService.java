package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
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
    public RangoCrediticio actualizar(RangoCrediticio rango_crediticio) {
        return rep.save(rango_crediticio);
    }

    @Override
    public RangoCrediticio eliminar(RangoCrediticio rango_crediticio) {
        rep.deleteById(rango_crediticio.getId());
        return rango_crediticio;
    }

    @Override
    public Optional<RangoCrediticio> obtener(RangoCrediticio rango_crediticio) {
        return rep.findById(rango_crediticio.getId());
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
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<RangoCrediticio> rangos_crediticios=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,6);
            for (List<String> datos: info) {
                RangoCrediticio rango_crediticio = new RangoCrediticio(datos);
                rangos_crediticios.add(rango_crediticio);
            }
            if(rangos_crediticios.isEmpty()){
                return false;
            }
            rep.saveAll(rangos_crediticios);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Optional<RangoCrediticio> obtenerSaldo(double saldo) {
        return rep.findSaldoRangoCrediticio(saldo);
    }

}
