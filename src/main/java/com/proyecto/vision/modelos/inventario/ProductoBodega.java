package com.proyecto.vision.modelos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "producto_bodega")
@Getter
@Setter
@AllArgsConstructor

public class ProductoBodega extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
	@ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;

    public ProductoBodega(long id){
        super(id);
    }

    public ProductoBodega(){
        super();
        this.codigo = Constantes.vacio;
        this.cantidad = Constantes.ceroId;
    }

}
