package com.proyecto.vision.modelos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.acceso.Empresa;
import com.proyecto.vision.modelos.acceso.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import static com.proyecto.vision.Constantes.tabla_sincronizacion;

@Entity
@Table(name = tabla_sincronizacion)
@Getter
@Setter
@AllArgsConstructor
public class Sincronizacion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "mes", nullable = true)
    private String mes;
    @Column(name = "anio", nullable = true)
    private String anio;
    @Column(name = "claves_accesos", nullable = true)
    private String clavesAccesos;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    public Sincronizacion(long id){
        super(id);
    }
    public Sincronizacion(){
        super();
        this.codigo = Constantes.vacio;
        this.tipo = Constantes.vacio;
        this.mes = Constantes.vacio;
        this.anio = Constantes.vacio;
        this.clavesAccesos = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }
}
