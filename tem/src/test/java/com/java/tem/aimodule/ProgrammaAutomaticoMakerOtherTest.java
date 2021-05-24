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
