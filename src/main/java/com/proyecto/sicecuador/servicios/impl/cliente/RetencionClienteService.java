package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.RetencionCliente;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.cliente.IRetencionClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IRetencionClienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public RetencionCliente crear(RetencionCliente retencion_cliente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_retencion_cliente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	retencion_cliente.setCodigo(codigo.get());
    	return rep.save(retencion_cliente);
    }

    @Override
    public RetencionCliente actualizar(RetencionCliente retencion_cliente) {
        return rep.save(retencion_cliente);
    }

    @Override
    public RetencionCliente eliminar(RetencionCliente retencion_cliente) {
        rep.deleteById(retencion_cliente.getId());
        return retencion_cliente;
    }

    @Override
    public Optional<RetencionCliente> obtener(RetencionCliente retencion_cliente) {
        return rep.findById(retencion_cliente.getId());
    }

    @Override
    public List<RetencionCliente> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<RetencionCliente> retenciones_clientes=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal, 15);
            for (List<String> datos: info) {
                RetencionCliente retencion_cliente = new RetencionCliente(datos);
                Optional<Cliente> cliente=rep_cliente.findById(retencion_cliente.getCliente().getId());
                if(cliente.isPresent()){
                    retenciones_clientes.add(retencion_cliente);
                }
            }
            if(retenciones_clientes.isEmpty()){
                return false;
            }
            rep.saveAll(retenciones_clientes);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
