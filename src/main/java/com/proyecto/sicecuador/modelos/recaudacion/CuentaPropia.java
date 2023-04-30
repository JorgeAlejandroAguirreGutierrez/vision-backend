package com.proyecto.sicecuador.modelos.recaudacion;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_cuenta_propia;

@Entity
@Table(name = tabla_cuenta_propia)
@Getter
@Setter
@AllArgsConstructor
public class CuentaPropia extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;

    public CuentaPropia(long id){
        super(id);
    }
    public CuentaPropia() {
        super();
        this.codigo = Constantes.vacio;
        this.numero = Constantes.vacio;
        this.estado = Constantes.activo;
    }
    public void normalizar(){
        if(this.banco == null) this.banco = new Banco();
    }
}
