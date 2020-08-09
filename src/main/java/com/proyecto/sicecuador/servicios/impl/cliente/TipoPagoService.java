package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.cliente.TipoContribuyente;
import com.proyecto.sicecuador.modelos.cliente.TipoPago;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITipoContribuyenteRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITipoPagoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ITipoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<TipoPago> tipos_pagos=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal);
            for (List<String> datos: info) {
                TipoPago tipo_pago = new TipoPago(datos);
                tipos_pagos.add(tipo_pago);
            }
            rep.saveAll(tipos_pagos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
