package com.java.tem.aimodule;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;

@AutoConfigureMockMvc
@WebAppConfiguration
public class ProgrammaAutomaticoMakerOtherTest {

  @InjectMocks
  private AccountService accountService;
  @InjectMocks
  private RisorseService risorseService;
  @InjectMocks
  private CorsaService corsaService;
  @InjectMocks
  private List<DatiGenerazione> listaDatiGenerazione = new ArrayList<DatiGenerazione>();
  @Mock
  private ArrayList<ArrayList<Object>> illegalValuesConducenti;
  @InjectMocks
  private ProgrammaAutomaticoMaker programmaAutomaticoMaker;

  @MockBean
  private static Conducente conducente;
  @MockBean
  private static Linea linea;
  @MockBean
  private static Mezzo mezzo;
  @MockBean
  private static DatiGenerazione testDatiGen;

  @BeforeEach
  void setUp() {
    programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
    illegalValuesConducenti = new ArrayList<ArrayList<Object>>();
    risorseService = new RisorseService();
    testDatiGen = new DatiGenerazione();
    testDatiGen.setConducente("TestConducente");
    testDatiGen.setMezzo("TestMezzo");
    testDatiGen.setOrario(LocalTime.of(9, 31, 0, 0));
    testDatiGen.setAndata(false);
    testDatiGen.setAziendaId(1L);
    testDatiGen.setAttesi(26);
    testDatiGen.setLineaCorsa("TestLinea");
    testDatiGen.setTraffico("No");
    programmaAutomaticoMaker.setAccountService(accountService);
    programmaAutomaticoMaker.setCorsaService(corsaService);
    programmaAutomaticoMaker.setRisorseService(risorseService);
  }

  @Test
  void doOperation() {
  }

  /* START CHECK ORARIO TESTS*/
  @Test
  void checkOrarioTrue() {
    programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
    LocalTime orario = LocalTime.of(9, 31, 0, 0);
    assertTrue(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
  }

  @Test
  void checkOrarioFalse() {
    programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
    LocalTime orario = LocalTime.of(10, 35, 0, 0);
    assertFalse(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
  }

  @Test
  void checkOrarioEqualsNoTraffico() {
    programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
    LocalTime orario = LocalTime.of(9, 31, 0, 0);
    testDatiGen.setTraffico("Si");
    assertFalse(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
  }

  @Test
  void checkOrarioNotEqualsTraffico() {
    programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
    LocalTime orario = LocalTime.of(9, 35, 0, 0);
    testDatiGen.setTraffico("Si");
    assertTrue(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
  }

  @Test
  void checkOrarioNotEqualsNoTraffico() {
    LocalTime orario = LocalTime.of(9, 35, 0, 0);
    testDatiGen.setTraffico("No");
    assertFalse(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
  }

  /* END CHECK ORARIO TESTS */

}
