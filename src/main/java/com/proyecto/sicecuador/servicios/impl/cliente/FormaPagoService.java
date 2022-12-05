package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IFormaPagoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IFormaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormaPagoService implements IFormaPagoService {
    @Autowired
    private IFormaPagoRepository rep;
    
    @Override
    public FormaPago crear(FormaPago formaPago) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_forma_pago);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	formaPago.setCodigo(codigo.get());
    	return rep.save(formaPago);
    }

    @Override
    public FormaPago actualizar(FormaPago formaPago) {
        return rep.save(formaPago);
    }

    @Override
    public FormaPago activar(FormaPago formaPago) {
        formaPago.setEstado(Constantes.activo);
        return rep.save(formaPago);
    }

    @Override
    public FormaPago inactivar(FormaPago formaPago) {
        formaPago.setEstado(Constantes.inactivo);
        return rep.save(formaPago);
    }

    @Override
    public FormaPago obtener(long id) {
        Optional<FormaPago> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.forma_pago);
    }

    @Override
    public List<FormaPago> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<FormaPago> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<FormaPago> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<FormaPago> buscar(FormaPago formaPago) {
        return  rep.buscar(formaPago.getCodigo(), formaPago.getDescripcion(), formaPago.getAbreviatura());
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<FormaPago> formasPagos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal, 10);
            for (List<String> datos: info) {
                FormaPago formaPago = new FormaPago(datos);
                formasPagos.add(formaPago);
            }
            rep.saveAll(formasPagos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
