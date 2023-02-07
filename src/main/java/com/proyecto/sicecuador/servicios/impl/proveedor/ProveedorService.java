package com.proyecto.sicecuador.servicios.impl.proveedor;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.modelos.proveedor.Proveedor;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.proveedor.IProveedorRepository;
import com.proyecto.sicecuador.servicios.interf.proveedor.IProveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements IProveedorService {
    @Autowired
    private IProveedorRepository rep;

    @Override
    public void validar(Proveedor proveedor) {
        if(proveedor.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(proveedor.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.razonSocial);
        if(proveedor.getNombreComercial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombreComercial);
        if(proveedor.getDireccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
        if(proveedor.getEspecial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.telefono);
        if(proveedor.getObligadoContabilidad().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.celular);
        if(proveedor.getFantasma().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.correo);
        if(proveedor.getTipoIdentificacion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipo_identificacion);
    }
    
    @Override
    public Proveedor crear(Proveedor proveedor) {
        validar(proveedor);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_proveedor);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	proveedor.setCodigo(codigo.get());
    	proveedor.setEstado(Constantes.activo);
    	Proveedor res = rep.save(proveedor);
        res.normalizar();
        return res;
    }

    @Override
    public Proveedor actualizar(Proveedor proveedor) {
    	validar(proveedor);
        Proveedor res = rep.save(proveedor);
        res.normalizar();
        return res;
    }

    @Override
    public Proveedor activar(Proveedor proveedor) {
        validar(proveedor);
        proveedor.setEstado(Constantes.activo);
        Proveedor res = rep.save(proveedor);
        res.normalizar();
        return res;
    }

    @Override
    public Proveedor inactivar(Proveedor proveedor) {
        validar(proveedor);
        proveedor.setEstado(Constantes.inactivo);
        Proveedor res = rep.save(proveedor);
        res.normalizar();
        return res;
    }

    @Override
    public Proveedor obtener(long id) {
        Optional<Proveedor> proveedor= rep.findById(id);
        if(proveedor.isPresent()) {
        	Proveedor res = proveedor.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.proveedor);
    }

    @Override
    public List<Proveedor> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Proveedor> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Proveedor> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }


    @Override
    public List<Proveedor> buscar(Proveedor proveedor) {
        return  rep.consultarPorRazonSocial(proveedor.getRazonSocial(), Constantes.activo);
    }
    
    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Proveedor> proveedores=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,7);
            for (List<String> datos: info) {
                Proveedor proveedor = new Proveedor(datos);
                proveedores.add(proveedor);
            }
            rep.saveAll(proveedores);
        }catch (Exception e){
        	System.err.println(e.getMessage());
        }
    }

}
