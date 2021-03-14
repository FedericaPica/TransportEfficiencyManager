package com.java.tem.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
public class RisorseControllerTest {

  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  RisorseService risorseService;
  @MockBean
  private AccountService accountService;

  /*
   * Insert Risorse Tests
   */
  @Test
  @WithMockUser
  void nomeTooShort() throws Exception {
    String url = "/daticorsa/submit/conducente";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "P")
        .param("cognome", "Neri")
        .param("codiceFiscale", "NREPLA80C15H501T")).andReturn();
    String sizeErrorString = "[nome],50,2]";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("nome").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void cognomeTooShort() throws Exception {
    String url = "/daticorsa/submit/conducente";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "Paolo")
        .param("cognome", "")
        .param("codiceFiscale", "NREPLA80C15H501T")).andReturn();
    String sizeErrorString = "[nome],50,2]";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("cognnome").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void codiceFiscaleTooLong() throws Exception {
    String url = "/daticorsa/submit/conducente";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "Paolo")
        .param("cognome", "Neri")
        .param("codiceFiscale", "NREPLA80C15H501TT")).andReturn();
    String sizeErrorString = "[codiceFiscale],16,16]";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("codiceFiscale").toString().contains(sizeErrorString), "");
  }
 
  @Test
  @WithMockUser
  void conducenteCorrect() throws Exception {
    String url = "/daticorsa/submit/conducente";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "Paolo")
        .param("cognome", "Neri")
        .param("codiceFiscale", "NREPLA80C15H501T")).andReturn();
    String sizeErrorString = "";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("").toString().contains(sizeErrorString), "");
  }
  
  @Test
  @WithMockUser
  void nomeLineaTooShort() throws Exception {
    String url = "/risorse/submit/linea";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "N")
        .param("partenza", "Napoli")
        .param("destinazione", "Avellino")).andReturn();
    String sizeErrorString = "[nome],50,2]";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("nome").toString().contains(sizeErrorString), "");
  }
  
  @Test
  @WithMockUser
  void DestinazioneTooShort() throws Exception {
    String url = "/risorse/submit/linea";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "NA08")
        .param("partenza", "Napoli")
        .param("destinazione", "")).andReturn();
    String sizeErrorString = "[nome],50,2]";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("destinazione").toString().contains(sizeErrorString), "");
  }
  
  @Test
  @WithMockUser
  void InsertLineaSuccess() throws Exception {
    String url = "/risorse/submit/linea";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "NA08")
        .param("partenza", "Napoli")
        .param("destinazione", "Avellino")).andReturn();
    String sizeErrorString = "";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("").toString().contains(sizeErrorString), "");
  }
  @Test
  @WithMockUser
  void TargaTooShort() throws Exception {
    String url = "/risorse/submit/mezzo";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("capienza", "50")
        .param("targa", "PL090S")
        .param("tipo", "PullmanS")).andReturn();
    String sizeErrorString = "[targa] 10,7";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("targa").toString().contains(sizeErrorString), "");
  }
  
  @Test
  @WithMockUser
  void TipoTooShort() throws Exception {
    String url = "/risorse/submit/mezzo";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("capienza", "50")
        .param("targa", "PL090SC")
        .param("tipo", "")).andReturn();
    String sizeErrorString = "[tipo] 50,2";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("tipo").toString().contains(sizeErrorString), "");
  }
  
  @Test
  @WithMockUser
  void InserimentoMezzoSuccess() throws Exception {
    String url = "/risorse/submit/mezzo";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("capienza", "50")
        .param("targa", "PL090SC")
        .param("tipo", "PullmanS")).andReturn();
    String sizeErrorString = "";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.risorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("").toString().contains(sizeErrorString), "");
  }
  /* End of Risorsa insert tests */
}



