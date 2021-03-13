package com.java.tem.controller;

import com.java.tem.model.accountservice.entity.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DatiCorsaControllerTest {
  @InjectMocks
  private AccountController accountController;
  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AccountService accountService;
  @MockBean
  private DatiCorsaController datiCorsaController;
  
  @Test
void LineaTooShort() throws Exception {
    String url = "/daticorsa/add";
    MvcResult result = ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(MockMvcRequestBuilders.post(url).with(csrf())))
        .param("lineaCorsa", "N")
        .param("orarioCorsa", "09:00:00")
        .param("numeroPosti", "50")
        .param("passeggeriSaliti", "50")
        .param("passeggeriNonSaliti", "13")
        .param("traffico", "true")
        .param("andata", "true")).andReturn();
    String sizeErrorString = "Il campo Linea corsa non rispetta la lunghezza minima";
    Object bindingResObject = (Object) result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("lineaCorsa").toString().contains(sizeErrorString), "");
  }
  
  @Test
void OrarioTooShort() throws Exception {
    String url = "/daticorsa/add";
    MvcResult result = ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(MockMvcRequestBuilders.post(url).with(csrf())))
        .param("lineaCorsa", "NA08")
        .param("orarioCorsa", "9")
        .param("numeroPosti", "50")
        .param("passeggeriSaliti", "50")
        .param("passeggeriNonSaliti", "13")
        .param("traffico", "true")
        .param("andata", "true")).andReturn();
    String sizeErrorString = "Il campo Orario corsa non rispetta la lunghezza minima";
    Object bindingResObject = (Object) result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("orarioCorsa").toString().contains(sizeErrorString), "");
  }
  
  @Test
void PostiTooLong() throws Exception {
    String url = "/daticorsa/add";
    MvcResult result = ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(MockMvcRequestBuilders.post(url).with(csrf())))
        .param("lineaCorsa", "NA08")
        .param("orarioCorsa", "09:00:00")
        .param("numeroPosti", "50")
        .param("passeggeriSaliti", "50")
        .param("passeggeriNonSaliti", "13")
        .param("traffico", "true")
        .param("andata", "true")).andReturn();
    String sizeErrorString = "Il campo Orario corsa non rispetta la lunghezza minima";
    Object bindingResObject = (Object) result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("orarioCorsa").toString().contains(sizeErrorString), "");
  }
  
  @Test
  void SalitiWrongFormat() throws Exception {
    String url = "/daticorsa/add";
    MvcResult result = ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(MockMvcRequestBuilders.post(url).with(csrf())))
        .param("lineaCorsa", "NA08")
        .param("orarioCorsa", "09:00:00")
        .param("numeroPosti", "50")
        .param("passeggeriSaliti", "--")
        .param("passeggeriNonSaliti", "13")
        .param("traffico", "true")
        .param("andata", "true")).andReturn();
    String sizeErrorString = "Il campo Passeggeri saliti non rispetta il formato";
    Object bindingResObject = (Object) result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("orarioCorsa").toString().contains(sizeErrorString), "");
  }
  @Test
  void DatiCorsaCorrect() throws Exception {
    String url = "/daticorsa/add";
    MvcResult result = ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(MockMvcRequestBuilders.post(url).with(csrf())))
        .param("lineaCorsa", "NA08")
        .param("orarioCorsa", "09:00:00")
        .param("numeroPosti", "50")
        .param("passeggeriSaliti", "50")
        .param("passeggeriNonSaliti", "13")
        .param("traffico", "true")
        .param("andata", "true")).andReturn();
    String sizeErrorString = "Aspetta un attimo non so come funziona nel caso in cui è corretto lo sistemo poi okay?";
    Object bindingResObject = (Object) result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("orarioCorsa").toString().contains(sizeErrorString), "");
  }
}