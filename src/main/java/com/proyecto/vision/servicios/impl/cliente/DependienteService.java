package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.cliente.Dependiente;
import com.proyecto.vision.repositorios.cliente.IDependienteRepository;
import com.proyecto.vision.servicios.interf.cliente.IDependienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DependienteService implements IDependienteService {
    @Autowired
    private IDependienteRepository rep;

    /**
     * Metodo que permite la creacion del objeto
     * @param dependiente
     * @return el objeto creado
     */
    @Override
    public Dependiente crear(Dependiente dependiente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_dependiente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	dependiente.setCodigo(codigo.get());
    	return rep.save(dependiente);
    }

    /**
     * Metodo que permite la actualizacion del objeto
     * @param dependiente
     * @return el objeto actualizado
     */
    @Override
    public Dependiente actualizar(Dependiente dependiente) {
        return rep.save(dependiente);
    }

    /**
     * Metodo que permite obtener un objeto a traves del id
     * @param id
     * @return el objeto
     */
    @Override
    public Dependiente obtener(long id) {
        Optional<Dependiente> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.dependiente);
    }

    /**
     * Metodo que permite consultar todos los objetos
     * @return lista de todos los objetos
     */
    @Override
    public List<Dependiente> consultar() {
        return rep.findAll();
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por la razon social
     * @param razonSocial
     * @return lista de los objetos filtrados por razon social
     */
    @Override
    public List<Dependiente> consultarPorRazonSocial(String razonSocial) {
        return  rep.consultarPorRazonSocial(razonSocial);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por el cliente
     * @param clienteId
     * @return lista de los objetos filtrados por el cliente
     */
    @Override
    public List<Dependiente> consultarPorCliente(long clienteId) {
        return  rep.consultarPorCliente(clienteId);
    }
}
