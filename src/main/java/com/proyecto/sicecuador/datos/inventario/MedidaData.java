package com.proyecto.sicecuador.datos.inventario;

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
            medidas.add(new Medida("MED011907000001", "LIBRA", "PESO","LIBRA", "LB"));
            medidas.add(new Medida("MED011907000002", "KILOGRAMO", "PESO","KILOGRAMO", "KG"));
            medidas.add(new Medida("MED011907000003", "QUINTAL US", "PESO","QUINTAL US", "CWT"));
            medidas.add(new Medida("MED011907000004", "QUINTAL UK", "PESO","QUINTAL UK", "UK_CWT"));
            medidas.add(new Medida("MED011907000005", "STONE", "PESO","STONE", "STONE"));
            medidas.add(new Medida("MED011907000006", "ONZA", "PESO","ONZA", "OZ"));
            medidas.add(new Medida("MED011907000007", "GRANO", "PESO","GRANO", "GRAIN"));
            medidas.add(new Medida("MED011907000008", "GRAMO", "PESO","GRAMO", "GR"));
            medidas.add(new Medida("MED011907000009", "SLUG", "PESO","SLUG", "TON"));
            medidas.add(new Medida("MED011907000010", "TONELADA", "PESO","TONELADA", "UK_TON"));
            medidas.add(new Medida("MED011907000011", "TONELADA LARGA", "PESO","TONELADA LARGA", "CWT"));
            medidas.add(new Medida("MED011907000012", "MILIGRAMO", "PESO","MILIGRAMO", "MG"));
            medidas.add(new Medida("MED011907000013", "UNIDAD", "UNIDAD","UNIDAD", "UNI"));
            medidas.add(new Medida("MED011907000014", "CAJAX12", "UNIDAD","CAJA X 12", "CX12"));
            medidas.add(new Medida("MED011907000015", "CAJAX500", "UNIDAD","CAJA X 500", "CX500"));
            medidas.add(new Medida("MED011907000016", "SIXPACK", "UNIDAD","SIXPACK", "SIX"));
            medidas.add(new Medida("MED011907000017", "JAVAX12", "UNIDAD","JAVA X 12", "JX12"));
           
            rep.saveAll(medidas);
        }
    }
}
