package com.proyecto.vision.servicios.impl.cajaBanco;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.repositorios.cajaBanco.IBancoRepository;
import com.proyecto.vision.servicios.interf.cajaBanco.IBancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BancoService implements IBancoService {
    @Autowired
    private IBancoRepository rep;

    @Override
    public void validar(Banco banco) {
        if(banco.getRuc().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(banco.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(banco.getSubsistema().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public Banco crear(Banco banco) {
        validar(banco);
        Optional<String> codigo = Util.generarCodigo(Constantes.tabla_banco);
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        banco.setCodigo(codigo.get());
        banco.setEstado(Constantes.activo);
    	return rep.save(banco);
    }

    //EXCEPCIONES DE DIFERENTES TIPO
    //EXCEPCION DE TIPO MERAMENTE TECNICO -> TRY {}CATCH{}
    //EXCEPCIONES DE NEGOCIO -> TRY CATCH -> EXCEPCION CUSTOMIZADAS -> HANDLER EXCEPTION

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
        return rep.consultar();
    }
    
    @Override
    public List<Banco> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<Banco> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
