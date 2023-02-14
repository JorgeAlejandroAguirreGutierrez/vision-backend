package com.proyecto.sicecuador.servicios.impl.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.exception.SecuenciaNoExistenteException;
import com.proyecto.sicecuador.modelos.compra.FacturaCompra;
import com.proyecto.sicecuador.modelos.compra.FacturaCompraLinea;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.repositorios.compra.IFacturaCompraLineaRepository;
import com.proyecto.sicecuador.repositorios.compra.IFacturaCompraRepository;
import com.proyecto.sicecuador.servicios.interf.compra.IFacturaCompraLineaService;
import com.proyecto.sicecuador.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.sicecuador.servicios.interf.comprobante.ITipoComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaCompraLineaService implements IFacturaCompraLineaService {
    @Autowired
    private IFacturaCompraLineaRepository rep;

    @Override
    public void validar(FacturaCompraLinea facturaCompraLinea) {
        if(facturaCompraLinea.getCantidad() <= Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(facturaCompraLinea.getCostoUnitario() <= Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
    }

    @Override
    public FacturaCompraLinea crear(FacturaCompraLinea facturaCompraLinea) {
        validar(facturaCompraLinea);
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_factura_compra_linea);
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
    	facturaCompraLinea.setCodigo(codigo.get());
        FacturaCompraLinea res = rep.save(facturaCompraLinea);
        res.normalizar();
        return res;
    }

    @Override
    public FacturaCompraLinea actualizar(FacturaCompraLinea facturaCompraLinea) {
        validar(facturaCompraLinea);
        FacturaCompraLinea res = rep.save(facturaCompraLinea);
        res.normalizar();
        return res;
    }

    @Override
    public FacturaCompraLinea obtener(long id) {
        Optional<FacturaCompraLinea> facturaCompraLinea = rep.findById(id);
        if(facturaCompraLinea.isPresent()) {
        	FacturaCompraLinea res = facturaCompraLinea.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.factura_compra);
    }

    @Override
    public List<FacturaCompraLinea> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<FacturaCompraLinea> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    
    public FacturaCompraLinea calcularLinea(FacturaCompraLinea facturaCompraLinea) {
        validar(facturaCompraLinea);
        double totalSinDescuentoLinea = facturaCompraLinea.getCantidad() * facturaCompraLinea.getCostoUnitario();
        facturaCompraLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
        return facturaCompraLinea;
    }
}
