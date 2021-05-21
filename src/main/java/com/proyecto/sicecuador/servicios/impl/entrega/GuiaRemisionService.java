package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.repositorios.entrega.IGuiaRemisionRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.IGuiaRemisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class GuiaRemisionService implements IGuiaRemisionService {
    @Autowired
    private IGuiaRemisionRepository rep;
    
    @Override
    public GuiaRemision crear(GuiaRemision guia_remision) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_guia_remision);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	guia_remision.setCodigo(codigo.get());
    	return rep.save(guia_remision);
    }

    @Override
    public GuiaRemision actualizar(GuiaRemision guia_remision) {
        return rep.save(guia_remision);
    }

    @Override
    public GuiaRemision eliminar(GuiaRemision guia_remision) {
        rep.deleteById(guia_remision.getId());
        return guia_remision;
    }

    @Override
    public Optional<GuiaRemision> obtener(GuiaRemision guia_remision) {
        return rep.findById(guia_remision.getId());
    }

    @Override
    public List<GuiaRemision> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<GuiaRemision> guias_remisiones=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,0);
            for (List<String> datos: info) {
                GuiaRemision guia_remision = new GuiaRemision(datos);
                guias_remisiones.add(guia_remision);
            }
            if (guias_remisiones.isEmpty()){
                return false;
            }
            rep.saveAll(guias_remisiones);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
