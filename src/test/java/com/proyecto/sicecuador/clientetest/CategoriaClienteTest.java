package com.proyecto.sicecuador.clientetest;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.path_categoria_cliente;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.repositorios.interf.cliente.ICategoriaClienteRepository;

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
import org.springframework.test.web.servlet.MvcResult;

import java.io.FileReader;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoriaClienteTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private static String token;
    
    private static CategoriaCliente createCategoriaCliente;
    
    private static ICategoriaClienteRepository categoriaClienteRepository;
    
    @Autowired
    public void setCategoriaClienteRepository (ICategoriaClienteRepository a) {
    	categoriaClienteRepository= a;
    }
    
    @BeforeClass
    public static void beforeClass() {
    	String user = "admin";
        String pass = "admin";
        String basic =user+":"+pass;
        token=Base64.getEncoder().encodeToString(basic.getBytes());
    }

    @Test
    public void testA1WhenCreateCategoriaClienteSuccess() throws Exception {
    	String filename = CategoriaClienteTest.class.getResource("/testdata/cliente/categoria_cliente.json").getPath();
    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
    	JsonReader reader = new JsonReader(new FileReader(filename));
    	CategoriaCliente categoria_cliente= gson.fromJson(reader, CategoriaCliente.class);
    	MvcResult result=this.mockMvc.perform(post(contexto+path_categoria_cliente).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + token)
                .content(asJsonString(categoria_cliente)))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Respuesta respuesta= gson.fromJson(json, Respuesta.class);
    	String objeto= gson.toJson(respuesta.getResultado());
    	createCategoriaCliente=gson.fromJson(objeto, CategoriaCliente.class);
    }
    @Test
    public void testA2WhenFindAllCategoriaClienteSuccess() throws Exception {
        this.mockMvc.perform(get(contexto+path_categoria_cliente).header("Authorization", "Basic " + token)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }
    
    @Test
    public void testA3WhenFindByIdCategoriaClienteSuccess() throws Exception {
    	this.mockMvc.perform(get(contexto+path_categoria_cliente+"/"+createCategoriaCliente.getId()).header("Authorization", "Basic " + token)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }
    
    @Test
    public void testA4WhenUpdateCategoriaClienteSuccess() throws Exception {
    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
    	createCategoriaCliente.setCodigo("CAT_U01");
    	MvcResult result=this.mockMvc.perform(put(contexto+path_categoria_cliente).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + token)
                .content(asJsonString(createCategoriaCliente)))
                .andExpect(status().isOk())
                .andReturn();
    	String json = result.getResponse().getContentAsString();
        Respuesta respuesta= gson.fromJson(json, Respuesta.class);
    	String objeto= gson.toJson(respuesta.getResultado());
    	createCategoriaCliente=gson.fromJson(objeto, CategoriaCliente.class);
    }
    
    @AfterClass
    public static void after() throws Exception {
    	categoriaClienteRepository.deleteById(createCategoriaCliente.getId());
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
