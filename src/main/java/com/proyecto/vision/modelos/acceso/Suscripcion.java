package com.proyecto.vision.modelos.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

import static com.proyecto.vision.Constantes.tabla_suscripcion;

@Entity
@Table(name = tabla_suscripcion)
@Getter
@Setter
@AllArgsConstructor
public class Suscripcion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha_inicial", nullable = true)
    private Date fechaInicial;
    @Column(name = "fecha_final", nullable = true)
    private Date fechaFinal;
    @Column(name = "conteo_comprobantes", nullable = true)
    private long conteoComprobantes;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "paquete_id", nullable = true)
    private Paquete paquete;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    //PAGO DE LA SUSCRIPCION
    @Column(name = "numero_transaccion", nullable = true)
    private String numeroTransaccion;
    @Column(name = "fecha_transaccion", nullable = true)
    private Date fechaTransaccion;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;

    public Suscripcion(long id){
        super(id);
    }
    public Suscripcion(){
        super();
        this.codigo = Constantes.vacio;
        this.fechaInicial = new Date();
        this.fechaFinal = new Date();
        this.conteoComprobantes = Constantes.ceroId;
        this.estado = Constantes.estadoActivo;
    }

    public void normalizar(){

    }
}
