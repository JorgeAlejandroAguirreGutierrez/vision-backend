package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.repositorios.interf.entrega.IGuiaRemisionRepository;
import com.proyecto.sicecuador.repositorios.interf.entrega.ITransportistaRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.ITransportistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TransportistaService implements ITransportistaService {
    @Autowired
    private ITransportistaRepository rep;
    
    @Override
    public Transportista crear(Transportista transportista) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_transportista);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	transportista.setCodigo(codigo.get());
    	return rep.save(transportista);
    }

    @Override
    public Transportista actualizar(Transportista transportista) {
        return rep.save(transportista);
    }

    @Override
    public Transportista eliminar(Transportista transportista) {
        rep.deleteById(transportista.getId());
        return transportista;
    }

    @Override
    public Optional<Transportista> obtener(Transportista transportista) {
        return rep.findById(transportista.getId());
    }

    @Override
    public List<Transportista> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Transportista> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Transportista> transportistas=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,1);
            for (List<String> datos: info) {
                Transportista transportista = new Transportista(datos);
                transportistas.add(transportista);
            }
            if (transportistas.isEmpty()){
                return false;
            }
            rep.saveAll(transportistas);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
