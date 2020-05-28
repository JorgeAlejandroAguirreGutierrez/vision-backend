package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.repositorios.interf.recaudacion.ITransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(46)
public class TransferenciaData implements ApplicationRunner {
    @Autowired
    private ITransferenciaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<DepositoTransferencia> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<DepositoTransferencia> depositos_transferencias = new ArrayList<>();
            depositos_transferencias.add(new DepositoTransferencia("DEP000001", "DEPOSITO", "00012312312", Date.valueOf("2019-08-28"), 5.00, new Recaudacion(1), new Banco(1)));
            depositos_transferencias.add(new DepositoTransferencia("TRA000001", "TRANSFERENCIA", "0001231234242", Date.valueOf("2019-08-28"), 5.00, new Recaudacion(1), new Banco(2)));
            depositos_transferencias.add(new DepositoTransferencia("DEP000002", "DEPOSITO", "00012312312", Date.valueOf("2019-08-28"), 5.00, new Recaudacion(2), new Banco(3)));
            depositos_transferencias.add(new DepositoTransferencia("DEP000003", "DEPOSITO", "1312324236", Date.valueOf("2019-08-28"), 15.00, new Recaudacion(2), new Banco(4)));
            rep.saveAll(depositos_transferencias);
        }*/
    }
}
