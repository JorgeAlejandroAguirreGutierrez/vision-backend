package com.proyecto.vision.servicios.impl.entrega;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.modelos.entrega.Transportista;
import com.proyecto.vision.repositorios.entrega.ITransportistaRepository;
import com.proyecto.vision.servicios.interf.entrega.ITransportistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TransportistaService implements ITransportistaService {
    @Autowired
    private ITransportistaRepository rep;

    @Override
    public void validar(Transportista transportista) {
        if (transportista.getTipoIdentificacion() == null)
            throw new DatoInvalidoException(Constantes.tipo_identificacion);
        if (transportista.getIdentificacion().equals(Constantes.vacio))
            throw new DatoInvalidoException(Constantes.identificacion);
    }
    
    @Override
    public Transportista crear(Transportista transportista) {
        validar(transportista);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(null, Constantes.tabla_transportista, transportista.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	transportista.setCodigo(codigo.get());
    	transportista.setEstado(Constantes.estadoActivo);
    	return rep.save(transportista);
    }

    @Override
    public Transportista actualizar(Transportista transportista) {
        return rep.save(transportista);
    }

    @Override
    public Transportista activar(Transportista transportista) {
        transportista.setEstado(Constantes.estadoActivo);
        return rep.save(transportista);
    }

    @Override
    public Transportista inactivar(Transportista transportista) {
        transportista.setEstado(Constantes.estadoInactivo);
        return rep.save(transportista);
    }

    @Override
    public Transportista obtener(long id) {
        Optional<Transportista> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.transportista);
        
    }

    @Override
    public List<Transportista> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Transportista> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Transportista> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Transportista> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }
}
