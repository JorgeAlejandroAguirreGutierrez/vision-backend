package com.proyecto.sicecuador.servicios.impl.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.repositorios.contabilidad.ICuentaContableRepository;
import com.proyecto.sicecuador.servicios.interf.contabilidad.ICuentaContableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaContableService implements ICuentaContableService {
    @Autowired
    private ICuentaContableRepository rep;

    @Override
    public void validar(CuentaContable cuentaContable) {
        if(cuentaContable.getCuenta().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.cuenta);
        if(cuentaContable.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(cuentaContable.getClasificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.clasificacion);
        if(cuentaContable.getNivel() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.nivel);
        if(cuentaContable.getFe().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.fe);
        if(cuentaContable.getCasillero().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.casillero);
        if(cuentaContable.getMapeo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.mapeo);
    }
    
    @Override
    public CuentaContable crear(CuentaContable cuentaContable) {
        validar(cuentaContable);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_cuenta_contable);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        cuentaContable.setCodigo(codigo.get());
        cuentaContable.setEstado(Constantes.activo);
    	return rep.save(cuentaContable);
    }

    @Override
    public CuentaContable actualizar(CuentaContable cuentaContable) {
    	validar(cuentaContable);
        return rep.save(cuentaContable);
    }

    @Override
    public CuentaContable activar(CuentaContable cuentaContable) {
        validar(cuentaContable);
        cuentaContable.setEstado(Constantes.activo);
        return rep.save(cuentaContable);
    }

    @Override
    public CuentaContable inactivar(CuentaContable cuentaContable) {
        validar(cuentaContable);
        cuentaContable.setEstado(Constantes.inactivo);
        return rep.save(cuentaContable);
    }

    @Override
    public CuentaContable obtener(long id) {
        Optional<CuentaContable> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.cuenta_contable);
    }

    @Override
    public List<CuentaContable> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<CuentaContable> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<CuentaContable> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }


    @Override
    public List<CuentaContable> buscar(CuentaContable cuentaContable) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!cuentaContable.getDescripcion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("descripcion"), "%"+cuentaContable.getDescripcion()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
    
    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<CuentaContable> cuentasContables=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,7);
            for (List<String> datos: info) {
                CuentaContable cuentaContable = new CuentaContable(datos);
                cuentasContables.add(cuentaContable);
            }
            rep.saveAll(cuentasContables);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
