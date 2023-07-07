package com.proyecto.vision.clientetest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.vision.servicios.interf.cliente.IClienteService;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathCliente;

import static org.hamcrest.Matchers.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private static String token;
    
    
    private static IClienteService clienteService;
    
    @Autowired
    public void setClienteService (IClienteService c) {
    	clienteService= c;
    }
    
    @BeforeClass
    public static void beforeClass() {
    	String user = "admin";
        String pass = "admin";
        String basic =user+":"+pass;
        token=Base64.getEncoder().encodeToString(basic.getBytes());
    }

    @Test
    public void testA1WhenCreateClienteSuccess() throws Exception {
    	String filename = ClienteTest.class.getResource("/testdata/cliente/cliente.json").getPath();
    	String cliente=readFileAsString(filename);
    	this.mockMvc.perform(post(contexto+pathCliente).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + token)
                .content(cliente))
                .andExpect(status().isOk());
    }
    @Test
    public void testA2WhenFindAllClienteSuccess() throws Exception {
        this.mockMvc.perform(get(contexto+pathCliente).header("Authorization", "Basic " + token)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }
    
    @Test
    public void testA3WhenFindByIdClienteSuccess() throws Exception {
    	this.mockMvc.perform(get(contexto+pathCliente+"/"+"1").header("Authorization", "Basic " + token)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }
    
    @Test
    public void testA4WhenUpdateClienteSuccess() throws Exception {
    	String filename = ClienteTest.class.getResource("/testdata/cliente/clienteUpdate.json").getPath();
    	String cliente=readFileAsString(filename);
    	this.mockMvc.perform(put(contexto+pathCliente).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + token)
                .content(cliente))
                .andExpect(status().isOk());
    }
    
    @AfterClass
    public static void after() throws Exception {
    	//clienteService.eliminar(createCliente);
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}
