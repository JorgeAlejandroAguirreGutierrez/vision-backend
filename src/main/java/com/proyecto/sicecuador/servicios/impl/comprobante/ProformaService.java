package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Proforma;
import com.proyecto.sicecuador.repositorios.comprobante.IProformaRepository;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IProformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class ProformaService implements IProformaService {
    @Autowired
    private IProformaRepository rep;
    
    @Override
    public Proforma crear(Proforma proforma) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_proforma);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	proforma.setCodigo(codigo.get());
    	return rep.save(proforma);
    }

    @Override
    public Proforma actualizar(Proforma proforma) {
        return rep.save(proforma);
    }

    @Override
    public Proforma eliminar(Proforma proforma) {
        rep.deleteById(proforma.getId());
        return proforma;
    }

    @Override
    public Optional<Proforma> obtener(Proforma proforma) {
        return rep.findById(proforma.getId());
    }

    @Override
    public List<Proforma> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
