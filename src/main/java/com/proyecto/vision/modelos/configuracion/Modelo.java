package com.proyecto.vision.modelos.configuracion;

import com.proyecto.vision.modelos.inventario.TipoGasto;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.modelos.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Modelo {
    private String razonSocial;
    private String nombreComercial;
    private String ruc;
    private String codDoc;
    private String claveAcceso;
    private String establecimiento;
    private String puntoVenta;
    private String secuencial;
    private String numeroComprobante;
    private String direccion;
    private String fecha;
    private String totalSinImpuestos;
    private String totalDescuento;
    private String importeTotal;
    private List<ModeloImpuesto> modeloImpuestos;
    private List<ModeloDetalle> modeloDetalles;

    private String tipo;
    private TipoGasto tipoGasto;
    private Usuario usuario;
    private Empresa empresa;
}
