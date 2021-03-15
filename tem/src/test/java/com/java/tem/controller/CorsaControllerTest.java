package com.java.tem.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorseService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import com.java.tem.model.programmacorseservice.repository.ProgrammaManualeMaker;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
public class CorsaControllerTest {

  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private RisorseService risorseService;
  @MockBean
  private AccountService accountService;
  @InjectMocks
  private CorsaController CorsaController;
  @MockBean
  private ProgrammaCorseService programmaCorseService;
  @InjectMocks
  private CorsaService corsaService;
  @MockBean
  private ProgrammaManualeMaker programmaManualeMaker;

  /*
   * Insert  Corsa Tests
   */
  @Test
  @WithMockUser
  void lineaNotSelected() throws Exception {
    String url = "/corsa/submit/1/";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
    		    .param("corsa", "09:00:00")
    	        .param("mezzo", "PullmanS")
    	        .param("linea", "")
    	        .param("conducente", "Paolo Neri")
    	        .param("programmaCorseId", "1")
    	        .param("andata", "true")).andReturn();
    String sizeErrorString = "Nessuna linea selezionata";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.corsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("linea").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void orarioBadFormat() throws Exception {
	String url = "/corsa/submit/1";
	List<Long> mezzi = new ArrayList<>();
	List<Long> conducenti = new ArrayList<>();
	mezzi.add(1L);
	conducenti.add(1L);
    MvcResult result = mockMvc.perform(post(url, "1").with(csrf())
        .flashAttr("corsa", new Corsa())
        .param("orario", "9")
        .param("mezzo", mezzi.toString())
        .param("linea", "1L")
        .param("conducente", conducenti.toString())
        .param("programmaCorseId", "1L")
        .param("andata", "true")).andReturn();
        //.andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException, " "));
    Object bindingResObject = result.getModelAndView();
    System.out.println(bindingResObject);
    BindingResult bindingResult = (BindingResult) bindingResObject;

  }

  @Test
  @WithMockUser
  void mezzoNotSelected() throws Exception {
	String url = "/corsa/submit//1/";
    MvcResult result =
        ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(post(url).with(csrf())))
        		.param("corsa", "09:00:00")
                .param("mezzo", "")
                .param("linea", "NA08")
                .param("conducente", "Paolo Neri")
                .param("programmaCorseId", "1")
                .param("andata", "true")).andReturn();
    String sizeErrorString = "Il campo mezzo corsa non rispetta la lunghezza minima";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("mezzo").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void success() throws Exception {
    String url = "/corsa/submit//1/";
    MvcResult result = ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(post(url).with(csrf())))
        		.param("corsa", "09:00:00")
                .param("mezzo", "PullmanS")
                .param("linea", "NA08")
                .param("conducente", "Paolo Neri")
                .param("programmaCorseId", "1")
                .param("andata", "true")).andReturn();
    String sizeErrorString = "";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.Corsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("mezzo").toString().contains(sizeErrorString), "");
  }
  
  /* End of Corsa insert tests */
}



