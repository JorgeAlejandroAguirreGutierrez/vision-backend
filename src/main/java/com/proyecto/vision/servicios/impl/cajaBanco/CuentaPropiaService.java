package com.proyecto.vision.servicios.impl.cajaBanco;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.cajaBanco.CuentaPropia;
import com.proyecto.vision.modelos.configuracion.MenuOpcion;
import com.proyecto.vision.modelos.configuracion.Ubicacion;
import com.proyecto.vision.repositorios.cajaBanco.ICuentaPropiaRepository;
import com.proyecto.vision.servicios.interf.cajaBanco.ICuentaPropiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CuentaPropiaService implements ICuentaPropiaService {
    @Autowired
    private ICuentaPropiaRepository rep;

    @Override
    public void validar(CuentaPropia cuentaPropia) {
        if (cuentaPropia.getNumero().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero);
        if (cuentaPropia.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
    }

    @Override
    public CuentaPropia crear(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        Optional<String> codigo = Util.generarCodigoPorEmpresa(Constantes.tabla_cuenta_propia, cuentaPropia.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        cuentaPropia.setCodigo(codigo.get());
        cuentaPropia.setEstado(Constantes.estadoActivo);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    @Override
    public CuentaPropia actualizar(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    @Override
    public CuentaPropia activar(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        cuentaPropia.setEstado(Constantes.estadoActivo);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

    @Override
    public CuentaPropia inactivar(CuentaPropia cuentaPropia) {
        validar(cuentaPropia);
        cuentaPropia.setEstado(Constantes.estadoInactivo);
        CuentaPropia res = rep.save(cuentaPropia);
        res.normalizar();
        return res;
    }

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

    @Override
    public List<CuentaPropia> consultar() {
        return rep.consultar();
    }

    @Override
    public List<CuentaPropia> consultarPorEmpresa(long empresaId) {
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<CuentaPropia> consultarPorEstado(String estado) {
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<CuentaPropia> consultarPorEmpresaYEstado(long empresaId, String estado) {
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public List<String> consultarBancoDistintoPorEmpresaYEstado(long empresaId, String estado) {
        return rep.consultarBancoDistintoPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public List<CuentaPropia> consultarPorBancoYEmpresa(String banco, long empresaId) {
        return rep.consultarPorBancoYEmpresa(banco, empresaId, Constantes.estadoActivo);
    }

    @Override
    public Page<CuentaPropia> consultarPagina(Pageable pageable) {
        return rep.findAll(pageable);
    }


}
