package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.TipoPago;
import com.proyecto.sicecuador.repositorios.cliente.ITipoPagoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ITipoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TipoPagoService implements ITipoPagoService {
    @Autowired
    private ITipoPagoRepository rep;
    
    @Override
    public TipoPago crear(TipoPago tipo_pago) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_pago);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipo_pago.setCodigo(codigo.get());
    	return rep.save(tipo_pago);
    }

    @Override
    public TipoPago actualizar(TipoPago tipo_pago) {
        return rep.save(tipo_pago);
    }

    @Override
    public TipoPago eliminar(TipoPago tipo_pago) {
        rep.deleteById(tipo_pago.getId());
        return tipo_pago;
    }

    @Override
    public Optional<TipoPago> obtener(TipoPago tipo_pago) {return rep.findById(tipo_pago.getId());
    }

    @Override
    public List<TipoPago> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<TipoPago> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<TipoPago> tipos_pagos=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,19);
            for (List<String> datos: info) {
                TipoPago tipo_pago = new TipoPago(datos);
                tipos_pagos.add(tipo_pago);
            }
            if(tipos_pagos.isEmpty()){
                return false;
            }
            rep.saveAll(tipos_pagos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
