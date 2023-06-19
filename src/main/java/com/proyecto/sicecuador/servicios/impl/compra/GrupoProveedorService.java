package com.proyecto.sicecuador.servicios.impl.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.compra.GrupoProveedor;
import com.proyecto.sicecuador.repositorios.compra.IGrupoProveedorRepository;
import com.proyecto.sicecuador.servicios.interf.compra.IGrupoProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoProveedorService implements IGrupoProveedorService {
    @Autowired
    private IGrupoProveedorRepository rep;

    @Override
    public void validar(GrupoProveedor grupoProveedor) {
        if(grupoProveedor.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(grupoProveedor.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
        if(grupoProveedor.getCuentaContable().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cuenta_contable);
    }
    
    @Override
    public GrupoProveedor crear(GrupoProveedor grupoProveedor) {
        validar(grupoProveedor);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_grupo_proveedor, grupoProveedor.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        grupoProveedor.setCodigo(codigo.get());
        grupoProveedor.setEstado(Constantes.activo);
    	GrupoProveedor res = rep.save(grupoProveedor);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoProveedor actualizar(GrupoProveedor grupoProveedor) {
        validar(grupoProveedor);
        GrupoProveedor res = rep.save(grupoProveedor);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoProveedor activar(GrupoProveedor grupoProveedor) {
        validar(grupoProveedor);
        grupoProveedor.setEstado(Constantes.activo);
        GrupoProveedor res = rep.save(grupoProveedor);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoProveedor inactivar(GrupoProveedor grupoProveedor) {
        validar(grupoProveedor);
        grupoProveedor.setEstado(Constantes.inactivo);
        GrupoProveedor res = rep.save(grupoProveedor);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoProveedor obtener(long id) {
        Optional<GrupoProveedor> grupoProveedor = rep.findById(id);
        if(grupoProveedor.isPresent()) {
            GrupoProveedor res = grupoProveedor.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.grupo_cliente);
    }

    @Override
    public List<GrupoProveedor> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<GrupoProveedor> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<GrupoProveedor> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<GrupoProveedor> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<GrupoProveedor> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public List<GrupoProveedor> buscar(GrupoProveedor grupoProveedor) {
        return rep.buscar(grupoProveedor.getCodigo(), grupoProveedor.getDescripcion(), grupoProveedor.getDescripcion());
    }
}
