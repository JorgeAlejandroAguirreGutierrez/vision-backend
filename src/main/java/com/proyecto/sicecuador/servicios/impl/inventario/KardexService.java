package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.IKardexRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KardexService implements IKardexService {
    @Autowired
    private IKardexRepository rep;
    
    @Override
    public Kardex crear(Kardex kardex) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_kardex);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	kardex.setCodigo(codigo.get());
    	return rep.save(kardex);
    }

    @Override
    public Kardex actualizar(Kardex kardex) {
        return rep.save(kardex);
    }

    @Override
    public Kardex eliminar(Kardex kardex) {
        rep.deleteById(kardex.getId());
        return kardex;
    }

    @Override
    public Optional<Kardex> obtener(Kardex kardex) {
        return rep.findById(kardex.getId());
    }

    @Override
    public List<Kardex> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Kardex> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Kardex> kardexs=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,8);
            for (List<String> datos: info) {
                Kardex kardex = new Kardex(datos);
                kardexs.add(kardex);
            }
            if(kardexs.isEmpty()){
                return false;
            }
            rep.saveAll(kardexs);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
