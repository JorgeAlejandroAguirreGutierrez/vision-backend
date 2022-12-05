package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
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
    public Banco crear(Banco banco) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_banco);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	banco.setCodigo(codigo.get());
    	return rep.save(banco);
    }

    @Override
    public Banco actualizar(Banco banco) {
        return rep.save(banco);
    }

    @Override
    public Banco activar(Banco banco) {
        banco.setEstado(Constantes.activo);
        return rep.save(banco);
    }

    @Override
    public Banco inactivar(Banco banco) {
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


    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Banco> bancos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,1);
            for (List<String> datos: info) {
                Banco banco = new Banco(datos);
                bancos.add(banco);
            }
            rep.saveAll(bancos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
