package com.proyecto.sicecuador.servicios.impl.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.modelos.contabilidad.MovimientoContable;
import com.proyecto.sicecuador.repositorios.contabilidad.IMovimientoContableRepository;
import com.proyecto.sicecuador.servicios.interf.contabilidad.IMovimientoContableService;
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
public class MovimientoContableService implements IMovimientoContableService {
    @Autowired
    private IMovimientoContableRepository rep;

    @Override
    public void validar(MovimientoContable movimientoContable) {
        if(movimientoContable.getAfectacionContable().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.afectacion_contable);
        if(movimientoContable.getInventario().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.inventario);
        if(movimientoContable.getCostoVenta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.costoVenta);
        if(movimientoContable.getDevolucionCompra().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.devolucionCompra);
        if(movimientoContable.getDescuentoCompra().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.descuentoCompra);
        if(movimientoContable.getVenta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.venta);
        if(movimientoContable.getDevolucionVenta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.devolucionVenta);
        if(movimientoContable.getDescuentoVenta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.descuentoVenta);
        if(movimientoContable.getDevolucionCostoVenta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.devolucionCostoVenta);
    }
    
    @Override
    public MovimientoContable crear(MovimientoContable movimientoContable) {
        validar(movimientoContable);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_movimiento_contable);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	movimientoContable.setCodigo(codigo.get());
    	movimientoContable.setEstado(Constantes.activo);
    	MovimientoContable res = rep.save(movimientoContable);
        res.normalizar();
        return res;
    }

    @Override
    public MovimientoContable actualizar(MovimientoContable movimientoContable) {
    	validar(movimientoContable);
        MovimientoContable res = rep.save(movimientoContable);
        res.normalizar();
        return res;
    }

    @Override
    public MovimientoContable activar(MovimientoContable movimientoContable) {
        validar(movimientoContable);
        movimientoContable.setEstado(Constantes.activo);
        MovimientoContable res = rep.save(movimientoContable);
        res.normalizar();
        return res;
    }

    @Override
    public MovimientoContable inactivar(MovimientoContable movimientoContable) {
        validar(movimientoContable);
        movimientoContable.setEstado(Constantes.inactivo);
        MovimientoContable res = rep.save(movimientoContable);
        res.normalizar();
        return res;
    }

    @Override
    public MovimientoContable obtener(long id) {
        Optional<MovimientoContable> movimientoContable = rep.findById(id);
        if(movimientoContable.isEmpty()) {
        	MovimientoContable res = movimientoContable.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.movimiento_contable);
    }

    @Override
    public List<MovimientoContable> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<MovimientoContable> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }


    @Override
    public List<MovimientoContable> buscar(MovimientoContable movimientoContable) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!movimientoContable.getCodigo().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+movimientoContable.getCodigo()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
    
    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<MovimientoContable> movimientosContables=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal,7);
            for (List<String> datos: info) {
                MovimientoContable movimientoContable = new MovimientoContable(datos);
                movimientosContables.add(movimientoContable);
            }
            rep.saveAll(movimientosContables);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
