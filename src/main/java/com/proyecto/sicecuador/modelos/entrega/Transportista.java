package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import static com.proyecto.sicecuador.Constantes.tabla_transportista;

@Entity
@Table(name = tabla_transportista)
@Getter
@Setter
@AllArgsConstructor
public class Transportista extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id", nullable = true)
    private TipoIdentificacion tipoIdentificacion;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public Transportista(long id){
        super(id);
    }
    public Transportista(){
        super();
        this.codigo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.estado = Constantes.activo;
    }

    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
    }
}
