package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IPlazoCreditoRepository;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IPlazoCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PlazoCreditoService implements IPlazoCreditoService {
    @Autowired
    private IPlazoCreditoRepository rep;
    
    @Override
    public PlazoCredito crear(PlazoCredito plazo_credito) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_plazo_credito);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	plazo_credito.setCodigo(codigo.get());
    	return rep.save(plazo_credito);
    }

    @Override
    public PlazoCredito actualizar(PlazoCredito plazo_credito) {
        return rep.save(plazo_credito);
    }

    @Override
    public PlazoCredito eliminar(PlazoCredito plazo_credito) {
        rep.deleteById(plazo_credito.getId());
        return plazo_credito;
    }

    @Override
    public Optional<PlazoCredito> obtener(PlazoCredito plazo_credito) {
        return rep.findById(plazo_credito.getId());
    }

    @Override
    public List<PlazoCredito> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<PlazoCredito> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<PlazoCredito> plazos_creditos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal, 14);
            for (List<String> datos: info) {
                PlazoCredito plazo_credito = new PlazoCredito(datos);
                plazos_creditos.add(plazo_credito);
            }
            if(plazos_creditos.isEmpty()){
                return false;
            }
            rep.saveAll(plazos_creditos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
