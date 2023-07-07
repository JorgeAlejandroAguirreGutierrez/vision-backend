package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.cliente.CalificacionCliente;
import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.modelos.cliente.GrupoCliente;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.repositorios.cliente.IGrupoClienteRepository;
import com.proyecto.vision.servicios.interf.cliente.IGrupoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GrupoClienteService implements IGrupoClienteService {
    @Autowired
    private IGrupoClienteRepository rep;

    @Override
    public void validar(GrupoCliente grupoCliente) {
        if(grupoCliente.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(grupoCliente.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
        if(grupoCliente.getCuentaContable().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cuenta_contable);
    }
    
    @Override
    public GrupoCliente crear(GrupoCliente grupoCliente) {
        validar(grupoCliente);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_grupo_cliente, grupoCliente.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	grupoCliente.setCodigo(codigo.get());
    	grupoCliente.setEstado(Constantes.activo);
    	GrupoCliente res = rep.save(grupoCliente);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoCliente actualizar(GrupoCliente grupoCliente) {
        validar(grupoCliente);
        GrupoCliente res = rep.save(grupoCliente);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoCliente activar(GrupoCliente grupoCliente) {
        validar(grupoCliente);
        grupoCliente.setEstado(Constantes.activo);
        GrupoCliente res = rep.save(grupoCliente);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoCliente inactivar(GrupoCliente grupoCliente) {
        validar(grupoCliente);
        grupoCliente.setEstado(Constantes.inactivo);
        GrupoCliente res = rep.save(grupoCliente);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoCliente obtener(long id) {
        Optional<GrupoCliente> grupoCliente = rep.findById(id);
        if(grupoCliente.isPresent()) {
        	GrupoCliente res = grupoCliente.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.grupo_cliente);
    }

    @Override
    public List<GrupoCliente> consultar() {
        return rep.consultar();
    }

    @Override
    public List<GrupoCliente> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<GrupoCliente> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<GrupoCliente> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<GrupoCliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<GrupoCliente> buscar(GrupoCliente grupoCliente) {
        return rep.buscar(grupoCliente.getCodigo(), grupoCliente.getDescripcion(), grupoCliente.getDescripcion());
    }
}
