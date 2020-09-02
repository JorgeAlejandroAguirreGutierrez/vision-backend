package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.modelos.recaudacion.Credito;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.ICreditoRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            if (credito.isSin_intereses()){
                //Primera Cuota
                int cuota=1;
                credito.setFecha_consecion(new Date());
                Date fecha_pago=credito.getFecha_primera_cuota();
                int numero_dias=credito.getPeriodicidad_numero();
                double dividendo=Math.rint((credito.getSaldo()/credito.getCuotas())*100d)/100d;
                double capital_inicio_periodo=credito.getSaldo();
                double capital=credito.getSaldo();
                double intereses_periodo= 0;
                double valor_cuota=dividendo;
                double saldo_capital=Math.rint((credito.getSaldo()-valor_cuota)*100d)/100d;
                Amortizacion amortizacion=new Amortizacion(null, cuota, fecha_pago, numero_dias, capital_inicio_periodo,
                        capital, intereses_periodo, valor_cuota, saldo_capital, credito);
                amortizaciones.add(amortizacion);
                //Fin Primera Cuota
                for (int i=1; i<credito.getCuotas(); i++){
                    cuota=i+1;
                    valor_cuota=dividendo;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fecha_pago);
                    calendar.add(Calendar.DAY_OF_YEAR, credito.getPeriodicidad_numero());
                    fecha_pago=calendar.getTime();
                    numero_dias=credito.getPeriodicidad_numero();
                    capital_inicio_periodo=Math.rint((amortizaciones.get(i-1).getSaldo_capital())*100d)/100d;
                    intereses_periodo=0;
                    capital=dividendo;
                    saldo_capital=Math.rint((capital_inicio_periodo-capital)*100d)/100d;
                    amortizacion=new Amortizacion(null, cuota, fecha_pago, numero_dias, capital_inicio_periodo,
                            capital, intereses_periodo, valor_cuota, saldo_capital, credito);
                    amortizaciones.add(amortizacion);
                }
            }
            if (credito.getTipo().equals(Constantes.tabla_amortizacion_francesa)){
                //Cero Cuota
                int cuota=0;
                credito.setFecha_consecion(new Date());
                Date fecha_pago=credito.getFecha_consecion();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha_pago);
                calendar.add(Calendar.DAY_OF_YEAR, credito.getPeriodicidad_numero());
                fecha_pago=calendar.getTime();
                int numero_dias= (int)((credito.getFecha_primera_cuota().getTime()-fecha_pago.getTime())/86400000);
                double capital_inicio_periodo=0;
                double capital=0;
                double intereses_periodo=Math.rint((credito.getSaldo()*((credito.getTasa_interes_anual()/100)/360)*numero_dias)*100d)/100d;
                double valor_cuota=intereses_periodo;
                double saldo_capital=0;
                Amortizacion amortizacion=new Amortizacion(null, cuota, fecha_pago, numero_dias, capital_inicio_periodo,
                        capital, intereses_periodo, valor_cuota, saldo_capital, credito);
                amortizaciones.add(amortizacion);
                //Fin cero cuota
                //Primera Cuota
                cuota=1;
                fecha_pago=credito.getFecha_primera_cuota();
                numero_dias=credito.getPeriodicidad_numero();
                double dividendo=Math.rint((credito.getSaldo()*((credito.getTasa_periodo()/100)/(1-Math.pow((1+(credito.getTasa_periodo()/100)), - credito.getCuotas()))))*100d)/100d;
                capital_inicio_periodo=credito.getSaldo();
                valor_cuota=dividendo;
                intereses_periodo= Math.rint((capital_inicio_periodo*((credito.getTasa_periodo()/100)))*100d)/100d;
                capital=Math.rint((valor_cuota-intereses_periodo)*100d)/100d;
                saldo_capital=Math.rint((capital_inicio_periodo-capital)*100d)/100d;
                credito.setFecha_consecion(new Date());
                amortizacion=new Amortizacion(null, cuota, fecha_pago, numero_dias, capital_inicio_periodo,
                        capital, intereses_periodo, valor_cuota, saldo_capital, credito);
                amortizaciones.add(amortizacion);
                //Fin Primera Cuota
                for (int i=1; i<credito.getCuotas(); i++){
                    cuota=i+1;
                    valor_cuota=dividendo;
                    calendar = Calendar.getInstance();
                    calendar.setTime(fecha_pago);
                    calendar.add(Calendar.DAY_OF_YEAR, credito.getPeriodicidad_numero());
                    fecha_pago=calendar.getTime();
                    numero_dias=credito.getPeriodicidad_numero();
                    capital_inicio_periodo=Math.rint((amortizaciones.get(i-1).getCapital_inicio_periodo()-amortizaciones.get(i-1).getCapital())*100d)/100d;
                    intereses_periodo=Math.rint((capital_inicio_periodo*credito.getTasa_periodo()/100)*100d)/100d;
                    capital=Math.rint((valor_cuota-intereses_periodo)*100d)/100d;
                    saldo_capital=Math.rint((capital_inicio_periodo-capital)*100d)/100d;
                    amortizacion=new Amortizacion(null, cuota, fecha_pago, numero_dias, capital_inicio_periodo,
                            capital, intereses_periodo, valor_cuota, saldo_capital, credito);
                    amortizaciones.add(amortizacion);
                }
            }
            if (credito.getTipo().equals(Constantes.tabla_amortizacion_alemana)){
                //Primera Cuota
                int cuota=1;
                Date fecha_pago=credito.getFecha_primera_cuota();
                credito.setFecha_consecion(new Date());
                int numero_dias=(int) ((fecha_pago.getTime()-credito.getFecha_consecion().getTime())/86400000);
                if (numero_dias<0) numero_dias=0;
                double dividendo=Math.rint((credito.getSaldo()/credito.getCuotas())*100d)/100d;
                double capital_inicio_periodo=credito.getSaldo();
                double capital=dividendo;
                double intereses_periodo= Math.rint((credito.getSaldo()*((credito.getTasa_interes_anual()/100)/360)*numero_dias)*100d)/100d;
                double valor_cuota=capital+intereses_periodo;
                double saldo_capital=Math.rint((credito.getSaldo()-capital)*100d)/100d;
                credito.setFecha_consecion(new Date());
                Amortizacion amortizacion=new Amortizacion(null, cuota, fecha_pago, numero_dias, capital_inicio_periodo,
                        capital, intereses_periodo, valor_cuota, saldo_capital, credito);
                amortizaciones.add(amortizacion);
                //Fin Primera Cuota
                for (int i=1; i<credito.getCuotas(); i++){
                    cuota=i+1;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fecha_pago);
                    calendar.add(Calendar.DAY_OF_YEAR, credito.getPeriodicidad_numero());
                    fecha_pago=calendar.getTime();
                    numero_dias=credito.getPeriodicidad_numero();
                    capital_inicio_periodo=Math.rint((amortizaciones.get(i-1).getCapital_inicio_periodo()-amortizaciones.get(i-1).getCapital())*100d)/100d;
                    capital=dividendo;
                    intereses_periodo=Math.rint((amortizaciones.get(i-1).getSaldo_capital()*((credito.getTasa_interes_anual()/100)/360)*numero_dias)*100d)/100d;
                    valor_cuota=Math.rint((capital-intereses_periodo)*100d)/100d;
                    saldo_capital=Math.rint((amortizaciones.get(i-1).getSaldo_capital()-capital)*100d)/100d;
                    amortizacion=new Amortizacion(null, cuota, fecha_pago, numero_dias, capital_inicio_periodo,
                            capital, intereses_periodo, valor_cuota, saldo_capital, null);
                    amortizaciones.add(amortizacion);
                }
            }
            credito.setAmortizaciones(amortizaciones);
            return Optional.of(credito);
        }
        return null;
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Credito> creditos=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,2);
            for (List<String> datos: info) {
                Credito credito = new Credito(datos);
                creditos.add(credito);
            }
            if(creditos.isEmpty()){
                return false;
            }
            rep.saveAll(creditos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
