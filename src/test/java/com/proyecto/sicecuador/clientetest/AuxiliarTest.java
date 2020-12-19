package com.proyecto.sicecuador.clientetest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.repositorios.interf.cliente.IAuxiliarRepository;

import static org.hamcrest.Matchers.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.FileReader;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuxiliarTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private static String token;
    
    private static Auxiliar createAuxiliar;
    
    private static IAuxiliarRepository auxiliarRepository;
    
    @Autowired
    public void setAuxiliarRepository (IAuxiliarRepository a) {
    	auxiliarRepository= a;
    }
    
    @BeforeClass
    public static void beforeClass() {
    	String user = "admin";
        String pass = "admin";
        String basic =user+":"+pass;
        token=Base64.getEncoder().encodeToString(basic.getBytes());
    }

    @Test
    public void testA1WhenCreateAuxiliarSuccess() throws Exception {
    	String filename = AuxiliarTest.class.getResource("/testdata/cliente/auxiliar.json").getPath();
    	Gson gson = new Gson();
    	JsonReader reader = new JsonReader(new FileReader(filename));
    	Auxiliar auxiliar= gson.fromJson(reader, Auxiliar.class);
    	MvcResult result=this.mockMvc.perform(post("/api/sicecuador/auxiliar").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + token)
                .content(asJsonString(auxiliar)))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
    	createAuxiliar= new ObjectMapper().readValue(json, Auxiliar.class);
    }
    @Test
    public void testA2WhenFindAllAuxiliarSuccess() throws Exception {
        this.mockMvc.perform(get("/api/sicecuador/auxiliar").header("Authorization", "Bearer " + token)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }
    
    @Test
    public void testA3WhenFindByIdAuxiliarSuccess() throws Exception {
    	this.mockMvc.perform(get("/api/sicecuador/cliente/"+createAuxiliar.getId()).header("Authorization", "Bearer " + token)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }
    
    @Test
    public void testA4WhenUpdateClienteSuccess() throws Exception {
    	createAuxiliar.setCodigo("AUX_U01");
    	MvcResult result=this.mockMvc.perform(put("/api/sicecuador/auxiliar").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + token)
                .content(asJsonString(createAuxiliar)))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
    	createAuxiliar= new ObjectMapper().readValue(json, Auxiliar.class);
    }
    
    @AfterClass
    public static void after() throws Exception {
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
