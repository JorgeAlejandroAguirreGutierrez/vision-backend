package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.recaudacion.Credito;
import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.ICuentaPropiaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICuentaPropiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CuentaPropiaService implements ICuentaPropiaService {
    @Autowired
    private ICuentaPropiaRepository rep;
    @Override
    public CuentaPropia crear(CuentaPropia cuenta_propia) {
        return rep.save(cuenta_propia);
    }

    @Override
    public CuentaPropia actualizar(CuentaPropia cuenta_propia) {
        return rep.save(cuenta_propia);
    }

    @Override
    public CuentaPropia eliminar(CuentaPropia cuenta_propia) {
        rep.deleteById(cuenta_propia.getId());
        return cuenta_propia;
    }

    @Override
    public Optional<CuentaPropia> obtener(CuentaPropia cuenta_propia) {
        return rep.findById(cuenta_propia.getId());
    }

    @Override
    public List<CuentaPropia> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<CuentaPropia> cuentas_propias=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal);
            for (List<String> datos: info) {
                CuentaPropia cuenta_propia = new CuentaPropia(datos);
                cuentas_propias.add(cuenta_propia);
            }
            rep.saveAll(cuentas_propias);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
