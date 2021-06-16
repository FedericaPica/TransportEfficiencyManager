package com.java.tem.aimodule;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import com.java.tem.model.programmacorseservice.repository.LineaRepository;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;

@AutoConfigureMockMvc
@WebAppConfiguration

public class ProgrammaAutomaticoMakerMezzoTest {

  @InjectMocks
  private AccountService accountService;
  @InjectMocks
  private RisorseService risorseService;
  @InjectMocks
  private CorsaService corsaService;
  @InjectMocks
  private List<DatiGenerazione> listaDatiGenerazione = new ArrayList<DatiGenerazione>();
  @Mock
  private ArrayList<ArrayList<Object>> illegalValuesMezzi;
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
  private ArrayList<Object> item = new ArrayList<Object>();

  private java.util.Optional<Linea> optLinea;
  private List<Mezzo> mezzi = new ArrayList<Mezzo>();


  @BeforeEach
  void setUp() {
    programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
    illegalValuesMezzi = new ArrayList<ArrayList<Object>>();
    LineaRepository lineaRepository = mock(LineaRepository.class);
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
    risorseService.setLineaRepository(lineaRepository);
    programmaAutomaticoMaker.setAccountService(accountService);
    programmaAutomaticoMaker.setCorsaService(corsaService);
    programmaAutomaticoMaker.setRisorseService(risorseService);
    mezzo = new Mezzo();
    linea = new Linea();
    conducente = new Conducente();
    linea.setDestinazione("TestDest");
    linea.setPartenza("TestPart");
    linea.setDurata(60);
    linea.setNome("TestLinea");
    optLinea = Optional.of(linea);
    mezzo.setId(1L);
    mezzo.setTarga("AA123AA");
    mezzo.setCapienza(35);
    mezzo.setTipo("TestMezzo");

    mezzi.add(mezzo);


  }

  /* START CHECK MEZZO TESTS*/

