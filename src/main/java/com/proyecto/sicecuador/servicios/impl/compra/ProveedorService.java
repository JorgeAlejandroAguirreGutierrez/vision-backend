package com.proyecto.sicecuador.servicios.impl.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.modelos.cliente.Segmento;
import com.proyecto.sicecuador.modelos.compra.Proveedor;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.compra.IProveedorRepository;
import com.proyecto.sicecuador.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.sicecuador.servicios.interf.compra.IProveedorService;

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
    @Autowired
    private IUbicacionRepository repUbicacion;

    @Override
    public void validar(Proveedor proveedor) {
        if(proveedor.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(proveedor.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.razonSocial);
        if(proveedor.getNombreComercial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombreComercial);
        if(proveedor.getDireccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
        if(proveedor.getEspecial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.especial);
        if(proveedor.getObligadoContabilidad().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.obligadoContabilidad);
        if(proveedor.getFantasma().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.fantasma);
        if(proveedor.getTipoIdentificacion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipo_identificacion);
        if(proveedor.getGrupoProveedor().getId() == Constantes.cero) throw new DatoInvalidoException(Constantes.grupo_proveedor);
        if(proveedor.getFormaPago().getId() == Constantes.cero) throw new DatoInvalidoException(Constantes.forma_pago);
        if(proveedor.getUbicacion().getProvincia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.provincia);
        if(proveedor.getUbicacion().getCanton().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.canton);
        if(proveedor.getUbicacion().getParroquia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.parroquia);
        if(proveedor.getCorreosProveedor().isEmpty()) throw new DatoInvalidoException(Constantes.correo);
        if(proveedor.getPlazoCredito().getId() == 0 ) proveedor.setPlazoCredito(null);
    }
    
    @Override
    public Proveedor crear(Proveedor proveedor) {
        validar(proveedor);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_proveedor);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(proveedor.getUbicacion().getProvincia(),proveedor.getUbicacion().getCanton(), proveedor.getUbicacion().getParroquia(), Constantes.activo);
        if(ubicacion.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.ubicacion);
        }
        proveedor.setUbicacion(ubicacion.get());
    	proveedor.setCodigo(codigo.get());
    	proveedor.setEstado(Constantes.activo);
    	Proveedor res = rep.save(proveedor);
        res.normalizar();
        return res;
    }

    @Override
    public Proveedor actualizar(Proveedor proveedor) {
    	validar(proveedor);
        Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(proveedor.getUbicacion().getProvincia(),proveedor.getUbicacion().getCanton(), proveedor.getUbicacion().getParroquia(), Constantes.activo);
        if(ubicacion.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.ubicacion);
        }
        proveedor.setUbicacion(ubicacion.get());
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
        return rep.consultar();
    }

    @Override
    public List<Proveedor> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
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
}
