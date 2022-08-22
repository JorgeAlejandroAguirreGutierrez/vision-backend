package com.proyecto.sicecuador.datos.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.repositorios.contabilidad.IAfectacionContableRepository;
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
@Order(29)
@Profile({"dev","prod"})
public class AfectacionContableData implements ApplicationRunner {
    @Autowired
    private IAfectacionContableRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<AfectacionContable> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {

            List<AfectacionContable> afectaciones_contables = new ArrayList<>();
            afectaciones_contables.add(new AfectacionContable("AFC011907000001","DESCRIPCION 1","ABREVIATURA 1"));
            afectaciones_contables.add(new AfectacionContable("AFC011907000002","DESCRIPCION 2","ABREVIATURA 2"));

            rep.saveAll(afectaciones_contables);
            /*medidas_precios.add(new Medida("MED011907000001", "LIBRA", "PESO","LIBRA", "LB"));
            medidas_precios.add(new Medida("MED011907000002", "KILOGRAMO", "PESO","KILOGRAMO", "KG"));
            medidas.add(new Medida("MED011907000003", "QUINTAL US", "PESO","QUINTAL US", "CWT"));
            medidas.add(new Medida("MED011907000004", "QUINTAL UK", "PESO","QUINTAL UK", "UK_CWT"));
            medidas.add(new Medida("MED011907000005", "STONE", "PESO","STONE", "STONE"));
            medidas.add(new Medida("MED011907000006", "ONZA", "PESO","ONZA", "OZ"));
            medidas.add(new Medida("MED011907000007", "GRANO", "PESO","GRANO", "GRAIN"));
            medidas.add(new Medida("MED011907000008", "GRAMO", "PESO","GRAMO", "GR"));
            medidas.add(new Medida("MED011907000009", "SLUG", "PESO","SLUG", "TON"));
            medidas.add(new Medida("MED011907000010", "TONELADA", "PESO","TONELADA LARGA", "UK_TON"));
            medidas.add(new Medida("MED011907000011", "TONELADA LARGA", "PESO","QUINTAL US", "CWT"));
            medidas.add(new Medida("MED011907000012", "MILIGRAMO", "PESO","MILIGRAMO", "MG"));
            */
        }
    }
}
