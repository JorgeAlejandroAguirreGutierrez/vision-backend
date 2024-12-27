package com.proyecto.vision.servicios.impl.cajaBanco;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.cajaBanco.CuentaPropia;
import com.proyecto.vision.repositorios.cajaBanco.ICuentaPropiaRepository;
import com.proyecto.vision.servicios.interf.cajaBanco.ICuentaPropiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class CuentaPropiaService implements ICuentaPropiaService {
    @Autowired
    private ICuentaPropiaRepository rep;

    /**
     * Metodo que permite la validacion de las propiedades del objeto
     * @param cuentaPropia
     */
    @Override
    public void validar(CuentaPropia cuentaPropia) {
        if (cuentaPropia.getNumero().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero);
        if (cuentaPropia.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
    }

    /**
     * Metodo que permite la creacion del objeto
     * @param cuentaPropia
     * @return el objeto creado
     */
    @Override
    public CuentaPropia crear(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        Optional<String> codigo = Util.generarCodigoPorEmpresa(null, Constantes.tabla_cuenta_propia, cuentaPropia.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        cuentaPropia.setCodigo(codigo.get());
        cuentaPropia.setEstado(Constantes.estadoActivo);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite la actualizacion del objeto
     * @param cuentaPropia
     * @return el objeto actualizado
     */
    @Override
    public CuentaPropia actualizar(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite poner la propiedad estado en activo
     * @param cuentaPropia
     * @return el objeto con la propiedad estado en activo
     */
    @Override
    public CuentaPropia activar(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        cuentaPropia.setEstado(Constantes.estadoActivo);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite poner la propiedad estado en inactivo
     * @param cuentaPropia
     * @return el objeto con la propiedad estado en inactivo
     */
    @Override
    public CuentaPropia inactivar(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        cuentaPropia.setEstado(Constantes.estadoInactivo);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite obtener un objeto a traves del id
     * @param id
     * @return el objeto
     */
    @Override
    public CuentaPropia obtener(long id) {
        Optional<CuentaPropia> cuentaPropia = rep.findById(id);
        if (cuentaPropia.isPresent()) {
            CuentaPropia res = cuentaPropia.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.cuenta_propia);
    }

    /**
     * Metodo que permite consultar todos los objetos
     * @return lista de todos los objetos
     */
    @Override
    public List<CuentaPropia> consultar() {
        return rep.consultar();
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por la empresa
     * @param empresaId
     * @return lista de los objetos filtrados por empresa
     */
    @Override
    public List<CuentaPropia> consultarPorEmpresa(long empresaId) {
        return rep.consultarPorEmpresa(empresaId);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por el estado
     * @param estado
     * @return lista de los objetos filtrados por el estado
     */
    @Override
    public List<CuentaPropia> consultarPorEstado(String estado) {
        return rep.consultarPorEstado(estado);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por el estado
     * @param empresaId
     * @param estado
     * @return lista de los objetos filtrados por empresa y estado
     */
    @Override
    public List<CuentaPropia> consultarPorEmpresaYEstado(long empresaId, String estado) {
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    /**
     * Metodo que permite consultar todos los objetos distintos filtrando por la empresa y el estado
     * @param empresaId
     * @param estado
     * @return lista con los objetos distintos filtrados por empresa y estado
     */
    @Override
    public List<String> consultarBancoDistintoPorEmpresaYEstado(long empresaId, String estado) {
        return rep.consultarBancoDistintoPorEmpresaYEstado(empresaId, estado);
    }

    /**
     * Metodo que permite consultar todos los objetos filtrando por el banco y la empresa
     * @param banco
     * @param empresaId
     * @return lista de los objetos filtrados por banco y empresa
     */
    @Override
    public List<CuentaPropia> consultarPorBancoYEmpresa(String banco, long empresaId) {
        return rep.consultarPorBancoYEmpresa(banco, empresaId, Constantes.estadoActivo);
    }
}