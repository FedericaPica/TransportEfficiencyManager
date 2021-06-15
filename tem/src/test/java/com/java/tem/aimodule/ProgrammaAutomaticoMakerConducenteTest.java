package com.java.tem.aimodule;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Conducente;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Linea;
import com.java.tem.model.programmacorseservice.entity.risorseservice.Mezzo;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import com.java.tem.model.programmacorseservice.repository.LineaRepository;

import junit.runner.Version;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.plugins.MockMaker;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.PowerMockitoCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import javax.swing.text.html.Option;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@WebAppConfiguration
class ProgrammaAutomaticoMakerConducenteTest {
	
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

    private static Logger logger = Logger.getLogger("mainLogger");
    
    private ArrayList<Object> item = new ArrayList<Object>();

    @MockBean
    private static Conducente conducente;
    @MockBean
    private static Linea linea;
    @MockBean
    private static Mezzo mezzo;
    @MockBean
    private static DatiGenerazione testDatiGen;
    
    private java.util.Optional<Linea> optLinea;
    private List<Conducente> conducenti = new ArrayList<Conducente>();

    @BeforeEach
    void setUp() {
    	programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
    	illegalValuesConducenti = new ArrayList<ArrayList<Object>>();
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
        mezzo.setTipo("TestMezzo");
        linea.setDestinazione("TestDest");
        linea.setPartenza("TestPart");
        linea.setDurata(60);
        linea.setNome("TestLinea");
        optLinea = Optional.of(linea);
        conducente.setCodiceFiscale("TestCF");
        conducente.setCognome("Cognome");
        conducente.setNome("Nome");
        
        conducenti.add(conducente);


    }

    /* START CHECK CONDUCENTE TESTS*/
    
    @Test
    void checkConducente1() {
    	illegalValuesConducenti = new ArrayList<ArrayList<Object>>();
    	listaDatiGenerazione = mock(List.class);
    	programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    	Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(0);
    	assertTrue(programmaAutomaticoMaker.checkConducente(conducente, testDatiGen, illegalValuesConducenti));
    }

    @Test
    void checkConducente2() {
    	listaDatiGenerazione = mock(List.class);
    	String codiceFiscale = "TestCF";
        item.add(conducente);
        item.add(LocalTime.of(9, 30, 30));
        item.add(LocalTime.of(10, 30, 30));
        illegalValuesConducenti.add(item);
        
    	LocalTime testTime = LocalTime.of(9, 31, 30);
    	programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
    	Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
    	Mockito.when(mock(Conducente.class).getCodiceFiscale()).thenReturn(codiceFiscale);
    	Mockito.when(mock(DatiGenerazione.class).getOrario()).thenReturn(testTime);

    	assertFalse(programmaAutomaticoMaker.checkConducente(conducente, testDatiGen, illegalValuesConducenti));
    }


    @Test
    void checkConducente3() {
        listaDatiGenerazione = mock(List.class);
        String codiceFiscale = "TestCF";
        when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
        when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
        when(mock(Conducente.class).getCodiceFiscale()).thenReturn(codiceFiscale);
        when(mock(DatiGenerazione.class).getConducente()).thenReturn(codiceFiscale);


        assertTrue(programmaAutomaticoMaker.checkConducente(conducente, testDatiGen, illegalValuesConducenti));
    }
    
    
    @Test
    void checkConducente4() {
        listaDatiGenerazione = mock(List.class);
        String codiceFiscale = "TestCF";
        when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
        when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
        when(mock(Conducente.class).getCodiceFiscale()).thenReturn(codiceFiscale);
        when(mock(DatiGenerazione.class).getConducente()).thenReturn(codiceFiscale);
        testDatiGen.setAndata(true);

        assertTrue(programmaAutomaticoMaker.checkConducente(conducente, testDatiGen, illegalValuesConducenti));
    }
    
   @Test
    void checkConducente5() {
        listaDatiGenerazione = mock(List.class);
        String codiceFiscale = "TestsF";
        item.add(conducente);
        item.add(LocalTime.of(9, 30, 30));
        item.add(LocalTime.of(10, 30, 30));
        illegalValuesConducenti.add(item);

        LocalTime testTime = LocalTime.of(11, 31, 30);
        programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
        Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(5);
        //Mockito.when(mock(LocalTime.class).isAfter(testTime)).thenReturn(false);

        when(mock(ArrayList.class).get(anyInt())).thenReturn(testDatiGen);
        when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
        when(mock(Conducente.class).getCodiceFiscale()).thenReturn(codiceFiscale);
        when(mock(DatiGenerazione.class).getConducente()).thenReturn(codiceFiscale);
        assertFalse(programmaAutomaticoMaker.checkConducente(conducente, testDatiGen, illegalValuesConducenti));
    }
   
   @Test
   void checkConducente6() {
       listaDatiGenerazione = mock(List.class);
       String codiceFiscale = "TestCF";
       item.add(conducente);
       item.add(LocalTime.of(9, 30, 30));
       item.add(LocalTime.of(10, 30, 30));
       illegalValuesConducenti.add(item);

       testDatiGen.setOrario(LocalTime.of(11, 30, 00));
       programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
       Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
       //Mockito.when(mock(LocalTime.class).isAfter(testTime)).thenReturn(false);
       //System.out.println(testDatiGen);
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
       assertFalse(programmaAutomaticoMaker.checkConducente(conducente, testDatiGen, illegalValuesConducenti));
   }
   
