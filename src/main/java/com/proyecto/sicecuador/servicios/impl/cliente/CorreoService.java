package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Correo;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.cliente.ICorreoRepository;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CorreoService implements ICorreoService {
    @Autowired
    private ICorreoRepository rep;
    @Autowired
    private IClienteRepository rep_cliente;
    
    @Override
    public Correo crear(Correo correo) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_correo);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	correo.setCodigo(codigo.get());
    	return rep.save(correo);
    }

    @Override
    public Correo actualizar(Correo correo) {
        return rep.save(correo);
    }

    @Override
    public Correo eliminar(Correo correo) {
        rep.deleteById(correo.getId());
        return correo;
    }

    @Override
    public Optional<Correo> obtener(Correo correo) {
        return rep.findById(correo.getId());
    }

    @Override
    public List<Correo> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Correo> correos=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,5);
            for (List<String> datos: info) {
                Correo correo = new Correo(datos);
                Optional<Cliente> cliente=rep_cliente.findById(correo.getCliente().getId());
                if(cliente.isPresent()){
                    correos.add(correo);
                }
            }
            if(correos.isEmpty()){
                return false;
            }
            rep.saveAll(correos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
