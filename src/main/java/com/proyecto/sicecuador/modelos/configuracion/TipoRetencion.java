package com.proyecto.sicecuador.modelos.configuracion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.configuracion.TipoRetencionUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_retencion")
@EntityListeners({TipoRetencionUtil.class})
public class TipoRetencion extends Entidad {
    @Column(name = "impuesto_retencion", nullable = true)
    private String impuesto_retencion;
    @Column(name = "tipo_retencion", nullable = true)
    private String tipo_retencion;
    @Column(name = "codigo_norma", nullable = true)
    private String codigo_norma;
    @Column(name = "homologacion_f_e", nullable = true)
    private String homologacion_f_e;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "porcentaje", nullable = true)
    private double porcentaje;

    public TipoRetencion(){

    }

    public TipoRetencion(long id){
        super(id);
    }

    public TipoRetencion(String codigo, String impuesto_retencion, String tipo_retencion, String codigo_norma, String homologacion_f_e, String descripcion,double porcentaje){
        super(codigo);
        this.impuesto_retencion=impuesto_retencion;
        this.tipo_retencion=tipo_retencion;
        this.codigo_norma=codigo_norma;
        this.homologacion_f_e=homologacion_f_e;
        this.descripcion=descripcion;
        this.porcentaje=porcentaje;
    }
    public TipoRetencion(List<String> datos){
        impuesto_retencion=datos.get(0)== null ? null: datos.get(0);
        tipo_retencion=datos.get(1)== null ? null: datos.get(1);
        codigo_norma=datos.get(2)== null ? null: datos.get(2);
        homologacion_f_e=datos.get(3)== null ? null: datos.get(3);
        descripcion=datos.get(4)== null ? null: datos.get(4);
        porcentaje=datos.get(5)== null ? null: Double.parseDouble(datos.get(5));
    }
    public String getImpuesto_retencion() {
        return impuesto_retencion;
    }

    public String getTipo_retencion() {
        return tipo_retencion;
    }

    public String getCodigo_norma() {
        return codigo_norma;
    }

    public String getHomologacion_f_e() {
        return homologacion_f_e;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }
}
