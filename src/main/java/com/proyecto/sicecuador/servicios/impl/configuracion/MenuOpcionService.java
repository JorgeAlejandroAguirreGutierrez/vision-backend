package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.MenuOpcion;
import com.proyecto.sicecuador.repositorios.configuracion.IMenuOpcionRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IMenuOpcionService;
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
        opcion.setEstado(Constantes.activo);
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
        opcion.setEstado(Constantes.activo);
        return rep.save(opcion);
    }

    @Override
    public MenuOpcion inactivar(MenuOpcion opcion) {
        validar(opcion);
        opcion.setEstado(Constantes.inactivo);
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
    public Page<MenuOpcion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<String> consultarModulos(){
        return rep.consultarModulos(Constantes.activo);
    }
    @Override
    public MenuOpcion obtenerPorOperacion(String operacion) {
        Optional<MenuOpcion> res= rep.findByOperacion(operacion, Constantes.activo);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.menu_opcion);
    }

    @Override
    public MenuOpcion obtenerPorOperacionTabla(MenuOpcion menuOpcion) {
        Optional<MenuOpcion> res= rep.findByTablaAndOperacion(menuOpcion.getTabla(), menuOpcion.getOperacion(), Constantes.activo);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.menu_opcion);
    }

    @Override
    public List<MenuOpcion> consultarPorModulo(String modulo) {
        return rep.consultarPorModulo(modulo, Constantes.si ,Constantes.activo);
    }
    @Override
    public List<MenuOpcion> consultarPorOperacion(String operacion) {
        return rep.consultarOpciones(operacion, Constantes.activo);
    }
}
