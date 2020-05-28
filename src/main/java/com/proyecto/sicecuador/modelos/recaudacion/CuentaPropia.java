package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "cuenta_propia")
public class CuentaPropia extends Entidad {

    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;

    public CuentaPropia(){
    }
    public CuentaPropia(String codigo, String numero, Banco banco){
        super(codigo);
        this.numero=numero;
        this.banco=banco;
    }

    public CuentaPropia(long id){
        super(id);
    }

    public String getNumero() {
        return numero;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
}
