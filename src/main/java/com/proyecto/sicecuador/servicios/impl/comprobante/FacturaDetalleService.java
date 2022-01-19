package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
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
    public FacturaDetalle crear(FacturaDetalle factura_detalle) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_factura_detalle);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	factura_detalle.setCodigo(codigo.get());
    	return rep.save(factura_detalle);
    }

    @Override
    public FacturaDetalle actualizar(FacturaDetalle factura_detalle) {
        return rep.save(factura_detalle);
    }

    @Override
    public FacturaDetalle eliminar(FacturaDetalle factura_detalle) {
        rep.deleteById(factura_detalle.getId());
        return factura_detalle;
    }

    @Override
    public Optional<FacturaDetalle> obtener(FacturaDetalle factura_detalle) {
        return rep.findById(factura_detalle.getId());
    }

    @Override
    public List<FacturaDetalle> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<FacturaDetalle> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
    
    public void calcularTotalSinDescuento(FacturaDetalle facturaDetalle) {
    	double totalSinDescuento=0;
    	totalSinDescuento=facturaDetalle.getCantidad()*facturaDetalle.getPrecio().getPrecioVentaPublico();
    	totalSinDescuento=Math.round(totalSinDescuento*100.0)/100.0;
    	facturaDetalle.setTotalSinDescuento(totalSinDescuento);
    }
    
    public void calcularValorPorcentajeDescuento(FacturaDetalle facturaDetalle) {
    	double valorPorcentajeDescuentoIndividual=0;
    	valorPorcentajeDescuentoIndividual=(facturaDetalle.getTotalSinDescuento()*facturaDetalle.getPorcentajeDescuentoIndividual())/100;
        valorPorcentajeDescuentoIndividual= Math.round(valorPorcentajeDescuentoIndividual*100.0)/100.0;
        facturaDetalle.setValorPorcentajeDescuentoIndividual(valorPorcentajeDescuentoIndividual);
    }
    
    public void calcularTotalDescuento(FacturaDetalle facturaDetalle) {
    	double totalDescuentoIndividual=0;
        totalDescuentoIndividual=facturaDetalle.getValorDescuentoIndividual()+facturaDetalle.getValorPorcentajeDescuentoIndividual()
        +facturaDetalle.getValorDescuentoIndividualTotales()+facturaDetalle.getValorPorcentajeDescuentoIndividual()
        +facturaDetalle.getValorDescuentoIndividualSubtotales()+facturaDetalle.getValorPorcentajeDescuentoIndividualSubtotales();    
        totalDescuentoIndividual= Math.round(totalDescuentoIndividual*100.0)/100.0;
        facturaDetalle.setTotalDescuentoIndividual(totalDescuentoIndividual);
    }
    
    public void calcularValorIvaSinDescuento(FacturaDetalle facturaDetalle) {
    	double valorIvaSinDescuento=0;
        valorIvaSinDescuento=facturaDetalle.getTotalSinDescuento()*facturaDetalle.getImpuesto().getPorcentaje()/100;
        valorIvaSinDescuento= Math.round(valorIvaSinDescuento*100.0)/100.0;
        facturaDetalle.setValorIvaSinDescuento(valorIvaSinDescuento);
    }
    
    public void calcularTotalConDescuento(FacturaDetalle facturaDetalle) {
    	double totalConDescuento=0;
        totalConDescuento=facturaDetalle.getTotalSinDescuento()-facturaDetalle.getTotalDescuentoIndividual()+facturaDetalle.getValorIvaSinDescuento();
        totalConDescuento= Math.round(totalConDescuento*100.0)/100.0;
        facturaDetalle.setTotalConDescuento(totalConDescuento);
    }
    
    public void calcularValorIvaConDescuento(FacturaDetalle facturaDetalle) {
    	double valorIvaConDescuento=0;
        valorIvaConDescuento=facturaDetalle.getTotalConDescuento()*facturaDetalle.getImpuesto().getPorcentaje()/100;
        valorIvaConDescuento = Math.round(valorIvaConDescuento*100.0)/100.0;
        facturaDetalle.setValorIvaConDescuento(valorIvaConDescuento);
    }
    
    /*CALCULAR SUBTOTALES*/
    public void calcularValorDescuentoSubtotales(Factura factura, FacturaDetalle facturaDetalle){
    	double valorDescuentoIndividualSubtotales=0;
    	valorDescuentoIndividualSubtotales=(factura.getValorDescuentoSubtotal()*facturaDetalle.getTotalSinDescuento())/factura.getSubtotalSinDescuento();
        valorDescuentoIndividualSubtotales=Math.round(valorDescuentoIndividualSubtotales*100.0)/100.0;
        facturaDetalle.setValorDescuentoIndividualSubtotales(valorDescuentoIndividualSubtotales);
    }
    
    public void calcularValorPorcentajeDescuentoSubtotales(Factura factura, FacturaDetalle facturaDetalle) {
    	double valorPorcentajeDescuentoIndividualSubtotales=(factura.getValorPorcentajeDescuentoSubtotal()*facturaDetalle.getTotalSinDescuento())/factura.getSubtotalSinDescuento();
        valorPorcentajeDescuentoIndividualSubtotales=Math.round(valorPorcentajeDescuentoIndividualSubtotales*100.0)/100.0;
        facturaDetalle.setValorPorcentajeDescuentoIndividualSubtotales(valorPorcentajeDescuentoIndividualSubtotales);
    }
    
    public void calcularPorcentajeDescuentoSubtotales(Factura factura, FacturaDetalle facturaDetalle) {
    	double porcentajeDescuentoIndividualSubtotales=0;
    	porcentajeDescuentoIndividualSubtotales=facturaDetalle.getValorPorcentajeDescuentoIndividualSubtotales()/facturaDetalle.getTotalSinDescuento();
        porcentajeDescuentoIndividualSubtotales= Math.round(porcentajeDescuentoIndividualSubtotales*100.0)/100.0;
        facturaDetalle.setPorcentajeDescuentoIndividualSubtotales(porcentajeDescuentoIndividualSubtotales);
    }
    /*FIN CALCULAR SUBTOTALES*/
    
    /*CALCULAR DESCUENTOS TOTALES*/
    public void calcularValoDescuentoTotales(Factura factura, FacturaDetalle facturaDetalle) {
    	double valorDescuentoIndividualTotales=0;
    	if(facturaDetalle.getImpuesto().getPorcentaje()>0) {
    		valorDescuentoIndividualTotales=((factura.getValorDescuentoTotal()*facturaDetalle.getTotalSinDescuento())/factura.getSubtotalSinDescuento()/((100+facturaDetalle.getImpuesto().getPorcentaje())/100));
    	} else {
    		valorDescuentoIndividualTotales=((factura.getValorDescuentoTotal()*facturaDetalle.getTotalSinDescuento())/factura.getSubtotalSinDescuento());
    	}
    	valorDescuentoIndividualTotales= Math.round(valorDescuentoIndividualTotales*100.0)/100.0;
    	facturaDetalle.setValorDescuentoIndividualTotales(valorDescuentoIndividualTotales);
    }
    
    public void calcularValorPorcentajeDescuentoTotales(Factura factura, FacturaDetalle facturaDetalle) {
    	double valorPorcentajeDescuentoIndividualTotales=0;
    	if(facturaDetalle.getImpuesto().getPorcentaje()>0) {
    		valorPorcentajeDescuentoIndividualTotales=((factura.getValorPorcentajeDescuentoTotal()*facturaDetalle.getTotalSinDescuento())/factura.getSubtotalSinDescuento()/((100+facturaDetalle.getImpuesto().getPorcentaje())/100));
    	} else {
    		valorPorcentajeDescuentoIndividualTotales=((factura.getValorPorcentajeDescuentoTotal()*facturaDetalle.getTotalSinDescuento())/factura.getSubtotalSinDescuento());
    	}
    	valorPorcentajeDescuentoIndividualTotales= Math.round(valorPorcentajeDescuentoIndividualTotales*100.0)/100.0;
    	facturaDetalle.setValorDescuentoIndividualTotales(valorPorcentajeDescuentoIndividualTotales);
    }
    
    public void calcular_porcentaje_descuento_totales(Factura factura, FacturaDetalle facturaDetalle) {
    	double porcentajeDescuentoIndividualTotales=facturaDetalle.getValorPorcentajeDescuentoIndividualTotales()/facturaDetalle.getTotalSinDescuento();
    	porcentajeDescuentoIndividualTotales= Math.round(porcentajeDescuentoIndividualTotales*100.0)/100.0;
        facturaDetalle.setPorcentajeDescuentoIndividualTotales(porcentajeDescuentoIndividualTotales);
    }
    /*FIN CALCULAR DESCUENTOS TOTALES*/
    
}
