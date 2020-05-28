package com.proyecto.sicecuador.modelos.configuracion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.configuracion.TipoRetencionUtil;

import javax.persistence.*;

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
