package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.inventario.Kardex;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cuenta_propia")
public class CuentaPropia extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @ManyToOne
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
    public CuentaPropia(List<String> datos){
        numero=datos.get(0)== null ? null: datos.get(0);
        banco=datos.get(1)== null ? null: new Banco((long) Double.parseDouble(datos.get(1)));
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
