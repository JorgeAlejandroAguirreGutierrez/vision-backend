package com.proyecto.vision.servicios.impl.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.venta.CierreCaja;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.repositorios.venta.ICierreCajaRepository;
import com.proyecto.vision.repositorios.venta.IFacturaRepository;
import com.proyecto.vision.servicios.interf.venta.ICierreCajaService;
import com.proyecto.vision.servicios.interf.venta.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CierreCajaService implements ICierreCajaService {
	@Autowired
    private ICierreCajaRepository rep;
	@Autowired
    private IFacturaRepository facturaRep;
    @Override
    public void validar(CierreCaja cierreCaja) {
        if(cierreCaja.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(cierreCaja.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(cierreCaja.getEmpresa().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
    }
    
    @Override
    public CierreCaja crear(CierreCaja cierreCaja) {
        validar(cierreCaja);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_cierre_caja, cierreCaja.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	Optional<CierreCaja> cierreCajaExistente = rep.obtenerPorFechaYEmpresa(cierreCaja.getFecha(), cierreCaja.getEmpresa().getId());
    	if(cierreCajaExistente.isPresent()){
    	    throw new EntidadExistenteException(Constantes.cierre_caja);
        }
        cierreCaja.setCodigo(codigo.get());
    	List<Factura> facturas = facturaRep.consultarPorFechaYEmpresaYEstadoEmitidaYEstadoRecaudada(cierreCaja.getFecha(), cierreCaja.getEmpresa().getId(), Constantes.estadoEmitida, Constantes.estadoRecaudada);
    	for(Factura factura: facturas){
    	    factura.setEstado(Constantes.estadoCerrada);
    	    facturaRep.save(factura);
        }
        cierreCaja.setEstado(Constantes.estadoActivo);
    	return rep.save(cierreCaja);
    }

    @Override
    public CierreCaja actualizar(CierreCaja cierreCaja) {
        validar(cierreCaja);
        return rep.save(cierreCaja);
    }

    @Override
    public CierreCaja activar(CierreCaja cierreCaja) {
        validar(cierreCaja);
        cierreCaja.setEstado(Constantes.estadoActivo);
        return rep.save(cierreCaja);
    }

    @Override
    public CierreCaja inactivar(CierreCaja cierreCaja) {
        validar(cierreCaja);
        cierreCaja.setEstado(Constantes.estadoInactivo);
        return rep.save(cierreCaja);
    }

    @Override
    public CierreCaja obtener(long id) {
        Optional<CierreCaja> resp= rep.findById(id);
        if(resp.isPresent()) {
        	return resp.get();
        }
        throw new EntidadNoExistenteException(Constantes.cierre_caja);
    }

    public CierreCaja normalizar() { return null; }

    @Override
    public List<CierreCaja> consultar() {
        return rep.consultar();
    }

    @Override
    public List<CierreCaja> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<CierreCaja> consultarPorEstado(String estado) {
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<CierreCaja> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<CierreCaja> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

}
