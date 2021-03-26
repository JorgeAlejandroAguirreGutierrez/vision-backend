package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.DetalleFactura;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IFacturaDetalleRepository;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class FacturaDetalleService implements IFacturaDetalleService {

    @Autowired
    private IFacturaDetalleRepository rep;
    
    @Autowired
    private static IParametroRepository parametroRep;

    @Override
    public DetalleFactura crear(DetalleFactura factura_detalle) {
        return rep.save(factura_detalle);
    }

    @Override
    public DetalleFactura actualizar(DetalleFactura factura_detalle) {
        return rep.save(factura_detalle);
    }

    @Override
    public DetalleFactura eliminar(DetalleFactura factura_detalle) {
        rep.deleteById(factura_detalle.getId());
        return factura_detalle;
    }

    @Override
    public Optional<DetalleFactura> obtener(DetalleFactura factura_detalle) {
        return rep.findById(factura_detalle.getId());
    }

    @Override
    public List<DetalleFactura> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
