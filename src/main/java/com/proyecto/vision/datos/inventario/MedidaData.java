package com.proyecto.vision.datos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.inventario.Medida;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.inventario.IMedidaRepository;
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
            medidas.add(new Medida("MED202301000001", "UNIDAD", "UNIDAD", "UNI", Constantes.estadoActivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000002", "UNIDAD", "MITAD", "MIT", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000003", "UNIDAD", "CUARTO", "CUA", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000004", "UNIDAD", "CAJAX10", "C10", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000005", "UNIDAD", "CAJAX12", "C12", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000006", "UNIDAD", "CAJAX20", "C20", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000007", "UNIDAD", "SIXPACK", "SIX", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000008", "UNIDAD", "JABA", "JX12", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000009", "PESO", "ARROBA", "@", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000010", "PESO", "GRAMO", "G", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000011", "PESO", "GRANO", "GRAIN", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000012", "PESO", "KILOGRAMO", "KG", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000013", "PESO", "LIBRA", "LB", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000014", "PESO", "MILIGRAMO", "MG", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000015", "PESO", "ONZA", "OZ", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000016", "PESO", "QUINTAL UK", "UK_CWT", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000017", "PESO", "QUINTAL", "CWT", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000018", "PESO", "SLUG", "SG", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000019", "PESO", "STONE", "STONE", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000020", "PESO", "TONELADA", "TON", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000021", "PESO", "TONELADA LARGA", "UK_TON", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000022", "VOLUMEN", "CENTÍMETRO CÚBICO", "CM^3", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000023", "VOLUMEN", "CUARTO DE GALÓN", "QT", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000024", "VOLUMEN", "CUCHARADA", "TBS", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000025", "VOLUMEN", "CUCHARADITA", "TSP", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000026", "VOLUMEN", "GALÓN", "GAL", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000027", "VOLUMEN", "LITRO", "L", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000028", "VOLUMEN", "METRO CÚBICO", "M^3", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000029", "VOLUMEN", "TAZA", "CUP", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000030", "VOLUMEN", "ONZA LÍQUIDA", "OZL", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000031", "VOLUMEN", "PIE CÚBICO", "FT^3", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000032", "VOLUMEN", "PINTA UK", "UK_PT", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000033", "VOLUMEN", "PINTA", "PT", Constantes.estadoInactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000034", "VOLUMEN", "PULGADA CÚBICA", "IN^3", Constantes.estadoInactivo, new Empresa(1)));
            // EMPRESA 2
            medidas.add(new Medida("MED022308000001", "UNIDAD", "UNIDAD", "UNI", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000002", "UNIDAD", "MITAD", "MIT", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000003", "UNIDAD", "CUARTO", "CUA", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000004", "UNIDAD", "CAJAX10", "C10", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000005", "UNIDAD", "CAJAX12", "C12", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000006", "UNIDAD", "CAJAX20", "C20", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000007", "UNIDAD", "SIXPACK", "SIX", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000008", "UNIDAD", "JABA", "JX12", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000009", "PESO", "ARROBA", "@", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000010", "PESO", "GRAMO", "G", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000011", "PESO", "GRANO", "GRAIN", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000012", "PESO", "KILOGRAMO", "KG", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000013", "PESO", "LIBRA", "LB", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000014", "PESO", "MILIGRAMO", "MG", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000015", "PESO", "ONZA", "OZ", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000016", "PESO", "QUINTAL UK", "UK_CWT", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000017", "PESO", "QUINTAL", "CWT", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000018", "PESO", "SLUG", "SG", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000019", "PESO", "STONE", "STONE", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000020", "PESO", "TONELADA", "TON", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000021", "PESO", "TONELADA LARGA", "UK_TON", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000022", "VOLUMEN", "CENTÍMETRO CÚBICO", "CM^3", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000023", "VOLUMEN", "CUARTO DE GALÓN", "QT", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000024", "VOLUMEN", "CUCHARADA", "TBS", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000025", "VOLUMEN", "CUCHARADITA", "TSP", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000026", "VOLUMEN", "GALÓN", "GAL", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000027", "VOLUMEN", "LITRO", "L", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000028", "VOLUMEN", "METRO CÚBICO", "M^3", Constantes.estadoActivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000029", "VOLUMEN", "TAZA", "CUP", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000030", "VOLUMEN", "ONZA LÍQUIDA", "OZL", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000031", "VOLUMEN", "PIE CÚBICO", "FT^3", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000032", "VOLUMEN", "PINTA UK", "UK_PT", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000033", "VOLUMEN", "PINTA", "PT", Constantes.estadoInactivo, new Empresa(2)));
            medidas.add(new Medida("MED022308000034", "VOLUMEN", "PULGADA CÚBICA", "IN^3", Constantes.estadoInactivo, new Empresa(2)));
            // EMPRESA 3
            medidas.add(new Medida("MED032309000001", "UNIDAD", "UNIDAD", "UNI", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000002", "UNIDAD", "MITAD", "MIT", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000003", "UNIDAD", "CUARTO", "CUA", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000004", "UNIDAD", "CAJAX10", "C10", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000005", "UNIDAD", "CAJAX12", "C12", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000006", "UNIDAD", "CAJAX20", "C20", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000007", "UNIDAD", "SIXPACK", "SIX", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000008", "UNIDAD", "JABA", "JX12", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000009", "PESO", "ARROBA", "@", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000010", "PESO", "GRAMO", "G", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000011", "PESO", "GRANO", "GRAIN", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000012", "PESO", "KILOGRAMO", "KG", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000013", "PESO", "LIBRA", "LB", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000014", "PESO", "MILIGRAMO", "MG", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000015", "PESO", "ONZA", "OZ", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000016", "PESO", "QUINTAL UK", "UK_CWT", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000017", "PESO", "QUINTAL", "CWT", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000018", "PESO", "SLUG", "SG", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000019", "PESO", "STONE", "STONE", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000020", "PESO", "TONELADA", "TON", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000021", "PESO", "TONELADA LARGA", "UK_TON", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000022", "VOLUMEN", "CENTÍMETRO CÚBICO", "CM^3", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000023", "VOLUMEN", "CUARTO DE GALÓN", "QT", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000024", "VOLUMEN", "CUCHARADA", "TBS", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000025", "VOLUMEN", "CUCHARADITA", "TSP", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000026", "VOLUMEN", "GALÓN", "GAL", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000027", "VOLUMEN", "LITRO", "L", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000028", "VOLUMEN", "METRO CÚBICO", "M^3", Constantes.estadoActivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000029", "VOLUMEN", "TAZA", "CUP", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000030", "VOLUMEN", "ONZA LÍQUIDA", "OZL", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000031", "VOLUMEN", "PIE CÚBICO", "FT^3", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000032", "VOLUMEN", "PINTA UK", "UK_PT", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000033", "VOLUMEN", "PINTA", "PT", Constantes.estadoInactivo, new Empresa(3)));
            medidas.add(new Medida("MED032309000034", "VOLUMEN", "PULGADA CÚBICA", "IN^3", Constantes.estadoInactivo, new Empresa(3)));
            rep.saveAll(medidas);
        }
    }
}
