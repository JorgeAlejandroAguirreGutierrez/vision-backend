package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
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
}
