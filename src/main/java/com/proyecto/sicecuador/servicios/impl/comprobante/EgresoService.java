package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Egreso;
import com.proyecto.sicecuador.repositorios.comprobante.IEgresoRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IEgresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class EgresoService implements IEgresoService {
    @Autowired
    private IEgresoRepository rep;
    
    @Override
    public Egreso crear(Egreso egreso) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_egreso);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	egreso.setCodigo(codigo.get());
    	return rep.save(egreso);
    }

    @Override
    public Egreso actualizar(Egreso egreso) {
        return rep.save(egreso);
    }

    @Override
    public Egreso eliminar(Egreso egreso) {
        rep.deleteById(egreso.getId());
        return egreso;
    }

    @Override
    public Optional<Egreso> obtener(Egreso egreso) {
        return rep.findById(egreso.getId());
    }

    @Override
    public List<Egreso> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
