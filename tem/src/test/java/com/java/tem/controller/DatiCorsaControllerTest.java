package com.java.tem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.java.tem.exceptions.DatiCorsaNotExistException;
import com.java.tem.exceptions.DoesNotBelongToAziendaException;
import com.java.tem.exceptions.ResourcesDoesNotExistException;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsa;
import com.java.tem.model.programmacorseservice.entity.daticorsaservice.DatiCorsaService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
  @MockBean
  private DatiCorsaService datiCorsaService;

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
  @WithMockUser
  void postiTooLong() throws Exception {
    String url = "/daticorsa/submit";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("lineaCorsa", "NA08")
        .param("orarioCorsa", "09:00:00")
        .param("numeroPosti", "86")
        .param("passeggeriSaliti", "50")
        .param("passeggeriNonSaliti", "13")
        .param("traffico", "true")
        .param("andata", "true")).andReturn();
    String sizeErrorString = "[numeroPosti],85]";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("numeroPosti").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void salitiWrongFormat() throws Exception {
    String url = "/daticorsa/submit";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
            .param("lineaCorsa", "NA08")
            .param("orarioCorsa", "09:00:00")
            .param("numeroPosti", "50")
            .param("passeggeriSaliti", "--")
            .param("passeggeriNonSaliti", "13")
            .param("traffico", "true")
            .param("andata", "true")).andDo(print()).andReturn();
    String sizeErrorString = "typeMismatch.passeggeriSaliti";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.datiCorsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("passeggeriSaliti").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void datiCorsaCorrect() throws Exception {
    String url = "/daticorsa/submit";

    mockMvc.perform(post(url).with(csrf())
            .param("lineaCorsa", "NA08")
            .param("orarioCorsa", "09:00:00")
            .param("numeroPosti", "50")
            .param("passeggeriSaliti", "50")
            .param("passeggeriNonSaliti", "13")
            .param("traffico", "true")
            .param("andata", "true")).andDo(print()).andExpect(status().isOk())
        .andExpect(view().name("dati-corsa-success"));
  }
  /* End of Dati Corsa insert tests */

  /* Edit form */

  @Test
  @WithMockUser
  void showUpdateFormDatiCorsaNotExist() throws Exception {
    DatiCorsa datiCorsa = mock(DatiCorsa.class);
    mockMvc.perform(get("/daticorsa/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(result ->
            assertTrue(result.getResolvedException() instanceof DatiCorsaNotExistException, "")
        );
  }

  @Test
  @WithMockUser
  void showUpdateFormDatiCorsaBelongsToAzienda() throws Exception {
    DatiCorsa datiCorsa = mock(DatiCorsa.class);
    Utente utente = new Utente();
    datiCorsa.setId(0L);
    utente.setId(1L);
    datiCorsa.setAzienda(utente);
    when(datiCorsaService.getDatiCorsa(0L)).thenReturn(Optional.of(datiCorsa));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(datiCorsaService.checkOwnership(datiCorsa, utente)).thenReturn(true);
    mockMvc.perform(get("/daticorsa/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(view().name("edit-daticorsa"));
  }

  @Test
  @WithMockUser
  void showUpdateFormDatiCorsaDoesNotBelongToAzienda() throws Exception {
    DatiCorsa datiCorsa = mock(DatiCorsa.class);
    Utente utente = new Utente();
    datiCorsa.setId(0L);
    utente.setId(1L);
    datiCorsa.setAzienda(utente);
    when(datiCorsaService.getDatiCorsa(0L)).thenReturn(Optional.of(datiCorsa));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(datiCorsaService.checkOwnership(datiCorsa, utente)).thenReturn(false);
    mockMvc.perform(get("/daticorsa/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(result ->
            assertEquals("I dati corsa non appartengono alla tua azienda",
                result.getModelAndView().getModelMap().getAttribute("error"), ""));
  }

  @Test
  @WithMockUser
  void updateDatiCorsaSuccess() throws Exception {
    mockMvc.perform(post("/daticorsa/update/{id}", "1").with(csrf())
        .param("lineaCorsa", "NA08")
        .param("orarioCorsa", "09:00:00")
        .param("numeroPosti", "50")
        .param("passeggeriSaliti", "50")
        .param("passeggeriNonSaliti", "13")
        .param("traffico", "true")
        .param("andata", "true"))
        .andExpect(status().isOk())
        .andExpect(view().name("update-success"));
  }

  @Test
  @WithMockUser
  void updateDatiCorsaFailureLinea() throws Exception {
    MvcResult result = mockMvc.perform(post("/daticorsa/update/{id}", "1").with(csrf())
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
  void updateDatiCorsaFailureOrario() throws Exception {
    MvcResult result = mockMvc.perform(post("/daticorsa/update/{id}", "1").with(csrf())
        .param("lineaCorsa", "NA208")
        .param("orarioCorsa", "0")
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

  /*@Test
  @WithMockUser
  void updateDatiCorsaFailureNumeroPosti() throws Exception {
    MvcResult result = mockMvc.perform(post("/daticorsa/update/{id}", "1").with(csrf())
        .param("lineaCorsa", "NA208")
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
  }*/

}