   @Test
   void checkConducente7() {
       listaDatiGenerazione = mock(List.class);
       String codiceFiscale = "TestCF";
       item.add(conducente);
       item.add(LocalTime.of(9, 30, 30));
       item.add(LocalTime.of(10, 30, 30));
       illegalValuesConducenti.add(item);

       testDatiGen.setOrario(LocalTime.of(11, 30, 00));
       programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
       Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
       //Mockito.when(mock(LocalTime.class).isAfter(testTime)).thenReturn(false);
       //System.out.println(testDatiGen);
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
       assertTrue(programmaAutomaticoMaker.checkConducente(conducente, testDatiGen, illegalValuesConducenti));
   }

   
   @Test
   void checkConducente8() {
       listaDatiGenerazione = mock(List.class);
       item.add(conducente);
       item.add(LocalTime.of(9, 30, 30));
       item.add(LocalTime.of(10, 30, 30));
       illegalValuesConducenti.add(item);

       testDatiGen.setOrario(LocalTime.of(7, 30, 00));
       programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
       Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
       //Mockito.when(mock(LocalTime.class).isAfter(testTime)).thenReturn(false);
       //System.out.println(testDatiGen);
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
       assertFalse(programmaAutomaticoMaker.checkConducente(conducente, testDatiGen, illegalValuesConducenti));
   }
   
