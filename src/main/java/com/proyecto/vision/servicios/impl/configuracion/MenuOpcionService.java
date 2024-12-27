package com.proyecto.vision.servicios.impl.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.configuracion.MenuOpcion;
import com.proyecto.vision.repositorios.configuracion.IMenuOpcionRepository;
import com.proyecto.vision.servicios.interf.configuracion.IMenuOpcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuOpcionService implements IMenuOpcionService {
    @Autowired
    private IMenuOpcionRepository rep;

    @Override
    public void validar(MenuOpcion menuOpcion) {
        if(menuOpcion.getModulo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(menuOpcion.getOpcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(menuOpcion.getTabla().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tabla);
        if(menuOpcion.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public MenuOpcion crear(MenuOpcion opcion) {
        validar(opcion);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_menu_opcion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        opcion.setCodigo(codigo.get());
        opcion.setEstado(Constantes.estadoActivo);
    	return rep.save(opcion);
    }

    @Override
    public MenuOpcion actualizar(MenuOpcion opcion) {
        validar(opcion);
        return rep.save(opcion);
    }

    @Override
    public MenuOpcion activar(MenuOpcion opcion) {
        validar(opcion);
        opcion.setEstado(Constantes.estadoActivo);
        return rep.save(opcion);
    }

    @Override
    public MenuOpcion inactivar(MenuOpcion opcion) {
        validar(opcion);
        opcion.setEstado(Constantes.estadoInactivo);
        return rep.save(opcion);
    }

    @Override
    public MenuOpcion obtener(long id) {
        Optional<MenuOpcion> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.menu_opcion);
    }

    @Override
    public List<MenuOpcion> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<MenuOpcion> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<String> consultarModulos(){
        return rep.consultarModulos(Constantes.estadoActivo);
    }
    @Override
    public MenuOpcion obtenerPorOperacion(String operacion) {
        Optional<MenuOpcion> res= rep.obtenerPorOperacionYEstado(operacion, Constantes.estadoActivo);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.menu_opcion);
    }

    @Override
    public MenuOpcion obtenerPorOperacionTabla(MenuOpcion menuOpcion) {
        Optional<MenuOpcion> res= rep.obtenerPorTablaYOperacionYEstado(menuOpcion.getTabla(), menuOpcion.getOperacion(), Constantes.estadoActivo);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.menu_opcion);
    }

    @Override
    public List<MenuOpcion> consultarPorModulo(String modulo) {
        return rep.consultarPorModulo(modulo, Constantes.si ,Constantes.estadoActivo);
    }
    @Override
    public List<MenuOpcion> consultarPorOperacion(String operacion) {
        return rep.consultarOpciones(operacion, Constantes.estadoActivo);
    }
}