  @Test
  void checkMezzo0() {
    illegalValuesMezzi = new ArrayList<ArrayList<Object>>();
    listaDatiGenerazione = mock(List.class);
    testDatiGen.setAttesi(50);
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    assertFalse(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }

  @Test
  void checkMezzo1() {
    illegalValuesMezzi = new ArrayList<ArrayList<Object>>();
    listaDatiGenerazione = mock(List.class);
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(0);
    assertTrue(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }

  @Test
  void checkMezzo2() {
    listaDatiGenerazione = mock(List.class);
    item.add(mezzo);
    item.add(LocalTime.of(9, 30, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);


    LocalTime testTime = LocalTime.of(9, 31, 30);
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
    Mockito.when(mock(DatiGenerazione.class).getOrario()).thenReturn(testTime);

    assertFalse(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }


  @Test
  void checkMezzo3() {
    listaDatiGenerazione = mock(List.class);
    Mezzo mezzo2 = new Mezzo();
    mezzo2.setId(2L);
    item.add(mezzo2);
    item.add(LocalTime.of(9, 30, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);
    when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    //when(mock(Mezzo.class).getId()).thenReturn(1L);
    when(mock(DatiGenerazione.class).getMezzo()).thenReturn(mezzo.getTipo());


    assertTrue(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }


  @Test
  void checkMezzo4() {
    listaDatiGenerazione = mock(List.class);
    when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    when(mock(Mezzo.class).getId()).thenReturn(1L);
    when(mock(DatiGenerazione.class).getMezzo()).thenReturn(mezzo.getTipo());
    testDatiGen.setAndata(true);

    assertTrue(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }

  @Test
  void checkMezzo5() {
    listaDatiGenerazione = mock(List.class);
    item.add(mezzo);
    item.add(LocalTime.of(9, 30, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);

    LocalTime testTime = LocalTime.of(11, 31, 30);
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(5);

    when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    when(mock(Mezzo.class).getId()).thenReturn(1L);
    when(mock(DatiGenerazione.class).getMezzo()).thenReturn(mezzo.getTipo());
    assertFalse(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }

  @Test
  void checkMezzo6() {
    listaDatiGenerazione = mock(List.class);
    item.add(mezzo);
    item.add(LocalTime.of(9, 30, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);

    testDatiGen.setOrario(LocalTime.of(11, 30, 00));
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(9, 31, 0, 0));
    testDatiGen2.setAndata(false);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");
    when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);
    assertTrue(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }

  @Test
  void checkMezzo7() {
    listaDatiGenerazione = mock(List.class);
    item.add(mezzo);
    item.add(LocalTime.of(9, 30, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);

    testDatiGen.setOrario(LocalTime.of(11, 30, 00));
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF2");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(9, 31, 0, 0));
    testDatiGen2.setAndata(true);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");
    when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);
    when(mock(DatiGenerazione.class).isAndata()).thenReturn(false);
    assertTrue(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }


  @Test
  void checkMezzo8() {
    listaDatiGenerazione = mock(List.class);
    item.add(mezzo);
    item.add(LocalTime.of(9, 30, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);

    testDatiGen.setOrario(LocalTime.of(7, 30, 00));
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(9, 31, 0, 0));
    testDatiGen2.setAndata(false);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");
    when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);
    assertTrue(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }

  @Test
  void checkMezzo9() {
    listaDatiGenerazione = mock(List.class);
    Mezzo mezzo2 = new Mezzo();
    mezzo2.setId(1L);
    mezzo2.setTipo("TestTipo2");
    mezzo2.setTarga("BB321BB");
    mezzo2.setCapienza(40);
    item.add(mezzo2);
    item.add(LocalTime.of(9, 30, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);

    testDatiGen.setOrario(LocalTime.of(7, 30, 00));
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("1");
    testDatiGen2.setOrario(LocalTime.of(9, 31, 0, 0));
    testDatiGen2.setAndata(true);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");
    when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);
    assertTrue(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }

  @Test
  void checkMezzo10() {
    listaDatiGenerazione = mock(List.class);
    Mezzo mezzo2 = new Mezzo();
    mezzo2.setId(1L);
    mezzo2.setTipo("TestTipo2");
    mezzo2.setTarga("BB321BB");
    mezzo2.setCapienza(40);
    item.add(mezzo2);
    item.add(LocalTime.of(9, 30, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);

    testDatiGen.setOrario(LocalTime.of(7, 30, 00));
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("1");
    testDatiGen2.setOrario(LocalTime.of(9, 31, 0, 0));
    testDatiGen2.setAndata(false);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");
    when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);
    assertFalse(programmaAutomaticoMaker.checkMezzo(mezzo, testDatiGen, illegalValuesMezzi));
  }



  /* END CHECK MEZZO TESTS */


  /* START FORWARD MEZZO TESTS */
  @Test
  void forwardMezzo1() {
    listaDatiGenerazione = mock(List.class);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(9, 50, 0, 0));
    testDatiGen2.setAndata(true);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(optLinea);
    when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(0);
    when(listaDatiGenerazione.size()).thenReturn(5);
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);
    assertFalse(
        programmaAutomaticoMaker.forwardMezzo(mezzo, illegalValuesMezzi, testDatiGen, mezzi));
  }

  @Test
  void forwardMezzo2() {
    listaDatiGenerazione = mock(List.class);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(7, 30, 0, 0));
    testDatiGen2.setAndata(true);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(optLinea);
    when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(0);
    when(listaDatiGenerazione.size()).thenReturn(5);
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);
    assertTrue(
        programmaAutomaticoMaker.forwardMezzo(mezzo, illegalValuesMezzi, testDatiGen, mezzi));
  }

  @Test
  void forwardMezzo3() {
    listaDatiGenerazione = mock(List.class);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(15, 30, 0, 0));
    testDatiGen2.setAndata(true);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(optLinea);
    when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(0);
    when(listaDatiGenerazione.size()).thenReturn(5);
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);
    assertTrue(
        programmaAutomaticoMaker.forwardMezzo(mezzo, illegalValuesMezzi, testDatiGen, mezzi));
  }

  @Test
  void forwardMezzo4() {
    listaDatiGenerazione = mock(List.class);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(10, 00, 0, 0));
    testDatiGen2.setAndata(true);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(optLinea);
    when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(0);
    when(listaDatiGenerazione.size()).thenReturn(5);
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);
    assertFalse(
        programmaAutomaticoMaker.forwardMezzo(mezzo, illegalValuesMezzi, testDatiGen, mezzi));
  }

  @Test
  void forwardMezzo5() {

    listaDatiGenerazione = mock(List.class);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    Mezzo mezzo2 = new Mezzo();
    mezzo2.setTipo("TestTipo2");
    mezzo2.setTarga("BB321BB");
    mezzo2.setCapienza(40);
    mezzi.add(mezzo2);
    item.add(mezzo2);
    item.add(LocalTime.of(7, 30, 30));
    item.add(LocalTime.of(8, 30, 30));
    illegalValuesMezzi.add(item);
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(10, 00, 0, 0));
    testDatiGen2.setAndata(true);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");

    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(optLinea);
    when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(0);
    when(listaDatiGenerazione.size()).thenReturn(5);
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);

    assertTrue(
        programmaAutomaticoMaker.forwardMezzo(mezzo, illegalValuesMezzi, testDatiGen, mezzi));
  }

  @Test
  void forwardMezzo6() {

    listaDatiGenerazione = mock(List.class);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    Mezzo mezzo2 = new Mezzo();
    mezzo2.setTipo("TestTipo2");
    mezzo2.setTarga("BB321BB");
    mezzo2.setCapienza(40);
    mezzi.add(mezzo2);
    item.add(mezzo2);
    item.add(LocalTime.of(7, 30, 30));
    item.add(LocalTime.of(8, 30, 30));
    illegalValuesMezzi.add(item);
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(10, 00, 0, 0));
    testDatiGen2.setAndata(true);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");

    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(optLinea);
    when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(0);
    when(listaDatiGenerazione.size()).thenReturn(5);
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);

