package com.java.tem.controller;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorseService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import com.java.tem.model.programmacorseservice.repository.ProgrammaManualeMaker;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

  private List<Long> mezzi;
  private List<Long> conducenti;
  private List<Linea> lineeAzienda;
  private List<Conducente> conducentiAzienda;
  private List<Mezzo> mezziAzienda;
  private Linea linea;
  /*
   * Insert  Corsa Tests
   */
  @BeforeEach
  void setUp() {
    mezzi = new ArrayList<>();
    conducenti = new ArrayList<>();
    mezzi.add(1L);
    conducenti.add(1L);
    Utente utente = mock(Utente.class);
    linea = mock(Linea.class);
    risorseService.getLineeByAzienda(utente);
    risorseService.getConducentiByAzienda(utente);
    risorseService.getMezziByAzienda(utente);
  }

  @Test
  @WithMockUser
  void lineaNotSelected() throws Exception {
    String url = "/corsa/submit/1/";

    mockMvc
        .perform(post("/corsa/submit/{programmaCorseId}", "1").with(csrf())
    		    .param("orario", "09:00:00")
    	        .param("mezzo", "1,2,3")
                .param("linea", "")
    	        .param("conducente", "1,2,3")
    	        .param("programmaCorseId", "1")
    	        .param("andata", "true"))
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException, ""));
  }

  @Test
  @WithMockUser
  void orarioBadFormat() throws Exception {
    Conducente conducente = mock(Conducente.class);
    ProgrammaCorse programmaCorse = mock(ProgrammaCorse.class);

    Mezzo mezzo = mock(Mezzo.class);
    when(risorseService.getConducente(Mockito.anyLong())).thenReturn(
        Optional.ofNullable(conducente));
	when(risorseService.getMezzo(Mockito.anyLong())).thenReturn(Optional.of(mezzo));

	when(programmaCorseService.getProgrammaCorseById(Mockito.anyLong()))
        .thenReturn(Optional.of(programmaCorse));
    MvcResult result = mockMvc.perform(post("/corsa/submit/{id}", "1").with(csrf())
        .flashAttr("corsa", new Corsa())
        .param("orario", "9")
        .param("mezzo", "1,2,3")
        .param("linea", "1")
        .param("conducente", "1,2,3")
        .param("programmaCorseId", "1L")
        .param("andata", "true")).andReturn();
    String sizeErrorString = "typeMismatch";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.corsa");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("orario").toString().contains(sizeErrorString),
        "");

  }

  @Test
  @WithMockUser
  void mezzoNotSelected() throws Exception {
    String url = "/corsa/submit/1/";
    Conducente conducente = mock(Conducente.class);
    ProgrammaCorse programmaCorse = mock(ProgrammaCorse.class);

    Mezzo mezzo = mock(Mezzo.class);
    when(risorseService.getConducente(Mockito.anyLong())).thenReturn(
        Optional.ofNullable(conducente));
    when(risorseService.getMezzo(Mockito.anyLong())).thenReturn(Optional.of(mezzo));
    when(risorseService.getLinea(Mockito.anyLong())).thenReturn(Optional.of(linea));

      when(programmaCorseService.getProgrammaCorseById(Mockito.anyLong()))
              .thenReturn(Optional.of(programmaCorse));
      mockMvc
              .perform(post("/corsa/submit/{programmaCorseId}", "1").with(csrf())
                      .param("orario", "09:00:00")
                      .param("linea", "1")
                      .param("conducente", "1,2,3")
                      .param("programmaCorseId", "1")
                      .param("andata", "true")).andDo(print())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException, ""));
  }


  @Test
  @WithMockUser
  void success() throws Exception {
      String url = "/corsa/submit/1/";
      Conducente conducente = mock(Conducente.class);
      ProgrammaCorse programmaCorse = mock(ProgrammaCorse.class);

      Mezzo mezzo = mock(Mezzo.class);
      when(risorseService.getConducente(Mockito.anyLong())).thenReturn(
              Optional.ofNullable(conducente));
      when(risorseService.getMezzo(Mockito.anyLong())).thenReturn(Optional.of(mezzo));
      when(risorseService.getLinea(Mockito.anyLong())).thenReturn(Optional.of(linea));

      when(programmaCorseService.getProgrammaCorseById(Mockito.anyLong()))
              .thenReturn(Optional.of(programmaCorse));
      mockMvc
              .perform(post("/corsa/submit/{programmaCorseId}", "1").with(csrf())
                      .param("orario", "09:00:00")
                      .param("mezzo", "1")
                      .param("linea", "1")
                      .param("conducente", "1,2,3")
                      .param("programmaCorseId", "1")
                      .param("andata", "true")).andDo(print())
              .andExpect(status().is3xxRedirection());
  }
  
  /* End of Corsa insert tests */
}



