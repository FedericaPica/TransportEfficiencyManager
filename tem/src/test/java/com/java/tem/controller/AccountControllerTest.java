package com.java.tem.controller;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.java.tem.exceptions.NotAuthorizedException;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.DettaglioUtente;
import com.java.tem.model.accountservice.entity.Utente;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;


@SuppressWarnings("JUnitTestMethodWithNoAssertions")
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class AccountControllerTest {

  @InjectMocks
  private AccountController accountController;

  @Autowired
  private WebApplicationContext wac;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;


  @Before
  public void setup() {

    mockMvc = MockMvcBuilders.standaloneSetup(accountController)
			.setControllerAdvice(new ExceptionHandlingController())
			.apply(springSecurity())
        .build();
  }

	@Test
	void getIndexTest() throws Exception {
		Utente utente = new Utente();
		//mockMvc.perform(post("/process_register")
		//   .param("rawPassword", "Lel")).andExpect(status().is5xxServerError());
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	/*
	 * Process Register Tests
	 */
	@Test
	void processRegisterEmptyDenominazione() throws Exception {
		String url = "/process_register";
		MvcResult result = mockMvc.perform(post(url).with(csrf())
				.param("email", "azienda@gmail.com")
				.param("password", "password")
				.param("username", "username")
				.param("denominazione", "")
				.param("partitaIVA", "08100750010")
				.param("telefono", "08257654334")
				.param("fax", "800909396")
				.param("indirizzo", "via Roma n. 13")
				.param("cap", "89700")
				.param("citta", "Verona")).andReturn();
		String sizeErrorString = "size must be between 2 and 50";
		Object bindingResObject = result.getModelAndView().getModelMap()
				.getAttribute("org.springframework.validation.BindingResult.dettaglioUtente");
		BindingResult bindingResult = (BindingResult) bindingResObject;
		assertTrue(
				bindingResult.getFieldError("denominazione").toString().contains(sizeErrorString),
				"");

	}

	@Test
	void processRegisterUserAlreadyExists() throws Exception {
		String url = "/process_register";
		when(accountService.checkUserExistanceByEmail(ArgumentMatchers.anyString()))
				.thenReturn(Boolean.TRUE);
		mockMvc.perform(post(url).with(csrf())
				.param("email", "azienda@gmail.com")
				.param("password", "password")
				.param("username", "username")
				.param("denominazione", "Bus s.r.l")
				.param("partitaIVA", "08100750010")
				.param("telefono", "08257654334")
				.param("fax", "800909396")
				.param("indirizzo", "via Roma n. 13")
				.param("cap", "89700")
				.param("citta", "Verona")).andExpect(status().is3xxRedirection())
				.andExpect(result -> assertTrue(result.getModelAndView().getModelMap()
								.getAttribute("error").toString().contains("Utente già esistente"),
						"Utente esistente"));
	}

	@Test
	void processRegisterOk() throws Exception {
		String url = "/process_register";
		when(accountService.checkUserExistanceByEmail(ArgumentMatchers.anyString()))
				.thenReturn(Boolean.FALSE);

		Utente utente = new Utente();
		DettaglioUtente dettaglioUtente = new DettaglioUtente();
		utente.setDettaglio(dettaglioUtente);
		utente.setEmail("azienda@gmail.com");

		when(accountService.registerUser(utente, dettaglioUtente)).thenReturn(utente);

		mockMvc.perform(post(url).with(csrf())
				.param("email", "azienda@gmail.com")
				.param("password", "password")
				.param("username", "username")
				.param("denominazione", "Bus s.r.l")
				.param("partitaIVA", "08100750010")
				.param("telefono", "08257654334")
				.param("fax", "800909396")
				.param("indirizzo", "via Roma n. 13")
				.param("cap", "89700")
				.param("citta", "Verona")).andExpect(status().isOk())
				.andExpect(view().name("register_success"));
	}

	/* End of Process register tests */


	@Test
	@WithMockUser
	void testUsersPageNoAdmin() throws Exception {
		when(accountService.isAdmin()).thenReturn(false);
		String urlString = "/users";
		mockMvc.perform(get(urlString)).andExpect(status().isForbidden()).andExpect(
				result -> assertTrue(
						result.getResolvedException() instanceof NotAuthorizedException));
	}

	@Test
  @WithMockUser
  void testUsersPageAdmin() throws Exception {
		when(accountService.isAdmin()).thenReturn(true);
		List<Utente> listUsers = new ArrayList<Utente>();
		when(accountService.getAllUsers()).thenReturn(listUsers);
		String urlString = "/users";
		mockMvc.perform(get(urlString)).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("admin"));
	}

	@Test
  @WithMockUser
  void testHomeNotLogged() {

	}

}
