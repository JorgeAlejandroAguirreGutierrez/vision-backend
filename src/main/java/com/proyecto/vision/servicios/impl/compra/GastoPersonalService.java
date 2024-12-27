package com.proyecto.vision.servicios.impl.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.compra.*;
import com.proyecto.vision.repositorios.compra.IGastoPersonalRepository;
import com.proyecto.vision.repositorios.configuracion.ITipoComprobanteRepository;
import com.proyecto.vision.servicios.interf.compra.IGastoPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GastoPersonalService implements IGastoPersonalService {
    @Autowired
    private IGastoPersonalRepository rep;
    @Autowired
    private ITipoComprobanteRepository tipoComprobanteRepository;

    @Override
    public void validar(GastoPersonal gastoPersonal) {
        if(gastoPersonal.getEstablecimiento().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.establecimiento);
        if(gastoPersonal.getPuntoVenta().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.punto_venta);
        if(gastoPersonal.getSecuencial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.secuencial);
        if(gastoPersonal.getEstablecimiento().length() > 3) throw new DatoInvalidoException(Constantes.establecimiento);
        if(gastoPersonal.getPuntoVenta().length() > 3) throw new DatoInvalidoException(Constantes.punto_venta);
        if(gastoPersonal.getSecuencial().length() > 9) throw new DatoInvalidoException(Constantes.secuencial);
        if (gastoPersonal.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if (gastoPersonal.getProveedor().getId() == Constantes.ceroId)
            throw new DatoInvalidoException(Constantes.proveedor);
        if (gastoPersonal.getUsuario().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.usuario);
        if (gastoPersonal.getGastoPersonalLineas().isEmpty())
            throw new DatoInvalidoException(Constantes.factura_compra_linea);
        for (GastoPersonalLinea gastoPersonalLinea : gastoPersonal.getGastoPersonalLineas()) {
            validarLinea(gastoPersonalLinea);
        }
    }

    @Transactional
    @Override
    public GastoPersonal crear(GastoPersonal gastoPersonal) {
        validar(gastoPersonal);
        Optional<GastoPersonal> gastoPersonalExistente = rep.obtenerPorEstableciminetoYEstacionYSecuencialYProveedor(gastoPersonal.getEstablecimiento(), gastoPersonal.getPuntoVenta(), gastoPersonal.getSecuencial(), gastoPersonal.getProveedor().getId());
        if(gastoPersonalExistente.isPresent()){
            throw new EntidadExistenteException(Constantes.gasto_personal);
        }
        Optional<String> codigo = Util.generarCodigoPorEmpresa(gastoPersonal.getFecha(), Constantes.tabla_gasto_personal, gastoPersonal.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        gastoPersonal.setCodigo(codigo.get());
        gastoPersonal.setEstado(Constantes.estadoPorPagar);
        GastoPersonal res = rep.save(gastoPersonal);
        res.normalizar();
        return res;
    }

    @Override
    public GastoPersonal actualizar(GastoPersonal gastoPersonal) {
        validar(gastoPersonal);
        for (GastoPersonalLinea gastoPersonalLinea : gastoPersonal.getGastoPersonalLineas()) {
            validarLinea(gastoPersonalLinea);
        }
        GastoPersonal res = rep.save(gastoPersonal);
        res.normalizar();
        return res;
    }

    @Override
    public GastoPersonal anular(GastoPersonal gastoPersonal) {
        validar(gastoPersonal);
        gastoPersonal.setEstado(Constantes.estadoAnulada);
        GastoPersonal res = rep.save(gastoPersonal);
        res.normalizar();
        return res;
    }

    @Override
    public GastoPersonal obtener(long id) {
        Optional<GastoPersonal> gastoPersonal = rep.findById(id);
        if (gastoPersonal.isPresent()) {
            GastoPersonal res = gastoPersonal.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.factura_compra);
    }

    @Override
    public List<GastoPersonal> consultar() {
        return rep.consultar();
    }

    @Override
    public List<GastoPersonal> consultarPorEstado(String estado) {
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<GastoPersonal> consultarPorEmpresa(long empresaId) {
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<GastoPersonal> consultarPorProveedorYEmpresaYEstado(long proveedorId, long empresaId, String estado){
        return rep.consultarPorProveedorYEmpresaYEstado(proveedorId, empresaId, estado);
    }

    @Override
    public List<GastoPersonal> consultarPorEmpresaYEstado(long empresaId, String estado) {
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    /*
     * CALCULOS CON FACTURA COMPRA LINEAS
     */
    @Override
    public void validarLinea(GastoPersonalLinea gastoPersonalLinea) {
        if (gastoPersonalLinea.getCantidad() <= Constantes.cero)
            throw new DatoInvalidoException(Constantes.cantidad);
        if (gastoPersonalLinea.getCostoUnitario() <= Constantes.cero)
            throw new DatoInvalidoException(Constantes.costoUnitario);
        if (gastoPersonalLinea.getImpuesto().getId() == Constantes.ceroId)
            throw new DatoInvalidoException(Constantes.impuesto);
    }
}