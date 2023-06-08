package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
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
            medidas.add(new Medida("MED202301000027", "UNIDAD", "UNIDAD", "UNI", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000028", "UNIDAD", "MITAD", "MIT", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000029", "UNIDAD", "CUARTO", "CUA", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000030", "UNIDAD", "CAJAX10", "C10", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000031", "UNIDAD", "CAJAX12", "C12", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000032", "UNIDAD", "CAJAX20", "C20", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000033", "UNIDAD", "SIXPACK", "SIX", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000034", "UNIDAD", "JABA", "JX12", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000001", "PESO", "ARROBA", "@", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000002", "PESO", "GRAMO", "G", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000003", "PESO", "GRANO", "GRAIN", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000004", "PESO", "KILOGRAMO", "KG", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000005", "PESO", "LIBRA", "LB", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000006", "PESO", "MILIGRAMO", "MG", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000007", "PESO", "ONZA", "OZ", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000008", "PESO", "QUINTAL UK", "UK_CWT", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000009", "PESO", "QUINTAL", "CWT", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000010", "PESO", "SLUG", "SG", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000011", "PESO", "STONE", "STONE", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000012", "PESO", "TONELADA", "TON", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000013", "PESO", "TONELADA LARGA", "UK_TON", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000014", "VOLUMEN", "CENTÍMETRO CÚBICO", "CM^3", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000015", "VOLUMEN", "CUARTO DE GALÓN", "QT", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000016", "VOLUMEN", "CUCHARADA", "TBS", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000017", "VOLUMEN", "CUCHARADITA", "TSP", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000018", "VOLUMEN", "GALÓN", "GAL", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000019", "VOLUMEN", "LITRO", "L", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000020", "VOLUMEN", "METRO CÚBICO", "M^3", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000021", "VOLUMEN", "TAZA", "CUP", Constantes.activo, new Empresa(1)));
            medidas.add(new Medida("MED202301000022", "VOLUMEN", "ONZA LÍQUIDA", "OZL", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000023", "VOLUMEN", "PIE CÚBICO", "FT^3", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000024", "VOLUMEN", "PINTA UK", "UK_PT", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000025", "VOLUMEN", "PINTA", "PT", Constantes.inactivo, new Empresa(1)));
            medidas.add(new Medida("MED202301000026", "VOLUMEN", "PULGADA CÚBICA", "IN^3", Constantes.inactivo, new Empresa(1)));

            medidas.add(new Medida("MED202301000027", "UNIDAD", "UNIDAD", "UNI", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000028", "UNIDAD", "MITAD", "MIT", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000029", "UNIDAD", "CUARTO", "CUA", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000030", "UNIDAD", "CAJAX10", "C10", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000031", "UNIDAD", "CAJAX12", "C12", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000032", "UNIDAD", "CAJAX20", "C20", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000033", "UNIDAD", "SIXPACK", "SIX", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000034", "UNIDAD", "JABA", "JX12", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000001", "PESO", "ARROBA", "@", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000002", "PESO", "GRAMO", "G", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000003", "PESO", "GRANO", "GRAIN", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000004", "PESO", "KILOGRAMO", "KG", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000005", "PESO", "LIBRA", "LB", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000006", "PESO", "MILIGRAMO", "MG", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000007", "PESO", "ONZA", "OZ", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000008", "PESO", "QUINTAL UK", "UK_CWT", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000009", "PESO", "QUINTAL", "CWT", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000010", "PESO", "SLUG", "SG", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000011", "PESO", "STONE", "STONE", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000012", "PESO", "TONELADA", "TON", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000013", "PESO", "TONELADA LARGA", "UK_TON", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000014", "VOLUMEN", "CENTÍMETRO CÚBICO", "CM^3", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000015", "VOLUMEN", "CUARTO DE GALÓN", "QT", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000016", "VOLUMEN", "CUCHARADA", "TBS", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000017", "VOLUMEN", "CUCHARADITA", "TSP", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000018", "VOLUMEN", "GALÓN", "GAL", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000019", "VOLUMEN", "LITRO", "L", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000020", "VOLUMEN", "METRO CÚBICO", "M^3", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000021", "VOLUMEN", "TAZA", "CUP", Constantes.activo, new Empresa(2)));
            medidas.add(new Medida("MED202301000022", "VOLUMEN", "ONZA LÍQUIDA", "OZL", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000023", "VOLUMEN", "PIE CÚBICO", "FT^3", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000024", "VOLUMEN", "PINTA UK", "UK_PT", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000025", "VOLUMEN", "PINTA", "PT", Constantes.inactivo, new Empresa(2)));
            medidas.add(new Medida("MED202301000026", "VOLUMEN", "PULGADA CÚBICA", "IN^3", Constantes.inactivo, new Empresa(2)));

            rep.saveAll(medidas);
        }
    }
}
