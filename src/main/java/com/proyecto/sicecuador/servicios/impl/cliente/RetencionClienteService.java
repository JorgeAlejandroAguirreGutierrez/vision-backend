package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.RetencionCliente;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.cliente.IRetencionClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IRetencionClienteService;
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
    public RetencionCliente crear(RetencionCliente retencionCliente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_retencion_cliente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	retencionCliente.setCodigo(codigo.get());
    	return rep.save(retencionCliente);
    }

    @Override
    public RetencionCliente actualizar(RetencionCliente retencionCliente) {
        return rep.save(retencionCliente);
    }

    @Override
    public RetencionCliente obtener(long id) {
        Optional<RetencionCliente> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
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

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<RetencionCliente> retencionesClientes=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal, 15);
            for (List<String> datos: info) {
                RetencionCliente retencionCliente = new RetencionCliente(datos);
                Optional<Cliente> cliente=rep_cliente.findById(retencionCliente.getCliente().getId());
                if(cliente.isPresent()){
                	retencionesClientes.add(retencionCliente);
                }
            }
            rep.saveAll(retencionesClientes);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
