package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.repositorios.configuracion.IEmpresaRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements IEmpresaService {
    @Autowired
    private IEmpresaRepository rep;

    @Override
    public void validar(Empresa empresa) {
        if(empresa.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(empresa.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.razonSocial);
        if(empresa.getNombreComercial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombreComercial);
        if(empresa.getLogo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.logo);
        if(empresa.getObligadoContabilidad().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.obligadoContabilidad);
        if(empresa.getDireccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
        if(empresa.getTipoIdentificacion().getId() ==  Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipo_identificacion);
        if(empresa.getUbicacion().getId() ==  Constantes.ceroId) throw new DatoInvalidoException(Constantes.ubicacion);
    }

    @Override
    public Empresa crear(Empresa empresa) {
        validar(empresa);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_empresa);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	empresa.setCodigo(codigo.get());
    	empresa.setEstado(Constantes.activo);
    	Empresa res = rep.save(empresa);
        res.normalizar();
        return res;
    }

    @Override
    public Empresa actualizar(Empresa empresa) {
        validar(empresa);
        Empresa res = rep.save(empresa);
        res.normalizar();
        return res;
    }

    @Override
    public Empresa activar(Empresa empresa) {
        validar(empresa);
        empresa.setEstado(Constantes.activo);
        Empresa res = rep.save(empresa);
        res.normalizar();
        return res;
    }

    @Override
    public Empresa inactivar(Empresa empresa) {
        validar(empresa);
        empresa.setEstado(Constantes.inactivo);
        Empresa res = rep.save(empresa);
        res.normalizar();
        return res;
    }

    @Override
    public Empresa obtener(long id) {
        Optional<Empresa> empresa = rep.findById(id);
        if(empresa.isPresent()) {
        	Empresa res = empresa.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.empresa);
    }

    @Override
    public List<Empresa> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Empresa> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Empresa> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
