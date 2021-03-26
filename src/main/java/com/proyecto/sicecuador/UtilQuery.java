package com.proyecto.sicecuador;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilQuery {
	@Autowired
    EntityManager em;

    public Optional<String> conteo(String tabla) {
        String query = "SELECT count(*) FROM (%s)";
        String sql = String.format(query, tabla);
        String conteo= (String) em.createNativeQuery(sql).getSingleResult();
        return Optional.of(conteo);
    }
}
