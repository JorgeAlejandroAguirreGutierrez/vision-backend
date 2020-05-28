package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.GeneroUtil;
import com.proyecto.sicecuador.otros.cliente.TipoContribuyenteUtil;

import javax.persistence.*;

@Entity
@Table(name = "tipo_contribuyente")
//@EntityListeners({TipoContribuyenteUtil.class})
public class TipoContribuyente extends Entidad {
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "subtipo", nullable = true)
    private String subtipo;
    @Column(name = "obligado_contabilidad", nullable = true)
    private boolean obligado_contabilidad;

    public TipoContribuyente(){
        super();
    }

    public TipoContribuyente(long id) {
        super(id);
    }

    public TipoContribuyente(String codigo, String tipo, String subtipo, boolean obligado_contabilidad){
        super(codigo);
        this.tipo=tipo;
        this.subtipo=subtipo;
        this.obligado_contabilidad=obligado_contabilidad;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSubtipo() {
        return subtipo;
    }

    public boolean isObligado_contabilidad() {
        return obligado_contabilidad;
    }
}
