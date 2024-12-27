package com.proyecto.vision.servicios.impl.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.configuracion.Ubicacion;
import com.proyecto.vision.modelos.acceso.CorreoEstablecimiento;
import com.proyecto.vision.modelos.acceso.Establecimiento;
import com.proyecto.vision.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.vision.repositorios.acceso.IEstablecimientoRepository;
import com.proyecto.vision.servicios.interf.acceso.IEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    }
    
    @Override
    public Establecimiento crear(Establecimiento establecimiento) {
        validar(establecimiento);
        Optional<Establecimiento> establecimientoExiste = rep.ObtenerPorEmpresaYCodigoSRI(establecimiento.getEmpresa().getId(), establecimiento.getCodigoSRI());
        if(establecimientoExiste.isPresent()){
            throw new EntidadExistenteException(Constantes.establecimiento);
        }
    	Optional<Ubicacion> ubicacion = repUbicacion.obtenerPorProvinciaYCantonYParroquia(establecimiento.getUbicacion().getProvincia(),establecimiento.getUbicacion().getCanton(), establecimiento.getUbicacion().getParroquia(), Constantes.estadoActivo);
    	if(ubicacion.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.ubicacion);
    	}
    	Optional<String>codigo = Util.generarCodigoPorEmpresa(null, Constantes.tabla_establecimiento, establecimiento.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        if(establecimiento.getCorreosEstablecimiento().isEmpty()){
            List<CorreoEstablecimiento> correosEstablecimiento = new ArrayList<>();
            correosEstablecimiento.add(new CorreoEstablecimiento("", Constantes.correo_predeterminado, establecimiento));
            establecimiento.setCorreosEstablecimiento(correosEstablecimiento);
        }
    	establecimiento.setCodigo(codigo.get());
    	establecimiento.setUbicacion(ubicacion.get());
    	establecimiento.setEstado(Constantes.estadoActivo);
    	Establecimiento res = rep.save(establecimiento);
        res.normalizar();
        return res;
    }

    @Override
    public Establecimiento actualizar(Establecimiento establecimiento) {
        validar(establecimiento);
        Optional<Ubicacion> ubicacion = repUbicacion.obtenerPorProvinciaYCantonYParroquia(establecimiento.getUbicacion().getProvincia(),establecimiento.getUbicacion().getCanton(), establecimiento.getUbicacion().getParroquia(), Constantes.estadoActivo);
        if(ubicacion.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.ubicacion);
        }
        if(establecimiento.getCorreosEstablecimiento().isEmpty()){
            List<CorreoEstablecimiento> correosEstablecimiento = new ArrayList<>();
            correosEstablecimiento.add(new CorreoEstablecimiento("", Constantes.correo_predeterminado, establecimiento));
            establecimiento.setCorreosEstablecimiento(correosEstablecimiento);
        }
        Establecimiento res = rep.save(establecimiento);
        res.normalizar();
        return res;
    }

    @Override
    public Establecimiento activar(Establecimiento establecimiento) {
        validar(establecimiento);
        establecimiento.setEstado(Constantes.estadoActivo);
        Establecimiento res = rep.save(establecimiento);
        res.normalizar();
        return res;
    }

    @Override
    public Establecimiento inactivar(Establecimiento establecimiento) {
        validar(establecimiento);
        establecimiento.setEstado(Constantes.estadoInactivo);
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
    public List<Establecimiento> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(Constantes.estadoActivo);
    }

    @Override
    public List<Establecimiento> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Establecimiento> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

}
