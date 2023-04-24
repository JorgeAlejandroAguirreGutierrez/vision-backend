package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.sicecuador.repositorios.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class EstablecimientoService implements IEstablecimientoService {
    @Autowired
    private IEstablecimientoRepository rep;
    @Autowired
    private IUbicacionRepository repUbicacion;

    @Override
    public void validar(Establecimiento establecimiento) {
        if(establecimiento.getCodigoSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.codigoSRI);
        if(establecimiento.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(establecimiento.getDireccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
        if(establecimiento.getRegimen().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.regimen);
        if(establecimiento.getUbicacion().getProvincia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.provincia);
        if(establecimiento.getUbicacion().getCanton().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.canton);
        if(establecimiento.getUbicacion().getParroquia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.parroquia);
        if(establecimiento.getEmpresa().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
        if(establecimiento.getTelefonos().isEmpty()) throw new DatoInvalidoException(Constantes.telefono);
        if(establecimiento.getCelulares().isEmpty()) throw new DatoInvalidoException(Constantes.celular);
        if(establecimiento.getCorreos().isEmpty()) throw new DatoInvalidoException(Constantes.correo);
    }
    
    @Override
    public Establecimiento crear(Establecimiento establecimiento) {
        validar(establecimiento);
    	Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(establecimiento.getUbicacion().getProvincia(),establecimiento.getUbicacion().getCanton(), establecimiento.getUbicacion().getParroquia(), Constantes.activo);
    	if(ubicacion.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.ubicacion);
    	}
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_establecimiento);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	establecimiento.setCodigo(codigo.get());
    	establecimiento.setUbicacion(ubicacion.get());
    	establecimiento.setEstado(Constantes.activo);
    	Establecimiento res = rep.save(establecimiento);
        res.normalizar();
        return res;
    }

    @Override
    public Establecimiento actualizar(Establecimiento establecimiento) {
        validar(establecimiento);
        Establecimiento res = rep.save(establecimiento);
        res.normalizar();
        return res;
    }

    @Override
    public Establecimiento activar(Establecimiento establecimiento) {
        validar(establecimiento);
        establecimiento.setEstado(Constantes.activo);
        Establecimiento res = rep.save(establecimiento);
        res.normalizar();
        return res;
    }

    @Override
    public Establecimiento inactivar(Establecimiento establecimiento) {
        validar(establecimiento);
        establecimiento.setEstado(Constantes.inactivo);
        Establecimiento res = rep.save(establecimiento);
        res.normalizar();
        return res;
    }

    @Override
    public Establecimiento obtener(long id) {
        Optional<Establecimiento> establecimiento = rep.findById(id);
        if(establecimiento.isPresent()) {
        	Establecimiento res = establecimiento.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.establecimiento);
    }

    @Override
    public List<Establecimiento> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Establecimiento> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }
    
    @Override
    public List<Establecimiento> consultarPorEmpresa(long empresaId){
    	return rep.consultarPorEmpresa(empresaId, Constantes.activo);
    }

    @Override
    public Page<Establecimiento> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
