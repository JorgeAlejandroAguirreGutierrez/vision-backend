package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.servicios.interf.comprobante.recaudacion.ICuentaPropiaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICuentaPropiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CuentaPropiaService implements ICuentaPropiaService {
    @Autowired
    private ICuentaPropiaRepository rep;

    @Override
    public void validar(CuentaPropia cuentaPropia) {
        if(cuentaPropia.getNumero().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero);
        if(cuentaPropia.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
    }
    
    @Override
    public CuentaPropia crear(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_cuenta_propia);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	cuentaPropia.setCodigo(codigo.get());
    	cuentaPropia.setEstado(Constantes.activo);
    	CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    @Override
    public CuentaPropia actualizar(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    @Override
    public CuentaPropia activar(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        cuentaPropia.setEstado(Constantes.activo);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    @Override
    public CuentaPropia inactivar(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        cuentaPropia.setEstado(Constantes.inactivo);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    @Override
    public CuentaPropia obtener(long id) {
        Optional<CuentaPropia> cuentaPropia= rep.findById(id);
        if(cuentaPropia.isPresent()) {
        	CuentaPropia res = cuentaPropia.get();
            res.normalizar();
            return res;
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
}
