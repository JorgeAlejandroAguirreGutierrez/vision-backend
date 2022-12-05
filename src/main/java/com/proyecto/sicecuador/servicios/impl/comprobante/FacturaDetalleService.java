package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
import com.proyecto.sicecuador.repositorios.comprobante.IFacturaDetalleRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaDetalleService implements IFacturaDetalleService {

    @Autowired
    private IFacturaDetalleRepository rep;

    @Override
    public FacturaDetalle crear(FacturaDetalle facturaDetalle) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_factura_detalle);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	facturaDetalle.setCodigo(codigo.get());
    	return rep.save(facturaDetalle);
    }

    @Override
    public FacturaDetalle actualizar(FacturaDetalle facturaDetalle) {
        return rep.save(facturaDetalle);
    }

    @Override
    public FacturaDetalle obtener(long id) {
        Optional<FacturaDetalle> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.factura_detalle);
    }

    @Override
    public List<FacturaDetalle> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<FacturaDetalle> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    
    public Optional<FacturaDetalle> calcular(FacturaDetalle facturaDetalle) {
    	this.calcularSubtotalSinDescuentoLinea(facturaDetalle);
        this.calcularValorPorcentajeDescuentoLinea(facturaDetalle);
        this.calcularTotalDescuentoLinea(facturaDetalle);
        this.calcularIvaSinDescuentoLinea(facturaDetalle);
        this.calcularSubtotalConDescuentoLinea(facturaDetalle);
        this.calcularIvaConDescuentoLinea(facturaDetalle);
        this.calcularTotalConDescuentoLinea(facturaDetalle);
        return Optional.of(facturaDetalle);
    }
    
    private void calcularSubtotalSinDescuentoLinea(FacturaDetalle facturaDetalle) {
		double subtotalSinDescuentoLinea=facturaDetalle.getCantidad()*facturaDetalle.getPrecio().getPrecioVentaPublicoManual();
    	subtotalSinDescuentoLinea=Math.round(subtotalSinDescuentoLinea*100.0)/100.0;
    	facturaDetalle.setSubtotalSinDescuentoLinea(subtotalSinDescuentoLinea);
    }
    
    private void calcularValorPorcentajeDescuentoLinea(FacturaDetalle facturaDetalle) {
		double valorPorcentajeDescuentoLinea=(facturaDetalle.getSubtotalSinDescuentoLinea()*facturaDetalle.getPorcentajeDescuentoLinea())/100;
    	valorPorcentajeDescuentoLinea= Math.round(valorPorcentajeDescuentoLinea*100.0)/100.0;
        facturaDetalle.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);
    	
    }
    
    private void calcularTotalDescuentoLinea(FacturaDetalle facturaDetalle) {
		double totalDescuentoLinea=facturaDetalle.getValorDescuentoTotalLinea()+facturaDetalle.getValorPorcentajeDescuentoLinea()+facturaDetalle.getValorDescuentoLinea()+facturaDetalle.getValorPorcentajeDescuentoTotalLinea();  
    	totalDescuentoLinea= Math.round(totalDescuentoLinea*100.0)/100.0;
        facturaDetalle.setTotalDescuentoLinea(totalDescuentoLinea);    	
    }
    
    private void calcularIvaSinDescuentoLinea(FacturaDetalle facturaDetalle) {
		double ivaSinDescuentoLinea=facturaDetalle.getSubtotalSinDescuentoLinea()*facturaDetalle.getImpuesto().getPorcentaje()/100;
        ivaSinDescuentoLinea= Math.round(ivaSinDescuentoLinea*100.0)/100.0;
        facturaDetalle.setIvaSinDescuentoLinea(ivaSinDescuentoLinea);
        
    }
    
    private void calcularSubtotalConDescuentoLinea(FacturaDetalle facturaDetalle) {
		double subtotalConDescuentoLinea=facturaDetalle.getSubtotalSinDescuentoLinea()-facturaDetalle.getTotalDescuentoLinea();
    	subtotalConDescuentoLinea= Math.round(subtotalConDescuentoLinea*100.0)/100.0;
        facturaDetalle.setSubtotalConDescuentoLinea(subtotalConDescuentoLinea);
    }
    
    private void calcularIvaConDescuentoLinea(FacturaDetalle facturaDetalle) {
		double ivaConDescuentoLinea=facturaDetalle.getSubtotalConDescuentoLinea()*facturaDetalle.getImpuesto().getPorcentaje()/100;
    	ivaConDescuentoLinea = Math.round(ivaConDescuentoLinea*100.0)/100.0;
        facturaDetalle.setIvaConDescuentoLinea(ivaConDescuentoLinea);
    }
    
    private void calcularTotalConDescuentoLinea(FacturaDetalle facturaDetalle) {
		double totalConDescuentoLinea=facturaDetalle.getSubtotalConDescuentoLinea()+facturaDetalle.getIvaConDescuentoLinea();
    	totalConDescuentoLinea = Math.round(totalConDescuentoLinea*100.0)/100.0;
        facturaDetalle.setTotalConDescuentoLinea(totalConDescuentoLinea);
    }

    @Override
    public void importar(MultipartFile file) {
    }    
}
