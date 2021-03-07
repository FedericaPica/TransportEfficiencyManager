package com.java.tem.controller;

import com.java.tem.aimodule.ProgrammaAutomaticoMaker;
import com.java.tem.exceptions.GenerationTypeNotFoundException;
import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.accountservice.entity.Utente;
import com.java.tem.model.programmacorseservice.entity.Corsa;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorseService;
import java.util.List;
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
public class ProgrammaCorseController {

  @Autowired
  private ProgrammaCorseService programmaCorseService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private CorsaService corsaService;

  @Autowired
  private ProgrammaAutomaticoMaker programmaAutomaticoMaker;


  @GetMapping("/programmacorse")
  public String homeProgrammaCorse() {
    return "home-programma-corse";
  }

  @GetMapping("/programmacorse/list")
  public String listProgrammaCorse(Model model) {
    Utente utente = accountService.getLoggedUser();
    boolean isAdmin = accountService.isAdmin();
    List<ProgrammaCorse> programmiList =
        programmaCorseService.getProgrammaCorseByAzienda(utente);
    model.addAttribute("listaProgrammi", programmiList);
    model.addAttribute("azienda", utente);
    model.addAttribute("adminCheck", isAdmin);
    return "list-programmacorse";
  }

  @GetMapping("/programmacorse/list/{aziendaId}")
  public String listProgrammaCorseByAzienda(@PathVariable("aziendaId") Long id, Model model) {
    Utente azienda = accountService.getUserById(id);
    boolean isAdmin = accountService.isAdmin();
    List<ProgrammaCorse> programmiList =
        programmaCorseService.getProgrammaCorseByAzienda(azienda);
    model.addAttribute("listaProgrammi", programmiList);
    model.addAttribute("azienda", azienda);
    model.addAttribute("adminCheck", isAdmin);
    return "list-programmacorse";
  }

  @GetMapping("/programmacorse/insert")
  public String insertProgramma(@RequestParam(name = "type") String type, Model model) throws
      GenerationTypeNotFoundException {
    if(!type.equals("automatico") && !type.equals("manuale")) {
      throw new GenerationTypeNotFoundException("Tipo di generazione non supportata");
    }
    model.addAttribute("programmaCorse", new ProgrammaCorse());
    model.addAttribute("submit", "/programmacorse/"+type+"/submit");
    return "insert-programmacorse";
  }

  @PostMapping("/programmacorse/automatico/submit")
  public ModelAndView insertProgrammaAutomatico(@ModelAttribute("programmaCorse")
                                                ProgrammaCorse programmaCorse) {

    ProgrammaCorse generato =
        programmaCorseService.generaProgrammaCorse("automatico", programmaCorse);


    return new ModelAndView("redirect:/programmacorse/dettaglio/" + generato.getId());
  }

  @GetMapping("/programmacorse/delete/{id}")
  public String deleteProgrammaCorse(@PathVariable("id") Long id) {
    ProgrammaCorse programmaCorse = programmaCorseService.getProgrammaCorseById(id).get();
    programmaCorseService.deleteProgrammaCorse(programmaCorse);

    return "index";
  }

  @PostMapping("/programmacorse/manuale/submit")
  public ModelAndView processProgrammaManuale(
      @ModelAttribute("programmaCorse") ProgrammaCorse programmaCorse) {
    programmaCorseService.generaProgrammaCorse("manuale", programmaCorse);

    return new ModelAndView("redirect:/corsa/insert/" + programmaCorse.getId());
  }

  @GetMapping("/programmacorse/dettaglio/{id}")
  public String detailProgrammaCorse(@PathVariable("id") Long id, Model model) {
    ProgrammaCorse programmaCorse = programmaCorseService.getProgrammaCorseById(id).get();
    List<Corsa> listaCorse = corsaService.getCorseByProgramma(programmaCorse);
    boolean isAdmin = accountService.isAdmin();
    model.addAttribute("listaCorse", listaCorse);
    model.addAttribute("programmaCorse", programmaCorse);
    model.addAttribute("adminCheck", isAdmin);
    return "detail-programmacorse";
  }

  @GetMapping("/programmacorse/{aziendaId}/dettaglio/{id}")
  public String detailProgrammaCorse(@PathVariable("aziendaId") Long aziendaId,
                                     @PathVariable("id") Long id,
                                     Model model) {
    ProgrammaCorse programmaCorse = programmaCorseService.getProgrammaCorseById(id).get();
    List<Corsa> listaCorse = corsaService.getCorseByProgramma(programmaCorse);
    boolean isAdmin = accountService.isAdmin();
    model.addAttribute("listaCorse", listaCorse);
    model.addAttribute("adminCheck", isAdmin);
    model.addAttribute("programmaCorse", programmaCorse);
    return "detail-programmacorse";
  }
}
