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

    /**
     * Metodo que permite la validacion de las propiedades del objeto
     * @param banco
     */
    @Override
    public void validar(Banco banco) {
        if(banco.getRuc().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(banco.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(banco.getSubsistema().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }

    /**
     * Metodo que permite la creacion del objeto
     * @param banco
     * @return el objeto creado
     */
    @Override
    public Banco crear(Banco banco) {
        validar(banco);
        Optional<String> codigo = Util.generarCodigo(Constantes.tabla_banco);
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        banco.setCodigo(codigo.get());
        banco.setEstado(Constantes.estadoActivo);
    	return rep.save(banco);
    }

    /**
     * Metodo que permite la actualizacion del objeto
     * @param banco
     * @return el objeto actualizado
     */
    @Override
    public Banco actualizar(Banco banco) {
        validar(banco);
        return rep.save(banco);
    }

    /**
     * Metodo que permite poner la propiedad estado en activo
     * @param banco
     * @return el objeto con la propiedad estado en activo
     */
    @Override
    public Banco activar(Banco banco) {
        validar(banco);
        banco.setEstado(Constantes.estadoActivo);
        return rep.save(banco);
    }

    /**
     * Metodo que permite poner la propiedad estado en inactivo
     * @param banco
     * @return el objeto con la propiedad estado en inactivo
     */
    @Override
    public Banco inactivar(Banco banco) {
        validar(banco);
        banco.setEstado(Constantes.estadoInactivo);
        return rep.save(banco);
    }

    /**
     * Metodo que permite obtener un objeto a traves del id
     * @param id
     * @return el objeto
     */
    @Override
    public Banco obtener(long id) {
        Optional<Banco> res=rep.findById(id);
        return res.get();
    }

    /**
     * Metodo que permite consultar todos los objetos
     * @return lista de todos los objetos
     */
    @Override
    public List<Banco> consultar() {
        return rep.consultar();
    }

    /**
     * Metodo que permite consultar los objetos filtrando por el estado
     * @param estado
     * @return lista de los objetos consultados filtrando por el estado
     */
    @Override
    public List<Banco> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }
}
