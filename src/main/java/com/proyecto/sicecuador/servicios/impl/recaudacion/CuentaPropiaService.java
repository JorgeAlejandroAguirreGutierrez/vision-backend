package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.repositorios.recaudacion.ICuentaPropiaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICuentaPropiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_cuenta_propia);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	cuenta_propia.setCodigo(codigo.get());
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
    public Page<CuentaPropia> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<CuentaPropia> cuentas_propias=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,3);
            for (List<String> datos: info) {
                CuentaPropia cuenta_propia = new CuentaPropia(datos);
                cuentas_propias.add(cuenta_propia);
            }
            if(cuentas_propias.isEmpty()){
                return false;
            }
            rep.saveAll(cuentas_propias);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
