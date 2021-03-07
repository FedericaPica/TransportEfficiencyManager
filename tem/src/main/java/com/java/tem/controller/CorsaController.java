package com.java.tem.controller;

import com.java.tem.exceptions.ResourcesDoesNotExistException;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CorsaController {
  //@Autowired
  //private CorsaService corsaService;

  @Autowired
  private RisorseService risorseService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private ProgrammaCorseService programmaCorseService;

  @Autowired
  private CorsaService corsaService;

  @Autowired
  private ProgrammaManualeMaker programmaManualeMaker;


  @GetMapping("/corsa/insert/{programmaCorseId}")
  public String insertCorsa(@PathVariable("programmaCorseId") Long programmaCorseId, Model model)
      throws ResourcesDoesNotExistException {

    Utente utente = accountService.getLoggedUser();

    List<Linea> linee = risorseService.getLineeByAzienda(utente);
    List<Conducente> conducenti = risorseService.getConducentiByAzienda(utente);
    List<Mezzo> mezzi = risorseService.getMezziByAzienda(utente);

    if (linee.size() == 0 || mezzi.size() == 0 || conducenti.size() == 0) {
      throw new ResourcesDoesNotExistException("Una o più risorse non esistenti.");
    }
	ProgrammaCorse programmaCorse = programmaCorseService
            .getProgrammaCorseById(programmaCorseId).get();

    model.addAttribute("linee", linee);
    model.addAttribute("mezzi", mezzi);
    model.addAttribute("conducenti", conducenti);
    model.addAttribute("programmaCorse", programmaCorse);
    model.addAttribute("linea", new Linea());

    model.addAttribute("corsa", new Corsa());

    return "/insert-corsa";
  }

  @PostMapping("/corsa/submit/{programmaCorseId}")
  public ModelAndView processCorsa(@ModelAttribute("corsa") Corsa corsa,
                                   @RequestParam(value = "mezzo") List<Long> mezzi,
                                   @RequestParam(value = "linea") Long lineaId,
                                   @RequestParam(value = "conducente") List<Long> conducenti,
                                   @PathVariable(value = "programmaCorseId")
                                       Long programmaCorseId) {
    Set<Conducente> conducentiToAdd = new HashSet<Conducente>();
    Set<Mezzo> mezziToAdd = new HashSet<Mezzo>();

    for (Long conducenteId : conducenti) {
      Conducente conducente = risorseService.getConducente(conducenteId).get();
      conducentiToAdd.add(conducente);
    }

    for (Long mezzoId : mezzi) {
      Mezzo mezzo = risorseService.getMezzo(mezzoId).get();
      mezziToAdd.add(mezzo);
    }

    ProgrammaCorse programmaCorse =
        programmaCorseService.getProgrammaCorseById(programmaCorseId).get();
    Linea linea = risorseService.getLinea(lineaId).get();

    programmaManualeMaker.creaCorsa(corsa, linea, mezziToAdd, conducentiToAdd, programmaCorse);

    return new ModelAndView("redirect:/corsa/insert/" + programmaCorseId);
  }

  @GetMapping("/corsa/delete/{id}")
  public String deleteCorsa(@PathVariable("id") Long id) {
    Corsa corsa = corsaService.getCorsaById(id).get();
    corsaService.deleteCorsa(corsa);

    return "home";
  }
}