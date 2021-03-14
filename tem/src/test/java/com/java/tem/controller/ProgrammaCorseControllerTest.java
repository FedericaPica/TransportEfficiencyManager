package com.java.tem.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.java.tem.model.accountservice.entity.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

public class ProgrammaCorseControllerTest {

  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AccountService accountService;
  @InjectMocks
  private ProgrammaCorseController programmaCorseController;

  @Test
  @WithMockUser
  void DataInizioBadFormat() throws Exception {
    String url = "/programmacorse/manuale/submit";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("inizioValidita", "09/02/aaaa")
        .param("fineValidita", "09/12/2021")).andReturn();
    String sizeErrorString = "Inizio validità ha il formato sbagliato";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.ProgrammaCorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("inizioValidita").toString().contains(sizeErrorString), "");
  }

  @Test
  @WithMockUser
  void ProgrammaCorseAllCorrect() throws Exception {
    String url = "/programmacorse/manuale/submit";
    MvcResult result = mockMvc.perform(post(url).with(csrf())
        .param("inizioValidita", "09/02/2021")
        .param("fineValidita", "09/12/2021")).andReturn();
    String sizeErrorString = "Questo è un caso in cui i dati sono corretti, mi sa che vanno cambiate le cose sotto";
    Object bindingResObject = result.getModelAndView().getModelMap()
        .getAttribute("org.springframework.validation.BindingResult.ProgrammaCorse");
    BindingResult bindingResult = (BindingResult) bindingResObject;
    assertTrue(bindingResult.getFieldError("orarioCorsa").toString().contains(sizeErrorString), "");
  }
}