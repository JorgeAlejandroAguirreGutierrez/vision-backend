package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.modelos.cliente.PlazoCredito;
import com.proyecto.vision.modelos.cliente.RetencionCliente;
import com.proyecto.vision.repositorios.cliente.IClienteRepository;
import com.proyecto.vision.repositorios.cliente.IRetencionClienteRepository;
import com.proyecto.vision.servicios.interf.cliente.IRetencionClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class RetencionClienteService implements IRetencionClienteService {
    @Autowired
    private IRetencionClienteRepository rep;
    @Autowired
    private IClienteRepository rep_cliente;

    @Override
    public void validar(RetencionCliente retencionCliente) {
        if(retencionCliente.getTipoRetencion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipoRetencion);
    }
    
    @Override
    public RetencionCliente crear(RetencionCliente retencionCliente) {
        validar(retencionCliente);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_retencion_cliente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	retencionCliente.setCodigo(codigo.get());
    	RetencionCliente res = rep.save(retencionCliente);
        res.normalizar();
        return res;
    }

    @Override
    public RetencionCliente actualizar(RetencionCliente retencionCliente) {
        validar(retencionCliente);
        RetencionCliente res = rep.save(retencionCliente);
        res.normalizar();
        return res;
    }

    @Override
    public RetencionCliente obtener(long id) {
        Optional<RetencionCliente> retencionCliente= rep.findById(id);
        if(retencionCliente.isPresent()) {
        	RetencionCliente res = retencionCliente.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.retencion_cliente);
    }

    @Override
    public List<RetencionCliente> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<RetencionCliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}