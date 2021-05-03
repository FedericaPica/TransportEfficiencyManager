package com.java.tem.aimodule;

import com.java.tem.model.accountservice.entity.AccountService;
import com.java.tem.model.programmacorseservice.entity.CorsaService;
import com.java.tem.model.programmacorseservice.entity.risorseservice.RisorseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@AutoConfigureMockMvc
@WebAppConfiguration
class ProgrammaAutomaticoMakerTest {

    @InjectMocks
    private AccountService accountService;
    @InjectMocks
    private RisorseService risorseService;
    @InjectMocks
    private CorsaService corsaService;
    @InjectMocks
    private List<DatiGenerazione> listaDatiGenerazione = new ArrayList<DatiGenerazione>();

    @InjectMocks
    private ProgrammaAutomaticoMaker programmaAutomaticoMaker;

    private static Logger logger = Logger.getLogger("mainLogger");

    @MockBean
    private static DatiGenerazione testDatiGen;

    @BeforeEach
    void setUp() {
        testDatiGen = new DatiGenerazione();
        testDatiGen.setConducente("TestConducente");
        testDatiGen.setMezzo("TestMezzo");
        testDatiGen.setOrario(LocalTime.of(9, 30, 0, 0));
        testDatiGen.setAndata(false);
        testDatiGen.setAziendaId(1L);
        testDatiGen.setAttesi(26);
        testDatiGen.setLineaCorsa("TestLinea");
        testDatiGen.setTraffico("No");

    }

    @Test
    void doOperation() {

    }

    @Test
    void checkOrarioTrue() {
        programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
        LocalTime orario = LocalTime.of(9, 30, 0, 0);
        System.out.println(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
        assertTrue(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
    }
    
    @Test
    void checkOrarioFalse() {
        programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
        LocalTime orario = LocalTime.of(9, 35, 0, 0);
        System.out.println(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
        assertFalse(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
    }
    
    @Test
    void checkOrarioEqualsNoTraffico() {
        programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
        LocalTime orario = LocalTime.of(9, 30, 0, 0);
        testDatiGen.setTraffico("Si");
        System.out.println(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
        assertFalse(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
    }

    @Test
    void checkOrarioNotEqualsTraffico() {
        programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
        LocalTime orario = LocalTime.of(9, 35, 0, 0);
        testDatiGen.setTraffico("Si");
        System.out.println(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
        assertTrue(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
    }
    
    @Test
    void checkOrarioNotEqualsNoTraffico() {
        programmaAutomaticoMaker = new ProgrammaAutomaticoMaker();
        LocalTime orario = LocalTime.of(9, 35, 0, 0);
        testDatiGen.setTraffico("No");
        System.out.println(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
        assertFalse(programmaAutomaticoMaker.checkOrario(testDatiGen, orario));
    }
    
    
    @Test
    void checkConducente() {
    	
    }

    @Test
    void checkMezzo() {
    }
}