package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Celular;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.ICelularRepository;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICelularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CelularService implements ICelularService {
    @Autowired
    private ICelularRepository rep;
    @Autowired
    private IClienteRepository rep_cliente;
    
    @Override
    public Celular crear(Celular celular) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_celular);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	celular.setCodigo(codigo.get());
    	return rep.save(celular);
    }

    @Override
    public Celular actualizar(Celular celular) {
        return rep.save(celular);
    }

    @Override
    public Celular eliminar(Celular celular) {
        rep.deleteById(celular.getId());
        return celular;
    }

    @Override
    public Optional<Celular> obtener(Celular celular) {
        return rep.findById(celular.getId());
    }

    @Override
    public List<Celular> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Celular> celulares=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,2);
            for (List<String> datos: info) {
                Celular celular = new Celular(datos);
                Optional<Cliente> cliente=rep_cliente.findById(celular.getCliente().getId());
                if(cliente.isPresent()){
                    celulares.add(celular);
                }
            }
            if(celulares.isEmpty()){
                return false;
            }
            rep.saveAll(celulares);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
