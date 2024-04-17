package com.proyecto.vision.modelos.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.proyecto.vision.Constantes.tabla_paquete;

@Entity
@Table(name = tabla_paquete)
@Getter
@Setter
@AllArgsConstructor
public class Paquete extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "minimo_comprobantes", nullable = true)
    private long minimoComprobantes;
    @Column(name = "maximo_comprobantes", nullable = true)
    private long maximoComprobantes;
    @Column(name = "valor_total", nullable = true)
    private double valorTotal;
    @Column(name = "valor_anual", nullable = true)
    private double valorAnual;
    @Column(name = "valor_minimo", nullable = true)
    private double valorMinimo;
    @Column(name = "valor_maximo", nullable = true)
    private double valorMaximo;
    @Column(name = "valor_puesta_inicial", nullable = true)
    private double valorPuestaInicial;
    @Column(name = "porcentaje_comision", nullable = true)
    private double porcentajeComision;
    @Column(name = "comision", nullable = true)
    private double comision;
    @Column(name = "cantidad_usuario_recaudacion", nullable = true)
    private long cantidadUsuarioRecaudacion;
    @Column(name = "cantidad_usuario_gerente", nullable = true)
    private long cantidadUsuarioGerente;
    @Column(name = "cantidad_usuario_administrador", nullable = true)
    private long cantidadUsuarioAdministrador;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "estado", nullable = true)
    private String estado;

    public Paquete(long id){
        super(id);
    }

    public Paquete(){
        super();
        this.codigo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.minimoComprobantes = Constantes.ceroId;
        this.maximoComprobantes = Constantes.ceroId;
        this.valorTotal = Constantes.cero;
        this.valorAnual = Constantes.cero;
        this.valorMinimo = Constantes.cero;
        this.valorMaximo = Constantes.cero;
        this.valorPuestaInicial = Constantes.cero;
        this.porcentajeComision = Constantes.cero;
        this.comision = Constantes.cero;
        this.cantidadUsuarioRecaudacion = Constantes.ceroId;
        this.cantidadUsuarioGerente = Constantes.ceroId;
        this.cantidadUsuarioAdministrador = Constantes.ceroId;
        this.tipo = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }

    public void normalizar(){

    }
}
