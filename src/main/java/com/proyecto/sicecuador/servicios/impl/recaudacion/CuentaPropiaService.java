package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
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
    public CuentaPropia crear(CuentaPropia cuentaPropia) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_cuenta_propia);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	cuentaPropia.setCodigo(codigo.get());
    	cuentaPropia.setEstado(Constantes.activo);
    	return rep.save(cuentaPropia);
    }

    @Override
    public CuentaPropia actualizar(CuentaPropia cuentaPropia) {
        return rep.save(cuentaPropia);
    }

    @Override
    public CuentaPropia activar(CuentaPropia cuentaPropia) {
        cuentaPropia.setEstado(Constantes.activo);
        return rep.save(cuentaPropia);
    }

    @Override
    public CuentaPropia inactivar(CuentaPropia cuentaPropia) {
        cuentaPropia.setEstado(Constantes.inactivo);
        return rep.save(cuentaPropia);
    }

    @Override
    public CuentaPropia obtener(long id) {
        Optional<CuentaPropia> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.cuenta_propia);
    }

    @Override
    public List<CuentaPropia> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<CuentaPropia> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<CuentaPropia> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<CuentaPropia> cuentas_propias=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,3);
            for (List<String> datos: info) {
                CuentaPropia cuenta_propia = new CuentaPropia(datos);
                cuentas_propias.add(cuenta_propia);
            }
            rep.saveAll(cuentas_propias);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
