package com.proyecto.vision.servicios.impl.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.acceso.Paquete;
import com.proyecto.vision.repositorios.acceso.IPaqueteRepository;
import com.proyecto.vision.servicios.interf.acceso.IPaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaqueteService implements IPaqueteService {
    @Autowired
    private IPaqueteRepository rep;

    private void validar(Paquete paquete) {
        if(paquete.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(paquete.getMinimoComprobantes() == Constantes.cero) throw new DatoInvalidoException(Constantes.minimoComprobantes);
        if(paquete.getMaximoComprobantes() == Constantes.cero) throw new DatoInvalidoException(Constantes.maximoComprobantes);
        if(paquete.getValorPuestaInicial() <= Constantes.cero) throw new DatoInvalidoException(Constantes.valorPuestaInicial);
        if(paquete.getPorcentajeComision() <= Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeComision);
        if(paquete.getCantidadUsuarioRecaudacion() <= Constantes.cero) throw new DatoInvalidoException(Constantes.cantidadUsuarioRecaudacion);
        if(paquete.getCantidadUsuarioGerente() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidadUsuarioGerente);
        if(paquete.getCantidadUsuarioAdministrador() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidadUsuarioAdministrador);
        if(paquete.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
    }

    private void validarCalcular(Paquete paquete) {
        if(paquete.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(paquete.getMinimoComprobantes() == Constantes.cero) throw new DatoInvalidoException(Constantes.minimoComprobantes);
        if(paquete.getMaximoComprobantes() == Constantes.cero) throw new DatoInvalidoException(Constantes.maximoComprobantes);
        if(paquete.getPorcentajeComision() < Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeComision);
    }


    @Override
    public Paquete crear(Paquete paquete) {
        validar(paquete);
        calcular(paquete);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_paquete);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        paquete.setCodigo(codigo.get());
        paquete.setEstado(Constantes.estadoActivo);
        Paquete res = rep.save(paquete);
        res.normalizar();
        return res;
    }

    @Override
    public Paquete actualizar(Paquete paquete) {
        validar(paquete);
        Paquete res = rep.save(paquete);
        res.normalizar();
        return res;
    }

    @Override
    public Paquete activar(Paquete paquete) {
        validar(paquete);
        paquete.setEstado(Constantes.estadoActivo);
        Paquete res = rep.save(paquete);
        res.normalizar();
        return res;
    }

    @Override
    public Paquete inactivar(Paquete paquete) {
        validar(paquete);
        paquete.setEstado(Constantes.estadoInactivo);
        Paquete res = rep.save(paquete);
        res.normalizar();
        return res;
    }

    @Override
    public Paquete obtener(long id) {
        Optional<Paquete> paquete = rep.findById(id);
        if(paquete.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Paquete res = paquete.get();
        res.normalizar();
        return res;
    }

    @Override
    public List<Paquete> consultar() {
        return rep.consultar();
    }

    @Override
    public Page<Paquete> consultarPagina(Pageable pageable){
        return rep.findAll(pageable);
    }

    @Override
    public List<Paquete> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Paquete calcular(Paquete paquete){
        validarCalcular(paquete);
        double valorAnual = paquete.getValorTotal() * 12;
        valorAnual = Math.round(valorAnual * 100.0) / 100.0;
        double valorMinimo = paquete.getValorTotal() / paquete.getMinimoComprobantes();
        valorMinimo = Math.round(valorMinimo * 100.0) / 100.0;
        double valorMaximo = paquete.getValorTotal() / paquete.getMaximoComprobantes();
        valorMaximo = Math.round(valorMaximo * 100.0) / 100.0;
        double comision = paquete.getValorTotal() * paquete.getPorcentajeComision() / 100;
        comision = Math.round(comision * 100.0) / 100.0;

        paquete.setValorAnual(valorAnual);
        paquete.setValorMinimo(valorMinimo);
        paquete.setValorMaximo(valorMaximo);
        paquete.setComision(comision);
        return paquete;
    }
}
