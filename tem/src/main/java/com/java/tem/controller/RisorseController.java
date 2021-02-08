package com.java.tem.controller;

import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RisorseController {

  @Autowired
  RisorseService risorseService;

  @GetMapping("/risorse")
  public String homeRisorse() {
    return "risorse-index";
  }
  
  @GetMapping("/risorse/add/conducente")
  public String addConducente(Model model) {
	  model.addAttribute("conducente", new Conducente());
	  return "insert-conducente";
  }
}
