package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Proforma;
import com.proyecto.sicecuador.repositorios.comprobante.IProformaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IProformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Proforma activar(Proforma proforma) {
        proforma.setEstado(Constantes.activo);
        return rep.save(proforma);
    }

    @Override
    public Proforma inactivar(Proforma proforma) {
        proforma.setEstado(Constantes.inactivo);
        return rep.save(proforma);
    }

    @Override
    public Proforma obtener(long id) {
        Optional<Proforma> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.proforma);
    }

    @Override
    public List<Proforma> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Proforma> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Proforma> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile file) {
    }
}
