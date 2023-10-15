package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.venta.FacturaLinea;
import com.proyecto.vision.repositorios.inventario.IKardexRepository;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import com.proyecto.vision.servicios.interf.inventario.ITipoOperacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KardexService implements IKardexService {
    @Autowired
    private IKardexRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private ITipoOperacionService tipoOperacionService;
    
    @Override
    public Kardex crear(Kardex kardex) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_kardex);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	kardex.setCodigo(codigo.get());
    	return rep.save(kardex);
    }

    @Override
    public Kardex actualizar(Kardex kardex) {
        return rep.save(kardex);
    }

    @Override
    public List<Kardex> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Kardex> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    @Override
    public List<Kardex> consultarPorProducto(long productoId) {
        return rep.consultarPorProducto(productoId);
    }
    @Override
    public List<Kardex> consultarPorTipoComprobanteYReferencia(long tipoComprobanteId, String referencia) {
        return rep.consultarPorTipoComprobanteYReferencia(tipoComprobanteId, referencia);
    }

    @Override
    public Kardex obtener(long id) {
        Optional<Kardex> res=rep.findById(id);
        if(res.isPresent()) {
            return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.kardex);
    }
    @Override
    public Kardex obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYIdLinea(long productoId, long bodegaId, long tipoComprobanteId, String comprobante, long idLinea) {
        Optional<Kardex> res = rep.obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYIdLinea(productoId, bodegaId, tipoComprobanteId, comprobante, idLinea);
        if(res.isEmpty()){
            return null;
        }
        return res.get();
    }
    @Override
    public Kardex obtenerUltimoPorProductoYBodega(long productoId, long bodegaId) {
        Optional<Kardex> res = rep.obtenerUltimoPorProductoYBodega(productoId, bodegaId);
        if(res.isEmpty()){
            return null;
        }
        return res.get();
    }

    @Override
    public Kardex obtenerSaldoInicialPorProductoYBodega(long productoId, long bodegaId) {
        Optional<Kardex> res = rep.obtenerSaldoInicialPorProductoYBodega(1, productoId, bodegaId);
        if(res.isEmpty()){
            return null;
        }
        return res.get();
    }

    @Override
    public Kardex obtenerUltimoPorProductoYBodegaYFecha(long productoId, long bodegaId, Date fecha) {
        Optional<Kardex> res = rep.obtenerUltimoPorProductoYBodegaYFecha(productoId, bodegaId, fecha);
        if(res.isEmpty()){
            return null;
        }
        return res.get();
    }
    @Override
    public Kardex obtenerUltimoPorProductoYBodegaYTablaTipoComprobante(long productoId, long bodegaId, String tablaTipoComprobante) {
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(tablaTipoComprobante);
        Optional<Kardex> res = rep.obtenerUltimoPorProductoYBodegaYTablaTipoComprobante(productoId, bodegaId, tipoComprobante.getId());
        if(res.isEmpty()){
            return null;
        }
        return res.get();
    }
    @Override
    public Kardex obtenerPenultimoPorProductoYBodegaYMismaFechaYId(long productoId, long bodegaId, Date fecha, long id) {
        Optional<Kardex> res = rep.obtenerPenultimoPorProductoYBodegaYMismaFechaYId(productoId, bodegaId, fecha, id);
        if(res.isEmpty()){
            return null;
        }
        return res.get();
    }
    @Override
    public Kardex obtenerPenultimoPorProductoYBodegaYMenorFecha(long productoId, long bodegaId, Date fecha) {
        Optional<Kardex> res = rep.obtenerPenultimoPorProductoYBodegaYMenorFecha(productoId, bodegaId, fecha);
        if(res.isEmpty()){
            return null;
        }
        return res.get();
    }
    @Override
    public void eliminar(long tipoComprobanteId, long tipoOperacionId,String referencia) {
        rep.eliminar(tipoComprobanteId, tipoOperacionId, referencia);
    }

    public void recalcularPorProductoYBodegaYFecha(long productoId, long bodegaId, Date fecha) {
        Optional<Kardex> res = rep.obtenerUltimoPorProductoYBodegaYFecha(productoId, bodegaId, fecha);
        if (res.isEmpty()) {
            return;
        }
        Kardex ultimoKardex = res.get();
        List<Kardex> kardexs = rep.consultarPorProductoYBodegaYFechaMayor(productoId, bodegaId, fecha);
        if (kardexs.isEmpty()) {
            return;
        }
        for (Kardex kardex : kardexs) {
            double saldo = Constantes.cero, costoTotal = Constantes.cero, costoPromedio = Constantes.cero;
            if (kardex.getTipoComprobante().getId() == 8){ // Factura de Compra
                saldo = ultimoKardex.getSaldo() + kardex.getEntrada();
                saldo = Math.round(saldo * 10000.0) / 10000.0;
                costoTotal = ultimoKardex.getCostoTotal() + (kardex.getEntrada() * kardex.getDebe());
                costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;
                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            if (kardex.getTipoComprobante().getId() == 9){ // Nota de Crédito de Compra
                if (kardex.getTipoOperacion().getId() == 6) {
                    saldo = ultimoKardex.getSaldo() + kardex.getEntrada();
                }
                if (kardex.getTipoOperacion().getId() == 8) {
                    saldo = ultimoKardex.getSaldo();
                }
                saldo = Math.round(saldo * 10000.0) / 10000.0;
                costoTotal = ultimoKardex.getCostoTotal() + (kardex.getEntrada() * kardex.getDebe());
                costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;
                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            if (kardex.getTipoComprobante().getId() == 10){ // Nota de Débito de Compra

            }
            if (kardex.getTipoComprobante().getId() == 2){ // Factura de Venta
                saldo = ultimoKardex.getSaldo() - kardex.getSalida();
                saldo = Math.round(saldo * 10000.0) / 10000.0;
                costoTotal = ultimoKardex.getCostoTotal() - (kardex.getSalida() * ultimoKardex.getCostoPromedio());
                costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;
                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
                kardex.setHaber(ultimoKardex.getCostoPromedio());
            }
            if (kardex.getTipoComprobante().getId() == 4){ // Nota de Crédito Venta
                if (kardex.getTipoOperacion().getId() == 7) {
                    saldo = ultimoKardex.getSaldo() + kardex.getEntrada();
                }
                if (kardex.getTipoOperacion().getId() == 9) {
                    saldo = ultimoKardex.getSaldo();
                }
                saldo = Math.round(saldo * 10000.0) / 10000.0;
                costoTotal = ultimoKardex.getCostoTotal() + (kardex.getEntrada() * kardex.getDebe());
                costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;
                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            if (kardex.getTipoComprobante().getId() == 5){ // Nota de Débito Venta

            }

            if (kardex.getTipoComprobante().getId() != 1) {
                kardex.setSaldo(saldo);
                kardex.setCostoPromedio(costoPromedio);
                kardex.setCostoTotal(costoTotal);
                ultimoKardex = kardex;
            }

            rep.save(kardex);
        }
    }
}