   @Test
   void checkConducente9() {
       listaDatiGenerazione = mock(List.class);
       Conducente conducente2 = new Conducente();
       conducente2.setCodiceFiscale("TestCF2");
       conducente2.setCognome("Cognome");
       conducente2.setNome("Nome");
       item.add(conducente2);
       item.add(LocalTime.of(9, 30, 30));
       item.add(LocalTime.of(10, 30, 30));
       illegalValuesConducenti.add(item);
       

       
       testDatiGen.setOrario(LocalTime.of(7, 30, 00));
       programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);
       Mockito.when(listaDatiGenerazione.indexOf(testDatiGen)).thenReturn(1);
       //Mockito.when(mock(LocalTime.class).isAfter(testTime)).thenReturn(false);
       DatiGenerazione testDatiGen2 = new DatiGenerazione();
       testDatiGen2.setConducente("TestCF");
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
       assertTrue(programmaAutomaticoMaker.checkConducente(conducente, testDatiGen, illegalValuesConducenti));
   }
  

   /* END CHECK CONDUCENTE TESTS */ 

   
   
   /* START FORWARD CONDUCENTE TESTS */ 
   @Test
   void forwardConducente1() {
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
	   assertFalse(programmaAutomaticoMaker.forwardConducente(conducente, illegalValuesConducenti, testDatiGen, conducenti));
   }
   
   @Test
   void forwardConducente2() {
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
	   assertTrue(programmaAutomaticoMaker.forwardConducente(conducente, illegalValuesConducenti, testDatiGen, conducenti));
   }
   @Test
   void forwardConducente3() {
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
	   assertTrue(programmaAutomaticoMaker.forwardConducente(conducente, illegalValuesConducenti, testDatiGen, conducenti));
   }
   
   @Test
   void forwardConducente4() {
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
	   assertFalse(programmaAutomaticoMaker.forwardConducente(conducente, illegalValuesConducenti, testDatiGen, conducenti));
   }
   @Test
   void forwardConducente5() {
	   
	   listaDatiGenerazione = mock(List.class);
	   DatiGenerazione testDatiGen2 = new DatiGenerazione();
	   Conducente conducente2 = new Conducente();
       conducente2.setCodiceFiscale("TestCF2");
       conducente2.setCognome("Cognome2");
       conducente2.setNome("Nome2");
       item.add(conducente2);
       item.add(LocalTime.of(11, 30, 30));
       item.add(LocalTime.of(13, 30, 30));
       illegalValuesConducenti.add(item);
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

	   assertFalse(programmaAutomaticoMaker.forwardConducente(conducente, illegalValuesConducenti, testDatiGen, conducenti));
   }
   
   @Test
   void forwardConducente6() {
	   
	   listaDatiGenerazione = mock(List.class);
	   DatiGenerazione testDatiGen2 = new DatiGenerazione();
	   Conducente conducente2 = new Conducente();
       conducente2.setCodiceFiscale("TestCF2");
       conducente2.setCognome("Cognome2");
       conducente2.setNome("Nome2");
       conducenti.add(conducente2);
       item.add(conducente2);
       item.add(LocalTime.of(7, 30, 30));
       item.add(LocalTime.of(8, 30, 30));
       illegalValuesConducenti.add(item);
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

	   assertTrue(programmaAutomaticoMaker.forwardConducente(conducente, illegalValuesConducenti, testDatiGen, conducenti));
   }
   
   
   /* END FORWARD CONDUCENTE TESTS */

   /* START RICERCABACKTRACKING CONDUCENTE TESTS*/

    @Test
    void ricercaBacktrackingConducente1() {
        listaDatiGenerazione.add(testDatiGen);
        assertTrue(programmaAutomaticoMaker.ricercaBacktrackingConducente(conducenti, illegalValuesConducenti));
    }

    @Test
    void ricercaBacktrackingConducente2() {
        testDatiGen.setConducente(null);
        Conducente conducente2 = new Conducente();
        conducente2.setCodiceFiscale("TestCF2");
        conducente2.setCognome("Cognome2");
        conducente2.setNome("Nome2");
        conducenti.add(conducente2);
        listaDatiGenerazione.add(testDatiGen);

        when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
        programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);

        assertTrue(programmaAutomaticoMaker.ricercaBacktrackingConducente(conducenti, illegalValuesConducenti));
    }

    @Test
    void ricercaBacktrackingConducente3() {
        Conducente conducente2 = new Conducente();
        DatiGenerazione testDatiGen2 = new DatiGenerazione();
        testDatiGen2.setConducente(null);
        testDatiGen2.setMezzo("TestMezzo");
        testDatiGen2.setOrario(LocalTime.of(9, 21, 0, 0));
        testDatiGen2.setAndata(false);
        testDatiGen2.setAziendaId(1L);
        testDatiGen2.setAttesi(26);
        testDatiGen2.setLineaCorsa("TestLinea");
        testDatiGen2.setTraffico("No");
        conducente2.setCodiceFiscale("TestCF2");
        conducente2.setCognome("Cognome2");
        conducente2.setNome("Nome2");
        conducenti.add(conducente2);
        item.add(conducente);
        item.add(LocalTime.of(9, 15, 30));
        item.add(LocalTime.of(10, 30, 30));
        illegalValuesConducenti.add(item);
        listaDatiGenerazione.add(testDatiGen);
        listaDatiGenerazione.add(testDatiGen2);

        when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
        programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);

        assertTrue(programmaAutomaticoMaker.ricercaBacktrackingConducente(conducenti, illegalValuesConducenti));
    }
    
    @Test
    void ricercaBacktrackingConducente4() {
        testDatiGen.setConducente(null);
        Conducente conducente2 = new Conducente();
        DatiGenerazione testDatiGen2 = new DatiGenerazione();
        testDatiGen2.setConducente("TestCF2");
        testDatiGen2.setMezzo("TestMezzo");
        testDatiGen2.setOrario(LocalTime.of(9, 33, 0, 0));
        testDatiGen2.setAndata(false);
        testDatiGen2.setAziendaId(1L);
        testDatiGen2.setAttesi(26);
        testDatiGen2.setLineaCorsa("TestLinea");
        testDatiGen2.setTraffico("No");
        conducente2.setCodiceFiscale("TestCF2");
        conducente2.setCognome("Cognome2");
        conducente2.setNome("Nome2");
        conducenti.add(conducente2);
        conducenti.remove(0);
        conducenti.add(conducente);
        item.add(conducente);
        item.add(LocalTime.of(9, 15, 30));
        item.add(LocalTime.of(10, 30, 30));
        illegalValuesConducenti.add(item);
        listaDatiGenerazione.add(testDatiGen);
        listaDatiGenerazione.add(testDatiGen2);

        when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
        programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);

        assertTrue(programmaAutomaticoMaker.ricercaBacktrackingConducente(conducenti, illegalValuesConducenti));
    }
    
    @Test
    void ricercaBacktrackingConducente5() {
        testDatiGen.setConducente(null);
        Conducente conducente2 = new Conducente();
        DatiGenerazione testDatiGen2 = new DatiGenerazione();
        testDatiGen2.setConducente("TestCF2");
        testDatiGen2.setMezzo("TestMezzo");
        testDatiGen2.setOrario(LocalTime.of(9, 33, 0, 0));
        testDatiGen2.setAndata(false);
        testDatiGen2.setAziendaId(1L);
        testDatiGen2.setAttesi(26);
        testDatiGen2.setLineaCorsa("TestLinea");
        testDatiGen2.setTraffico("No");
        conducente2.setCodiceFiscale("TestCF2");
        conducente2.setCognome("Cognome2");
        conducente2.setNome("Nome2");
        conducenti.add(conducente2);
        conducenti.remove(0);
        conducenti.add(conducente);
        item.add(conducente);
        item.add(LocalTime.of(9, 15, 30));
        item.add(LocalTime.of(10, 30, 30));
        illegalValuesConducenti.add(item);
        listaDatiGenerazione.add(testDatiGen);
        listaDatiGenerazione.add(testDatiGen2);

        when(risorseService.getLineaByName(any(String.class))).thenReturn(Optional.of(linea));
        programmaAutomaticoMaker.setListaDatiGenerazione(listaDatiGenerazione);

        assertTrue(programmaAutomaticoMaker.ricercaBacktrackingConducente(conducenti, illegalValuesConducenti));
    }

    /* END RICERCABACKTRACKING CONDUCENTE TESTS*/
    
}