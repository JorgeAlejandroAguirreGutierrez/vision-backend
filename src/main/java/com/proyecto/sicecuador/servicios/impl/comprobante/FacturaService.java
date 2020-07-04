package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IFacturaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class FacturaService implements IFacturaService {
    @Autowired
    private IFacturaRepository rep;
    @Override
    public Factura crear(Factura factura) {
        return rep.save(factura);
    }

    @Override
    public Factura actualizar(Factura factura) {
        return rep.save(factura);
    }

    @Override
    public Factura eliminar(Factura factura) {
        rep.deleteById(factura.getId());
        return factura;
    }

    @Override
    public Optional<Factura> obtener(Factura factura) {
        return rep.findById(factura.getId());
    }

    @Override
    public List<Factura> consultar() {
        return rep.findAll();
    }

    @Override
    public List<Factura> consultarClienteRazonSocial(Factura factura) {
        return  rep.findAll(new Specification<Factura>() {
            @Override
            public Predicate toPredicate(Root<Factura> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (factura.getCliente().getRazon_social()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("cliente").get("razon_social"), "%"+factura.getCliente().getRazon_social()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public List<Factura> consultarNumero(Factura factura) {
        return  rep.findAll(new Specification<Factura>() {
            @Override
            public Predicate toPredicate(Root<Factura> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (factura.getNumero()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("numero"), "%"+factura.getNumero()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
    @Override
    public ByteArrayInputStream generarPDF(Factura factura) {
        try {
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            Document documento = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(documento, salida);
            // 2. Create PdfWriter
            PdfWriter.getInstance(documento, new FileOutputStream("result.pdf"));
            // 3. Open document
            documento.open();
            // 4. Add content
            documento.add(new Paragraph("LOGO", FontFactory.getFont("arial",30, Font.ITALIC)));
            PdfPTable tabla_empresa = new PdfPTable(1);
            tabla_empresa.addCell(factura.getVendedor().getPunto_venta().getEstablecimiento().getEmpresa().getRazon_social()+"\n"+
                    "Direccion: "+factura.getVendedor().getPunto_venta().getEstablecimiento().getDireccion());
            documento.add(tabla_empresa);
            PdfPTable tabla_factura = new PdfPTable(1);
            tabla_factura.addCell("RUC: "+factura.getVendedor().getPunto_venta().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
                    "FACTURA"+"\n"+
                    "No."+factura.getNumero()+"\n"+
                    "Fecha: "+factura.getFecha().toString());
            documento.add(tabla_factura);
            PdfPTable tabla_cliente = new PdfPTable(1);
            tabla_cliente.addCell("Razon Social: "+factura.getCliente().getRazon_social()+"\n"+
                    "Identificacion: "+factura.getCliente().getIdentificacion()+"\n"+
                    "Fecha: "+factura.getFecha().toString()+"\n"+
                    "Direccion: "+factura.getCliente().getDireccion().getDireccion());
            documento.add(tabla_cliente);
            PdfPTable tabla_factura_detalle = new PdfPTable(9);
            tabla_factura_detalle.addCell("Codigo");
            tabla_factura_detalle.addCell("Cantidad");
            tabla_factura_detalle.addCell("Descripcion");
            tabla_factura_detalle.addCell("Series");
            tabla_factura_detalle.addCell("Precio U");
            tabla_factura_detalle.addCell("Subsidio");
            tabla_factura_detalle.addCell("Sin subsidio");
            tabla_factura_detalle.addCell("Descuento");
            tabla_factura_detalle.addCell("Total");
            for (int i = 0; i <factura.getFactura_detalles().size(); i++)
            {
                tabla_factura_detalle.addCell(factura.getFactura_detalles().get(i).getProducto().getCodigo());
                tabla_factura_detalle.addCell(factura.getFactura_detalles().get(i).getCantidad()+"");
                tabla_factura_detalle.addCell(factura.getFactura_detalles().get(i).getProducto().getNombre());
                String series="";
                if (!factura.getFactura_detalles().get(i).getProducto().isSerie_autogenerado()){
                    for (int j = 0; j <factura.getFactura_detalles().get(i).getCaracteristicas().size(); i++){
                        series=series+" "+factura.getFactura_detalles().get(i).getCaracteristicas().get(j).getSerie();
                    }
                }
                tabla_factura_detalle.addCell(series);
                tabla_factura_detalle.addCell(factura.getFactura_detalles().get(i).getPrecio().getValor()+"");
                tabla_factura_detalle.addCell(factura.getFactura_detalles().get(i).getSubsidio()+"");
                tabla_factura_detalle.addCell(factura.getFactura_detalles().get(i).getSin_subsidio()+"");
                tabla_factura_detalle.addCell(factura.getFactura_detalles().get(i).getValor_descuento_individual_totales()+"");
                tabla_factura_detalle.addCell(factura.getFactura_detalles().get(i).getSubtotal_con_descuento()+"");
            }
            documento.add(tabla_factura_detalle);
            // 5. Close document
            documento.close();

            return new ByteArrayInputStream(salida.toByteArray());
        } catch(Exception e){
            return null;
        }
    }

}
