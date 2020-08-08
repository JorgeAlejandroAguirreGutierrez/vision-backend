package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IFacturaDetalleRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.IProductoRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaDetalleService;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    public boolean importar(MultipartFile file) {
        return false;
    }
}
