package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.repositorios.recaudacion.IBancoRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IBancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BancoService implements IBancoService {
    @Autowired
    private IBancoRepository rep;

    @Override
    public void validar(Banco banco) {
        if(banco.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(banco.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(banco.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public Banco crear(Banco banco) {
        validar(banco);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_banco);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	banco.setCodigo(codigo.get());
    	banco.setEstado(Constantes.activo);
    	return rep.save(banco);
    }

    @Override
    public Banco actualizar(Banco banco) {
        validar(banco);
        return rep.save(banco);
    }

    @Override
    public Banco activar(Banco banco) {
        validar(banco);
        banco.setEstado(Constantes.activo);
        return rep.save(banco);
    }

    @Override
    public Banco inactivar(Banco banco) {
        validar(banco);
        banco.setEstado(Constantes.inactivo);
        return rep.save(banco);
    }

    @Override
    public Banco obtener(long id) {
        Optional<Banco> res=rep.findById(id);
        return res.get();
    }

    @Override
    public List<Banco> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Banco> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }
    
    @Override
    public Page<Banco> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
