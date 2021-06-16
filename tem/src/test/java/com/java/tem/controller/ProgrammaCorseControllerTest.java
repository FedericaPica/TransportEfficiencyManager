package com.java.tem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.java.tem.exceptions.GenerationTypeNotFoundException;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.accountservice.repository.UserRepository;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import com.java.tem.model.programmacorseservice.repository.ConducenteRepository;
import com.java.tem.model.programmacorseservice.repository.LineaRepository;
import com.java.tem.model.programmacorseservice.repository.MezzoRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class ProgrammaCorseControllerTest {

  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AccountService accountService;
  @InjectMocks
  private static ProgrammaCorseController programmaCorseController;
  @Mock
  private UserRepository userRepository;
  @MockBean
  private RisorseService risorseService;
  @Mock
  private MezzoRepository mezzoRepository;
  @Mock
  private LineaRepository lineaRepository;
  @Mock
  private ConducenteRepository conducenteRepository;


  @Test
  @WithMockUser
  void DataInizioBadFormat() throws Exception {
    String url = "/programmacorse/manuale/submit";
    mockMvc.perform(post(url).with(csrf())
        .param("inizioValidita", "2021-02-dd")
        .param("fineValidita", "2022-09-21"))
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof BindException));

  }

  @Test
  @WithMockUser
  void ProgrammaCorseAllCorrect() throws Exception {
    String url = "/programmacorse/manuale/submit";
    Utente utente = new Utente();
    utente.setId(1L);
    when(accountService.getUserByUsername(Mockito.anyString())).thenReturn(utente);
    when(userRepository.findByEmail(Mockito.anyString())).thenReturn(utente);
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("inizioValidita", "2021-02-09")
        .param("fineValidita", "2021-09-12")).andReturn();
  }

  @Test
  @WithMockUser
  void programmaCorseInsertWrongType() throws Exception {
    String url = "/programmacorse/insert";
    String type = "Wrong";
    mockMvc.perform(get(url)
        .param("type", type))
        .andExpect(result -> assertTrue(
            result.getResolvedException() instanceof GenerationTypeNotFoundException));
  }


  @Test
  @WithMockUser
  void programmaCorseManualeInsertNoResources() throws Exception {
    String url = "/programmacorse/insert";
    String errorValue = "Una o più risorse mancanti. Per generare un programma " +
        "è necessario disporre di almeno una risorsa per tipo.";
    String type = "manuale";
    MvcResult response = mockMvc.perform(get(url)
        .param("type", type))
        .andReturn();
    ModelMap mev = response.getModelAndView().getModelMap();
    assertTrue(errorValue.equals(mev.getAttribute("error")), "");
  }

  @Test
  @WithMockUser
  void programmaCorseInsertConducentiGTZero() throws Exception {
    String url = "/programmacorse/insert";
    String errorValue = "Una o più risorse mancanti. Per generare un programma " +
        "è necessario disporre di almeno una risorsa per tipo.";
    String type = "manuale";
    Utente utente = mock(Utente.class);
    //ConducenteRepository conducenteRepository = mock(ConducenteRepository.class);
    List<Conducente> conducentiList = new ArrayList<Conducente>();
    conducentiList.add(mock(Conducente.class));
    when(risorseService.getConducentiByAzienda(utente)).thenReturn(conducentiList);
    MvcResult response = mockMvc.perform(get(url)
        .param("type", type))
        .andReturn();
    ModelMap mev = response.getModelAndView().getModelMap();
    assertTrue(errorValue.equals(mev.getAttribute("error")), "");
  }

  @Test
  @WithMockUser
  void programmaCorseInsertConducentiMezziGTZero() throws Exception {
    String url = "/programmacorse/insert";
    String errorValue = "Una o più risorse mancanti. Per generare un programma " +
        "è necessario disporre di almeno una risorsa per tipo.";
    String type = "manuale";
    Utente utente = mock(Utente.class);
    //ConducenteRepository conducenteRepository = mock(ConducenteRepository.class);
    List<Conducente> conducentiList = new ArrayList<Conducente>();
    List<Mezzo> mezziList = new ArrayList<Mezzo>();
    conducentiList.add(mock(Conducente.class));
    mezziList.add(mock(Mezzo.class));
    when(risorseService.getConducentiByAzienda(utente)).thenReturn(conducentiList);
    when(risorseService.getMezziByAzienda(utente)).thenReturn(mezziList);
    MvcResult response = mockMvc.perform(get(url)
        .param("type", type))
        .andReturn();
    ModelMap mev = response.getModelAndView().getModelMap();
    assertTrue(errorValue.equals(mev.getAttribute("error")), "");
  }

  @Test
  @WithMockUser
  void programmaCorseInsertAllGTZero() throws Exception {
    String url = "/programmacorse/insert";
    String errorValue = "Una o più risorse mancanti. Per generare un programma " +
        "è necessario disporre di almeno una risorsa per tipo.";
    String type = "manuale";
    Utente utente = mock(Utente.class);
    List<Conducente> conducentiList = mock(List.class);
    List<Mezzo> mezziList = mock(List.class);
    List<Linea> lineeList = mock(List.class);

    when(conducentiList.size()).thenReturn(10);
    when(mezziList.size()).thenReturn(10);
    when(lineeList.size()).thenReturn(10);
    when(accountService.getLoggedUser()).thenReturn(utente);

    when(risorseService.getConducentiByAzienda(utente)).thenReturn(conducentiList);

    when(risorseService.getMezziByAzienda(utente)).thenReturn(mezziList);

    when(risorseService.getLineeByAzienda(utente)).thenReturn(lineeList);

    System.out.println(conducentiList.size());
    MvcResult response = (MvcResult) mockMvc.perform(get(url)
        .param("type", type))
        .andReturn();
    assertEquals(200, response.getResponse().getStatus());


  }
}