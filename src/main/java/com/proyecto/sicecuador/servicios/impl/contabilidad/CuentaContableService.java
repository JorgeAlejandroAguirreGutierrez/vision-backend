package com.proyecto.sicecuador.servicios.impl.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
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
    public CuentaContable crear(CuentaContable cuentaContable) {
    	//proveedor.normalizar();
/*    	Optional<Proveedor> getProveedor=rep.obtenerPorRazonSocial(proveedor.getRazonSocial());
    	if(getProveedor.isPresent()) {
    		throw new ModeloExistenteException();
    	}*/
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_cuenta_contable);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        cuentaContable.setCodigo(codigo.get());
    	return rep.save(cuentaContable);
    }

    @Override
    public CuentaContable actualizar(CuentaContable cuentaContable) {
    	//proveedor.normalizar();
    	return rep.save(cuentaContable);
    }

    @Override
    public CuentaContable eliminar(CuentaContable cuentaContable) {
        rep.deleteById(cuentaContable.getId());
        return cuentaContable;
    }

    @Override
    public Optional<CuentaContable> obtener(CuentaContable cuentaContable) {
        return rep.findById(cuentaContable.getId());
    }

    @Override
    public List<CuentaContable> consultar() {
        return rep.findAll();
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
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<CuentaContable> cuentas_contables=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,7);
            for (List<String> datos: info) {
                CuentaContable cuentaContable = new CuentaContable(datos);
                cuentas_contables.add(cuentaContable);
            }
            if(cuentas_contables.isEmpty()){
                return false;
            }
            rep.saveAll(cuentas_contables);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
