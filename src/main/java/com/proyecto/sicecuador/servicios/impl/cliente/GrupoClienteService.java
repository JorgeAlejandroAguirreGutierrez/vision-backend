package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IGrupoClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IGrupoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class GrupoClienteService implements IGrupoClienteService {
    @Autowired
    private IGrupoClienteRepository rep;
    
    @Override
    public GrupoCliente crear(GrupoCliente grupoCliente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_grupo_cliente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	grupoCliente.setCodigo(codigo.get());
    	return rep.save(grupoCliente);
    }

    @Override
    public GrupoCliente actualizar(GrupoCliente grupoCliente) {
        return rep.save(grupoCliente);
    }

    @Override
    public GrupoCliente activar(GrupoCliente grupoCliente) {
        grupoCliente.setEstado(Constantes.activo);
        return rep.save(grupoCliente);   
    }

    @Override
    public GrupoCliente inactivar(GrupoCliente grupoCliente) {
        grupoCliente.setEstado(Constantes.inactivo);
        return rep.save(grupoCliente);
    }

    @Override
    public GrupoCliente obtener(long id) {
        Optional<GrupoCliente> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.grupo_cliente);
    }

    @Override
    public List<GrupoCliente> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<GrupoCliente> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<GrupoCliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<GrupoCliente> buscar(GrupoCliente grupoCliente) {
        return  rep.buscar(grupoCliente.getCodigo(), grupoCliente.getDescripcion(), grupoCliente.getDescripcion());
    }

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<GrupoCliente> gruposClientes=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal, 12);
            for (List<String> datos: info) {
                GrupoCliente grupoCliente = new GrupoCliente(datos);
                gruposClientes.add(grupoCliente);
            }
            rep.saveAll(gruposClientes);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
