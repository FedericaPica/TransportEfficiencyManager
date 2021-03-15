package com.java.tem.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.java.tem.exceptions.ResourcesDoesNotExistException;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
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
  private RisorseService risorseService;
  @MockBean
  private AccountService accountService;

  /*
   * Insert Risorse Tests
   */
  @Test
  @WithMockUser
  void nomeTooShort() throws Exception {
    String url = "/risorse/submit/conducente";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "P")
        .param("cognome", "Neri")
        .param("codiceFiscale", "NREPLA80C15H501T")).andReturn();
    String sizeErrorString = "size must be between 2 and 50";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.conducente");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("nome").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void cognomeTooShort() throws Exception {
    String url = "/risorse/submit/conducente";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "Paolo")
        .param("cognome", "")
        .param("codiceFiscale", "NREPLA80C15H501T")).andReturn();
    String sizeErrorString = "size must be between 2 and 50";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.conducente");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("cognome").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void codiceFiscaleTooLong() throws Exception {
    String url = "/risorse/submit/conducente";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "Paolo")
        .param("cognome", "Neri")
        .param("codiceFiscale", "NREPLA80C15H501TT")).andReturn();
    String sizeErrorString = "[codiceFiscale],16,16]";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.conducente");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("codiceFiscale").toString().contains(sizeErrorString), "");
  }
 
  @Test
  @WithMockUser
  void conducenteCorrect() throws Exception {
    String url = "/risorse/submit/conducente";
    mockMvc.perform(post(url).with(csrf())
        .param("nome", "Paolo")
        .param("cognome", "Neri")
        .param("codiceFiscale", "NREPLA80C15H501T"))
        .andExpect(status().isOk())
        .andExpect(view().name("insert-success-conducente"));
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
        .getAttribute("org.springframework.validation.BindingResult.linea");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("nome").toString().contains(sizeErrorString), "");
  }
  
  @Test
  @WithMockUser
  void destinazioneTooShort() throws Exception {
    String url = "/risorse/submit/linea";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("nome", "NA08")
        .param("partenza", "Napoli")
        .param("destinazione", "")).andReturn();
    String sizeErrorString = "[destinazione],50,2]";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.linea");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("destinazione").toString().contains(sizeErrorString), "");
  }
  
  @Test
  @WithMockUser
  void insertLineaSuccess() throws Exception {
    String url = "/risorse/submit/linea";
    mockMvc.perform(post(url).with(csrf())
        .param("nome", "NA08")
        .param("partenza", "Napoli")
        .param("destinazione", "Avellino")).andExpect(status().isOk())
    .andExpect(view().name("insert-success-linea"));
  }
  
  @Test
  @WithMockUser
  void targaTooShort() throws Exception {
    String url = "/risorse/submit/mezzo";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("capienza", "50")
        .param("targa", "PL090S")
        .param("tipo", "PullmanS")).andReturn();
    String sizeErrorString = "[targa],10,7";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.mezzo");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("targa").toString().contains(sizeErrorString), "");
  }
  
  @Test
  @WithMockUser
  void tipoTooShort() throws Exception {
    String url = "/risorse/submit/mezzo";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("capienza", "50")
        .param("targa", "PL090SC")
        .param("tipo", "")).andReturn();
    String sizeErrorString = "[tipo],50,2";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.mezzo");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("tipo").toString().contains(sizeErrorString), "");
  }
  
  @Test
  @WithMockUser
  void inserimentoMezzoSuccess() throws Exception {
    String url = "/risorse/submit/mezzo";
    mockMvc.perform(post(url).with(csrf())
        .param("capienza", "50")
        .param("targa", "PL090SC")
        .param("tipo", "PullmanS")).andExpect(status().isOk())
            .andExpect(view().name("insert-success"));
  }
  /* End of Risorsa insert tests */


  /* Risorse Edit Test */
  @Test
  @WithMockUser
  void showUpdateFormMezzoNotExists() throws Exception {
    Mezzo mezzo = mock(Mezzo.class);
    //when(risorseService.getMezzo(0L)).thenReturn(Optional.empty());
    mockMvc.perform(get("/risorse/mezzo/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(result ->
            assertTrue(result.getResolvedException() instanceof ResourcesDoesNotExistException, "")
        );
  }
  @Test
  @WithMockUser
  void showUpdateFormLineaNotExists() throws Exception {
    Mezzo mezzo = mock(Mezzo.class);
    mockMvc.perform(get("/risorse/linea/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(result ->
            assertTrue(result.getResolvedException() instanceof ResourcesDoesNotExistException, "")
        );
  }

  @Test
  @WithMockUser
  void showUpdateFormConducenteNotExists() throws Exception {
    Mezzo mezzo = mock(Mezzo.class);
    mockMvc.perform(get("/risorse/conducente/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(result ->
            assertTrue(result.getResolvedException() instanceof ResourcesDoesNotExistException, "")
        );
  }

  @Test
  @WithMockUser
  void showUpdateFormMezzoBelongsToAzienda() throws Exception {
    Mezzo mezzo = mock(Mezzo.class);
    Utente utente = new Utente();
    mezzo.setId(0L);
    utente.setId(1L);
    mezzo.setAzienda(utente);
    when(risorseService.getMezzo(0L)).thenReturn(Optional.of(mezzo));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(mezzo, utente)).thenReturn(true);
    mockMvc.perform(get("/risorse/mezzo/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(view().name("edit-mezzo"));
  }

  @Test
  @WithMockUser
  void showUpdateFormMezzoDoesNotBelongToAzienda() throws Exception {
    Mezzo mezzo = mock(Mezzo.class);
    Utente utente = new Utente();
    mezzo.setId(0L);
    utente.setId(1L);
    mezzo.setAzienda(utente);
    when(risorseService.getMezzo(0L)).thenReturn(Optional.of(mezzo));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(mezzo, utente)).thenReturn(false);
    mockMvc.perform(get("/risorse/mezzo/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(result ->
            assertEquals("La risorsa non appartiene all'azienda",
                result.getModelAndView().getModelMap().getAttribute("error"), ""));
  }

  @Test
  @WithMockUser
  void showUpdateFormLineaBelongsToAzienda() throws Exception {
    Linea linea = mock(Linea.class);
    Utente utente = new Utente();
    linea.setId(0L);
    utente.setId(1L);
    linea.setAzienda(utente);
    when(risorseService.getLinea(0L)).thenReturn(Optional.of(linea));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(linea, utente)).thenReturn(true);
    mockMvc.perform(get("/risorse/linea/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(view().name("edit-linea"));
  }

  @Test
  @WithMockUser
  void showUpdateFormLineaDoesNotBelongToAzienda() throws Exception {
    Linea linea = mock(Linea.class);
    Utente utente = new Utente();
    linea.setId(0L);
    utente.setId(1L);
    linea.setAzienda(utente);
    when(risorseService.getLinea(0L)).thenReturn(Optional.of(linea));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(linea, utente)).thenReturn(false);
    mockMvc.perform(get("/risorse/linea/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(result ->
            assertEquals("La risorsa non appartiene all'azienda",
                result.getModelAndView().getModelMap().getAttribute("error"), ""));
  }

  @Test
  @WithMockUser
  void showUpdateFormConducenteBelongsToAzienda() throws Exception {
    Conducente conducente = mock(Conducente.class);
    Utente utente = new Utente();
    conducente.setId(0L);
    utente.setId(1L);
    conducente.setAzienda(utente);
    when(risorseService.getConducente(0L)).thenReturn(Optional.of(conducente));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(conducente, utente)).thenReturn(true);
    mockMvc.perform(get("/risorse/conducente/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(view().name("edit-conducente"));
  }

  @Test
  @WithMockUser
  void showUpdateFormConducenteDoesNotBelongToAzienda() throws Exception {
    Conducente conducente = mock(Conducente.class);
    Utente utente = new Utente();
    when(risorseService.getConducente(0L)).thenReturn(Optional.of(conducente));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(conducente, utente)).thenReturn(false);
    mockMvc.perform(get("/risorse/conducente/edit/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(result ->
            assertEquals("La risorsa non appartiene all'azienda",
                result.getModelAndView().getModelMap().getAttribute("error"), ""));
  }

  @Test
  @WithMockUser
  void updateConducenteSuccess() throws Exception {
    mockMvc.perform(post("/risorse/update/conducente/{id}", "1").with(csrf())
        .param("nome", "Paolo")
        .param("cognome", "Neri")
        .param("codiceFiscale", "NREPLA80C15H501T"))
        .andExpect(status().isOk())
        .andExpect(view().name("update-success"));
  }

  @Test
  @WithMockUser
  void updateConducenteFailureCodiceFiscale() throws Exception {
    String sizeErrorString = "[codiceFiscale],16,16";
    MvcResult result = mockMvc.perform(post("/risorse/update/conducente/{id}", "1").with(csrf())
        .param("nome", "Paolo")
        .param("cognome", "Neri")
        .param("codiceFiscale", ""))
        .andReturn();
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.conducente");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("codiceFiscale").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void updateConducenteFailureNome() throws Exception {
    String sizeErrorString = "[nome],50,2";
    MvcResult result = mockMvc.perform(post("/risorse/update/conducente/{id}", "1").with(csrf())
        .param("nome", "P")
        .param("cognome", "Neri")
        .param("codiceFiscale", "NREPLA80C15H501T"))
        .andReturn();
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.conducente");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("nome").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void updateConducenteFailureCognome() throws Exception {
    String sizeErrorString = "[cognome],50,2";
    MvcResult result = mockMvc.perform(post("/risorse/update/conducente/{id}", "1").with(csrf())
        .param("nome", "Paolo")
        .param("cognome", "N")
        .param("codiceFiscale", "NREPLA80C15H501T"))
        .andReturn();
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.conducente");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("cognome").toString().contains(sizeErrorString), "");
  }

  /* Linea */

  @Test
  @WithMockUser
  void updateLineaSuccess() throws Exception {
    mockMvc.perform(post("/risorse/update/linea/{id}", "1").with(csrf())
        .param("nome", "NA08")
        .param("partenza", "Napoli")
        .param("destinazione", "Avellino")).andExpect(status().isOk())
        .andExpect(view().name("update-success"));
  }

  @Test
  @WithMockUser
  void updateLineaNomeFailure() throws Exception {
    mockMvc.perform(post("/risorse/update/linea/{id}", "1").with(csrf())
        .param("nome", "N")
        .param("partenza", "Napoli")
        .param("durata", "35")
        .param("destinazione", "Avellino")).andDo(print());
    String sizeErrorString = "[nome],50,2";
    /*Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.linea");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("nome").toString().contains(sizeErrorString), "");*/
  }
  
  @Test
  @WithMockUser
  void updateLineaPartenzaFailure() throws Exception {
    MvcResult result = mockMvc.perform(post("/risorse/update/linea/{id}", "1").with(csrf())
        .param("nome", "NA08")
        .param("partenza", "N")
        .param("destinazione", "Avellino")).andReturn();
    String sizeErrorString = "[partenza],50,2";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.linea");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("partenza").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void updateLineaDestinazioneFailure() throws Exception {
    String url = "/risorse/submit/linea";
    MvcResult result = mockMvc.perform(post("/risorse/update/linea/{id}", "1").with(csrf())
        .param("nome", "NA08")
        .param("partenza", "Napoli")
        .param("destinazione", "")).andReturn();
    String sizeErrorString = "[destinazione],50,2";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.linea");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("destinazione").toString().contains(sizeErrorString), "");
  }

  /* End Linea updates */

  /* Mezzo updates */

  @Test
  @WithMockUser
  void updateMezzoTargaFailure() throws Exception {
    MvcResult result = mockMvc.perform(post("/risorse/update/mezzo/{id}", "1").with(csrf())
        .param("capienza", "50")
        .param("targa", "PL090S")
        .param("tipo", "PullmanS")).andReturn();
    String sizeErrorString = "[targa],10,7";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.mezzo");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("targa").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void updateMezzoTipoFailure() throws Exception {
    MvcResult result = mockMvc.perform(post("/risorse/update/mezzo/{id}", "1").with(csrf())
        .param("capienza", "50")
        .param("targa", "PL090SC")
        .param("tipo", "")).andReturn();
    String sizeErrorString = "[tipo],50,2";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.mezzo");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("tipo").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void updateMezzoSuccess() throws Exception {
    mockMvc.perform(post("/risorse/update/mezzo/{id}", "1").with(csrf())
        .param("capienza", "50")
        .param("targa", "PL090SC")
        .param("tipo", "PullmanS")).andExpect(status().isOk())
        .andExpect(view().name("update-success"));
  }
  /* End Mezzo updates */

  /* Delete Tests */

  @Test
  @WithMockUser
  void deleteConducenteFailureOwnership() throws Exception {
    Conducente conducente = mock(Conducente.class);
    Utente utente = new Utente();
    when(risorseService.getConducente(0L)).thenReturn(Optional.of(conducente));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(conducente, utente)).thenReturn(false);
    mockMvc.perform(get("/risorse/delete/conducente/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(result ->
            assertEquals("La risorsa non appartiene all'azienda",
                result.getModelAndView().getModelMap().getAttribute("error"), ""));
  }

  @Test
  @WithMockUser
  void deleteLineaFailureOwnership() throws Exception {
    Linea linea = mock(Linea.class);
    Utente utente = new Utente();
    when(risorseService.getLinea(0L)).thenReturn(Optional.of(linea));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(linea, utente)).thenReturn(false);
    mockMvc.perform(get("/risorse/delete/linea/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(result ->
            assertEquals("La risorsa non appartiene all'azienda",
                result.getModelAndView().getModelMap().getAttribute("error"), ""));
  }

  @Test
  @WithMockUser
  void deleteMezzoFailureOwnership() throws Exception {
    Mezzo mezzo = mock(Mezzo.class);
    Utente utente = new Utente();
    when(risorseService.getMezzo(0L)).thenReturn(Optional.of(mezzo));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(mezzo, utente)).thenReturn(false);
    mockMvc.perform(get("/risorse/delete/mezzo/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(result ->
            assertEquals("La risorsa non appartiene all'azienda",
                result.getModelAndView().getModelMap().getAttribute("error"), ""));
  }

  @Test
  @WithMockUser
  void deleteMezzoSuccess() throws Exception {
    Mezzo mezzo = mock(Mezzo.class);
    Utente utente = new Utente();
    when(risorseService.getMezzo(0L)).thenReturn(Optional.of(mezzo));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(mezzo, utente)).thenReturn(true);
    mockMvc.perform(get("/risorse/delete/mezzo/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/home"));
  }

  @Test
  @WithMockUser
  void deleteLineaSuccess() throws Exception {
    Linea linea = mock(Linea.class);
    Utente utente = new Utente();
    when(risorseService.getLinea(0L)).thenReturn(Optional.of(linea));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(linea, utente)).thenReturn(true);
    mockMvc.perform(get("/risorse/delete/linea/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/home"));
  }

  @Test
  @WithMockUser
  void deleteConducenteSuccess() throws Exception {
    Conducente conducente = mock(Conducente.class);
    Utente utente = new Utente();
    when(risorseService.getConducente(0L)).thenReturn(Optional.of(conducente));
    when(accountService.getLoggedUser()).thenReturn(Optional.of(utente).get());
    when(risorseService.checkOwnership(conducente, utente)).thenReturn(true);
    mockMvc.perform(get("/risorse/delete/conducente/{id}", Mockito.anyLong()).with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/home"));
  }
}



