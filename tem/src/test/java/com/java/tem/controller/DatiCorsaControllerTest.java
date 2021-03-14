package com.java.tem.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.java.tem.model.accountservice.entity.AccountService;
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
public class DatiCorsaControllerTest {

  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AccountService accountService;
  @InjectMocks
  private DatiCorsaController datiCorsaController;

  /*
   * Insert Dati Corsa Tests
   */
  @Test
  @WithMockUser
  void lineaTooShort() throws Exception {
    String url = "/daticorsa/submit";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("lineaCorsa", "N")
        .param("orarioCorsa", "09:00:00")
        .param("numeroPosti", "50")
        .param("passeggeriSaliti", "50")
        .param("passeggeriNonSaliti", "13")
        .param("traffico", "true")
        .param("andata", "true")).andReturn();
    String sizeErrorString = "[lineaCorsa],40,2]";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("lineaCorsa").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void orarioBadFormat() throws Exception {
    String url = "/daticorsa/submit";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("lineaCorsa", "NA08")
        .param("orarioCorsa", "9")
        .param("numeroPosti", "50")
        .param("passeggeriSaliti", "50")
        .param("passeggeriNonSaliti", "13")
        .param("traffico", "true")
        .param("andata", "true")).andReturn();
    String sizeErrorString = "typeMismatch";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("orarioCorsa").toString().contains(sizeErrorString), "");
  }

  @Test
  void postiTooLong() throws Exception {
    String url = "/daticorsa/add";
    MvcResult result =
        ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(post(url).with(csrf())))
            .param("lineaCorsa", "NA08")
            .param("orarioCorsa", "09:00:00")
            .param("numeroPosti", "500")
            .param("passeggeriSaliti", "50")
            .param("passeggeriNonSaliti", "13")
            .param("traffico", "true")
            .param("andata", "true")).andReturn();
    String sizeErrorString = "Il campo Numero posti non rispetta la lunghezza minima";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("numeroPosti").toString().contains(sizeErrorString), "");
  }

  @Test
  void salitiWrongFormat() throws Exception {
    String url = "/daticorsa/add";
    MvcResult result =
        ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(post(url).with(csrf())))
            .param("lineaCorsa", "NA08")
            .param("orarioCorsa", "09:00:00")
            .param("numeroPosti", "50")
            .param("passeggeriSaliti", "--")
            .param("passeggeriNonSaliti", "13")
            .param("traffico", "true")
            .param("andata", "true")).andReturn();
    String sizeErrorString = "Il campo Passeggeri saliti non rispetta il formato";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("passeggeriSaliti").toString().contains(sizeErrorString), "");
  }

  @Test
  void datiCorsaCorrect() throws Exception {
    String url = "/daticorsa/add";
    MvcResult result =
        ((ResultActions) ((MockHttpServletRequestBuilder) mockMvc.perform(post(url).with(csrf())))
            .param("lineaCorsa", "NA08")
            .param("orarioCorsa", "09:00:00")
            .param("numeroPosti", "50")
            .param("passeggeriSaliti", "50")
            .param("passeggeriNonSaliti", "13")
            .param("traffico", "true")
            .param("andata", "true")).andReturn();
    String sizeErrorString =
        "Aspetta un attimo non so come funziona nel caso in cui è corretto lo sistemo poi okay?";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("orarioCorsa").toString().contains(sizeErrorString), "");
  }
  /* End of Dati Corsa insert tests */
}