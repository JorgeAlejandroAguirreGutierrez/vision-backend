package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.repositorios.inventario.IMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(39)
@Profile({"dev","prod"})
public class MedidaData implements ApplicationRunner {
    @Autowired
    private IMedidaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Medida> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Medida> medidas = new ArrayList<>();
            medidas.add(new Medida("MED011907000013", "UNIDAD","UNIDAD", "UNI", Constantes.activo));
            medidas.add(new Medida("MED011907000014", "UNIDAD","CAJA X 12", "CX12", Constantes.activo));
            medidas.add(new Medida("MED011907000015", "UNIDAD","CAJA X 500", "CX500", Constantes.activo));
            medidas.add(new Medida("MED011907000016", "UNIDAD","SIXPACK", "SIX", Constantes.activo));
            medidas.add(new Medida("MED011907000017", "UNIDAD","JAVA X 12", "JX12", Constantes.activo));
            medidas.add(new Medida("MED011907000001", "PESO","LIBRA", "LB", Constantes.activo));
            medidas.add(new Medida("MED011907000002", "PESO","KILOGRAMO", "KG", Constantes.activo));
            medidas.add(new Medida("MED011907000003", "PESO","QUINTAL US", "CWT", Constantes.activo));
            medidas.add(new Medida("MED011907000004", "PESO","QUINTAL UK", "UK_CWT", Constantes.activo));
            medidas.add(new Medida("MED011907000005", "PESO","STONE", "STONE", Constantes.activo));
            medidas.add(new Medida("MED011907000006", "PESO","ONZA", "OZ", Constantes.activo));
            medidas.add(new Medida("MED011907000007", "PESO","GRANO", "GRAIN", Constantes.activo));
            medidas.add(new Medida("MED011907000008", "PESO","GRAMO", "GR", Constantes.activo));
            medidas.add(new Medida("MED011907000009", "PESO","SLUG", "TON", Constantes.activo));
            medidas.add(new Medida("MED011907000010", "PESO","TONELADA", "UK_TON", Constantes.activo));
            medidas.add(new Medida("MED011907000011", "PESO","TONELADA LARGA", "CWT", Constantes.activo));
            medidas.add(new Medida("MED011907000012", "PESO","MILIGRAMO", "MG", Constantes.activo));
            rep.saveAll(medidas);
        }
    }
}
