package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IAmortizacionRepository;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IBancoRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IAmortizacionService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IBancoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Amortizacion> amortizaciones=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,0);
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
