package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.DatoInvalidoException;
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

    /**
     * Metodo que permite la validacion de las propiedades del objeto
     * @param grupoCliente
     */
    @Override
    public void validar(GrupoCliente grupoCliente) {
        if(grupoCliente.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(grupoCliente.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
        if(grupoCliente.getCuentaContable().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cuenta_contable);
    }

    /**
     * Metodo que permite la creacion del objeto
     * @param grupoCliente
     * @return el objeto creado
     */
    @Override
    public GrupoCliente crear(GrupoCliente grupoCliente) {
        validar(grupoCliente);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(null, Constantes.tabla_grupo_cliente, grupoCliente.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	grupoCliente.setCodigo(codigo.get());
    	grupoCliente.setEstado(Constantes.estadoActivo);
    	GrupoCliente res = rep.save(grupoCliente);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite la actualizacion del objeto
     * @param grupoCliente
     * @return el objeto actualizado
     */
    @Override
    public GrupoCliente actualizar(GrupoCliente grupoCliente) {
        validar(grupoCliente);
        GrupoCliente res = rep.save(grupoCliente);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite poner la propiedad estado en activo
     * @param grupoCliente
     * @return el objeto con la propiedad estado en activo
     */
    @Override
    public GrupoCliente activar(GrupoCliente grupoCliente) {
        validar(grupoCliente);
        grupoCliente.setEstado(Constantes.estadoActivo);
        GrupoCliente res = rep.save(grupoCliente);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite poner la propiedad estado en inactivo
     * @param grupoCliente
     * @return el objeto con la propiedad estado en inactivo
     */
    @Override
    public GrupoCliente inactivar(GrupoCliente grupoCliente) {
        validar(grupoCliente);
        grupoCliente.setEstado(Constantes.estadoInactivo);
        GrupoCliente res = rep.save(grupoCliente);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite obtener un objeto a traves del id
     * @param id
     * @return el objeto
     */
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

    /**
     * Metodo que permite consultar todos los objetos
     * @return lista de todos los objetos
     */
    @Override
    public List<GrupoCliente> consultar() {
        return rep.consultar();
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por la empresa
     * @param empresaId
     * @return lista de los objetos filtrados por la empresa
     */
    @Override
    public List<GrupoCliente> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por el estado
     * @param estado
     * @return lista de los objetos filtrados por el estado
     */
    @Override
    public List<GrupoCliente> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por la empresa y el estado
     * @param empresaId
     * @param estado
     * @return lista de los objetos filtrados por la empresa y el estado
     */
    @Override
    public List<GrupoCliente> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    /**
     * Metodo que permite consultar los objetos por pagina
     * @param pageable
     * @return lista de tipo Page con los objetos
     */
    @Override
    public Page<GrupoCliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
