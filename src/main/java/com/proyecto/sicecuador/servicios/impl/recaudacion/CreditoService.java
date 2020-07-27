package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import com.proyecto.sicecuador.modelos.recaudacion.Credito;
import com.proyecto.sicecuador.modelos.recaudacion.ModeloTabla;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.ICreditoRepository;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IModeloTablaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CreditoService implements ICreditoService {
    @Autowired
    private ICreditoRepository rep;
    @Override
    public Credito crear(Credito credito) {
        return rep.save(credito);
    }

    @Override
    public Credito actualizar(Credito credito) {
        return rep.save(credito);
    }

    @Override
    public Credito eliminar(Credito credito) {
        rep.deleteById(credito.getId());
        return credito;
    }

    @Override
    public Optional<Credito> obtener(Credito credito) {
        return rep.findById(credito.getId());
    }

    @Override
    public List<Credito> consultar() {
        return rep.findAll();
    }
    @Override
    public Optional<Credito> construir(Credito credito) {
        if(credito.getSaldo()>0){
            List<Amortizacion> amortizaciones=new ArrayList<>();
            if (credito.getTipo().equals(Constantes.tabla_amortizacion_francesa)){
                double tasa_periodo=credito.getTasa_interes_anual()/credito.getPeriodicidad_total();
                credito.setTasa_periodo(tasa_periodo);
                //Primera Cuota
                int cuota=1;
                Date fecha_pago=credito.getFecha_primera_cuota();
                int numero_dias=credito.getPeriodicidad_numero();
                double dividendo=Math.rint((credito.getSaldo()*((credito.getTasa_periodo()/100)/(1-Math.pow((1+(credito.getTasa_periodo()/100)), - credito.getCuotas()))))*100d)/100d;
                double capital_inicio_periodo=credito.getSaldo();
                double valor_cuota=dividendo;
                double intereses_periodo= Math.rint((capital_inicio_periodo*((credito.getTasa_periodo()/100)))*100d)/100d;
                double capital=Math.rint((valor_cuota-intereses_periodo)*100d)/100d;
                double saldo_capital=Math.rint((capital_inicio_periodo-capital)*100d)/100d;
                Amortizacion amortizacion=new Amortizacion(null, cuota, fecha_pago, numero_dias, capital_inicio_periodo,
                        capital, intereses_periodo, valor_cuota, saldo_capital, credito);
                amortizaciones.add(amortizacion);
                //Fin Primera Cuota
                for (int i=1; i<credito.getCuotas(); i++){
                    cuota=i+1;
                    if (credito.getPeriodicidad().equals(Constantes.periodo_mensual)){
                        valor_cuota=dividendo;
                        Calendar calendar = Calendar.getInstance();
                        credito.setFecha_consecion(new Date());
                        calendar.setTime(credito.getFecha_consecion());
                        calendar.add(Calendar.DAY_OF_YEAR, credito.getPeriodicidad_numero());
                        fecha_pago=calendar.getTime();
                        numero_dias=credito.getPeriodicidad_numero();
                        capital_inicio_periodo=Math.rint((amortizaciones.get(i-1).getCapital_inicio_periodo()-amortizaciones.get(i-1).getCapital())*100d)/100d;
                        intereses_periodo=Math.rint((capital_inicio_periodo*credito.getTasa_periodo()/100)*100d)/100d;
                        capital=Math.rint((valor_cuota-intereses_periodo)*100d)/100d;
                        saldo_capital=Math.rint((capital_inicio_periodo-capital)*100d)/100d;
                    }
                    amortizacion=new Amortizacion(null, cuota, fecha_pago, numero_dias, capital_inicio_periodo,
                            capital, intereses_periodo, valor_cuota, saldo_capital, credito);
                    amortizaciones.add(amortizacion);
                }
            }
            credito.setAmortizaciones(amortizaciones);
            return Optional.of(credito);
        }
        return null;
    }
}
