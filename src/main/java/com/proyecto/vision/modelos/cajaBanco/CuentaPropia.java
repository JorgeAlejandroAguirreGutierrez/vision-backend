package com.proyecto.vision.modelos.cajaBanco;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_cuenta_propia;

@Entity
@Table(name = tabla_cuenta_propia)
@Getter
@Setter
@AllArgsConstructor
public class CuentaPropia extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "tipo_cuenta", nullable = true)
    private String tipoCuenta;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public CuentaPropia(long id){
        super(id);
    }
    public CuentaPropia() {
        super();
        this.codigo = Constantes.vacio;
        this.tipoCuenta = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.numero = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
        this.banco = new Banco();
    }

    public void normalizar(){
        if(this.banco == null) this.banco = new Banco();
        if(this.empresa == null) this.empresa = new Empresa();
    }
}