    assertTrue(
        programmaAutomaticoMaker.forwardMezzo(mezzo, illegalValuesMezzi, testDatiGen, mezzi));
  }

  @Test
  void forwardMezzo7() {

    listaDatiGenerazione = mock(List.class);
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    Mezzo mezzo2 = new Mezzo();
    mezzo2.setTipo("TestTipo2");
    mezzo2.setTarga("BB321BB");
    mezzo2.setCapienza(40);
    mezzi.add(mezzo2);
    item.add(mezzo2);
    item.add(LocalTime.of(8, 30, 30));
    item.add(LocalTime.of(11, 30, 30));
    illegalValuesMezzi.add(item);
    testDatiGen2.setConducente("TestCF");
    testDatiGen2.setMezzo("TestMezzo2");
    testDatiGen2.setOrario(LocalTime.of(8, 00, 0, 0));
    testDatiGen2.setAndata(true);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea2");
    testDatiGen2.setTraffico("No");

    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    when(risorseService.getLineaByName(any(String.class))).thenReturn(optLinea);
    when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(0);
    when(listaDatiGenerazione.size()).thenReturn(5);
    when(listaDatiGenerazione.get(anyInt())).thenReturn(testDatiGen2);

    assertTrue(
        programmaAutomaticoMaker.forwardMezzo(mezzo, illegalValuesMezzi, testDatiGen, mezzi));
  }


  /* END FORWARD MEZZO TESTS */

  /* START RICERCABACKTRACKING MEZZO TESTS*/

  @Test
  void ricercaBacktrackingMezzo1() {
    listaDatiGenerazione.add(testDatiGen);
    assertTrue(programmaAutomaticoMaker.ricercaBacktrackingMezzo(mezzi, illegalValuesMezzi));
  }

  @Test
  void ricercaBacktrackingMezzo2() {
    testDatiGen.setMezzo(null);
    Mezzo mezzo2 = new Mezzo();
    mezzo2.setId(1L);
    mezzo2.setTarga("AA123AA2");
    mezzo2.setCapienza(352);
    mezzo2.setTipo("TestMezzo2");
    listaDatiGenerazione.add(testDatiGen);

    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);

    assertTrue(programmaAutomaticoMaker.ricercaBacktrackingMezzo(mezzi, illegalValuesMezzi));
  }

  @Test
  void ricercaBacktrackingMezzo3() {
    Mezzo mezzo2 = new Mezzo();
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("Conducente");
    testDatiGen2.setMezzo(null);
    testDatiGen2.setOrario(LocalTime.of(9, 21, 0, 0));
    testDatiGen2.setAndata(false);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea");
    testDatiGen2.setTraffico("No");
    mezzo2.setId(1L);
    mezzo2.setTarga("AA123AA2");
    mezzo2.setCapienza(352);
    mezzo2.setTipo("TestMezzo2");
    mezzi.add(mezzo2);
    item.add(mezzo);
    item.add(LocalTime.of(9, 15, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);
    listaDatiGenerazione.add(testDatiGen);
    listaDatiGenerazione.add(testDatiGen2);

    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);

    assertFalse(programmaAutomaticoMaker.ricercaBacktrackingMezzo(mezzi, illegalValuesMezzi));
  }

  @Test
  void ricercaBacktrackingMezzo4() {
    testDatiGen.setMezzo(null);
    Mezzo mezzo2 = new Mezzo();
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF2");
    testDatiGen2.setMezzo("TestMezzo");
    testDatiGen2.setOrario(LocalTime.of(9, 33, 0, 0));
    testDatiGen2.setAndata(false);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea");
    testDatiGen2.setTraffico("No");
    mezzo2.setId(1L);
    mezzo2.setTarga("AA123AA2");
    mezzo2.setCapienza(352);
    mezzo2.setTipo("TestMezzo2");
    mezzi.add(mezzo2);
    mezzi.remove(0);
    mezzi.add(mezzo);
    item.add(mezzo);
    item.add(LocalTime.of(9, 15, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);
    listaDatiGenerazione.add(testDatiGen);
    listaDatiGenerazione.add(testDatiGen2);

    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);

    assertTrue(programmaAutomaticoMaker.ricercaBacktrackingMezzo(mezzi, illegalValuesMezzi));
  }

  @Test
  void ricercaBacktrackingMezzo5() {
    testDatiGen.setMezzo(null);
    Mezzo mezzo2 = new Mezzo();
    DatiGenerazione testDatiGen2 = new DatiGenerazione();
    testDatiGen2.setConducente("TestCF2");
    testDatiGen2.setMezzo("TestMezzo");
    testDatiGen2.setOrario(LocalTime.of(9, 33, 0, 0));
    testDatiGen2.setAndata(false);
    testDatiGen2.setAziendaId(1L);
    testDatiGen2.setAttesi(26);
    testDatiGen2.setLineaCorsa("TestLinea");
    testDatiGen2.setTraffico("No");
    mezzo2.setId(1L);
    mezzo2.setTarga("AA123AA2");
    mezzo2.setCapienza(352);
    mezzo2.setTipo("TestMezzo2");
    mezzi.add(mezzo2);
    mezzi.remove(0);
    mezzi.add(mezzo);
    item.add(mezzo);
    item.add(LocalTime.of(9, 15, 30));
    item.add(LocalTime.of(10, 30, 30));
    illegalValuesMezzi.add(item);
    listaDatiGenerazione.add(testDatiGen);
    listaDatiGenerazione.add(testDatiGen2);

    when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
    programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);

    assertTrue(programmaAutomaticoMaker.ricercaBacktrackingMezzo(mezzi, illegalValuesMezzi));
  }

  /* END RICERCABACKTRACKING MEZZO TESTS*/

}
