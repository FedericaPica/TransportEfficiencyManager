package com.java.tem.katalon;

import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

@TestMethodOrder(OrderAnnotation.class)
public class SystemTesting {

  private static WebDriver driver;
  @SuppressWarnings("unused")
  private static String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  /**
   * SetUp.
   */
  @BeforeEach
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }


  @Test
  public void testRegistrazioneVuota() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testRegistrazioneUsernameErrato() throws Exception {
    driver.get("http://localhost:8080/register");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)="
        + "'Registrazione azienda'])[1]/following::div[2]")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("B");
    driver.findElement(By.id("denominazione")).clear();
    driver.findElement(By.id("denominazione")).sendKeys("Bus Srl");
    driver.findElement(By.id("partitaIVA")).clear();
    driver.findElement(By.id("partitaIVA")).sendKeys("08100750010");
    driver.findElement(By.id("telefono")).clear();
    driver.findElement(By.id("telefono")).sendKeys("08257654334");
    driver.findElement(By.id("fax")).clear();
    driver.findElement(By.id("fax")).sendKeys("800909396");
    driver.findElement(By.id("indirizzo")).clear();
    driver.findElement(By.id("indirizzo")).sendKeys("Via Roma n 13");
    driver.findElement(By.id("cap")).clear();
    driver.findElement(By.id("cap")).sendKeys("89700");
    driver.findElement(By.id("citta")).clear();
    driver.findElement(By.id("citta")).sendKeys("Verona");
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testRegistrazioneNomeAziendaErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)="
        + "'Registrazione azienda'])[1]/following::div[2]")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("AziendaBus");
    driver.findElement(By.id("denominazione")).clear();
    driver.findElement(By.id("denominazione")).sendKeys("Bu$ srl");
    driver.findElement(By.id("partitaIVA")).clear();
    driver.findElement(By.id("partitaIVA")).sendKeys("08100750010");
    driver.findElement(By.id("telefono")).clear();
    driver.findElement(By.id("telefono")).sendKeys("08257654334");
    driver.findElement(By.id("fax")).clear();
    driver.findElement(By.id("fax")).sendKeys("800909396");
    driver.findElement(By.id("indirizzo")).clear();
    driver.findElement(By.id("indirizzo")).sendKeys("Via Roma n 13");
    driver.findElement(By.id("cap")).clear();
    driver.findElement(By.id("cap")).sendKeys("89700");
    driver.findElement(By.id("citta")).clear();
    driver.findElement(By.id("citta")).sendKeys("Verona");
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testRegistrazionePswErrata() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)="
        + "'Registrazione azienda'])[1]/following::div[2]")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("test");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("AziendaBus");
    driver.findElement(By.id("denominazione")).clear();
    driver.findElement(By.id("denominazione")).sendKeys("Bus srl");
    driver.findElement(By.id("partitaIVA")).clear();
    driver.findElement(By.id("partitaIVA")).sendKeys("08100750010");
    driver.findElement(By.id("telefono")).clear();
    driver.findElement(By.id("telefono")).sendKeys("08257654334");
    driver.findElement(By.id("fax")).clear();
    driver.findElement(By.id("fax")).sendKeys("800909396");
    driver.findElement(By.id("indirizzo")).clear();
    driver.findElement(By.id("indirizzo")).sendKeys("Via Roma n 13");
    driver.findElement(By.id("cap")).clear();
    driver.findElement(By.id("cap")).sendKeys("89700");
    driver.findElement(By.id("citta")).clear();
    driver.findElement(By.id("citta")).sendKeys("Verona");
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testRegistrazionePivaErrata() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)="
        + "'Registrazione azienda'])[1]/following::div[2]")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("AziendaBus");
    driver.findElement(By.id("denominazione")).clear();
    driver.findElement(By.id("denominazione")).sendKeys("Bus srl");
    driver.findElement(By.id("partitaIVA")).clear();
    driver.findElement(By.id("partitaIVA")).sendKeys("0810075001$");
    driver.findElement(By.id("telefono")).clear();
    driver.findElement(By.id("telefono")).sendKeys("08257654334");
    driver.findElement(By.id("fax")).clear();
    driver.findElement(By.id("fax")).sendKeys("800909396");
    driver.findElement(By.id("indirizzo")).clear();
    driver.findElement(By.id("indirizzo")).sendKeys("Via Roma n 13");
    driver.findElement(By.id("cap")).clear();
    driver.findElement(By.id("cap")).sendKeys("89700");
    driver.findElement(By.id("citta")).clear();
    driver.findElement(By.id("citta")).sendKeys("Verona");
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testRegistrazioneTelErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)="
        + "'Registrazione azienda'])[1]/following::div[2]")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("AziendaBus");
    driver.findElement(By.id("denominazione")).clear();
    driver.findElement(By.id("denominazione")).sendKeys("Bus srl");
    driver.findElement(By.id("partitaIVA")).clear();
    driver.findElement(By.id("partitaIVA")).sendKeys("08100750010");
    driver.findElement(By.id("telefono")).clear();
    driver.findElement(By.id("telefono")).sendKeys("0825765");
    driver.findElement(By.id("fax")).clear();
    driver.findElement(By.id("fax")).sendKeys("800909396");
    driver.findElement(By.id("indirizzo")).clear();
    driver.findElement(By.id("indirizzo")).sendKeys("Via Roma n 13");
    driver.findElement(By.id("cap")).clear();
    driver.findElement(By.id("cap")).sendKeys("89700");
    driver.findElement(By.id("citta")).clear();
    driver.findElement(By.id("citta")).sendKeys("Verona");
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testRegistrazioneFaxErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)="
        + "'Registrazione azienda'])[1]/following::div[2]")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("AziendaBus");
    driver.findElement(By.id("denominazione")).clear();
    driver.findElement(By.id("denominazione")).sendKeys("Bus srl");
    driver.findElement(By.id("partitaIVA")).clear();
    driver.findElement(By.id("partitaIVA")).sendKeys("08100750010");
    driver.findElement(By.id("telefono")).clear();
    driver.findElement(By.id("telefono")).sendKeys("08257654334");
    driver.findElement(By.id("fax")).clear();
    driver.findElement(By.id("fax")).sendKeys("800");
    driver.findElement(By.id("indirizzo")).clear();
    driver.findElement(By.id("indirizzo")).sendKeys("Via Roma n 13");
    driver.findElement(By.id("cap")).clear();
    driver.findElement(By.id("cap")).sendKeys("89700");
    driver.findElement(By.id("citta")).clear();
    driver.findElement(By.id("citta")).sendKeys("Verona");
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testRegistrazioneIndirizzoErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)="
        + "'Registrazione azienda'])[1]/following::div[2]")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("AziendaBus");
    driver.findElement(By.id("denominazione")).clear();
    driver.findElement(By.id("denominazione")).sendKeys("Bus srl");
    driver.findElement(By.id("partitaIVA")).clear();
    driver.findElement(By.id("partitaIVA")).sendKeys("08100750010");
    driver.findElement(By.id("telefono")).clear();
    driver.findElement(By.id("telefono")).sendKeys("08257654334");
    driver.findElement(By.id("fax")).clear();
    driver.findElement(By.id("fax")).sendKeys("800909396");
    driver.findElement(By.id("indirizzo")).clear();
    driver.findElement(By.id("indirizzo")).sendKeys("V");
    driver.findElement(By.id("cap")).clear();
    driver.findElement(By.id("cap")).sendKeys("89700");
    driver.findElement(By.id("citta")).clear();
    driver.findElement(By.id("citta")).sendKeys("Verona");
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  @Order(1)
  public void testRegistrazioneOk() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)="
        + "'Registrazione azienda'])[1]/following::div[2]")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("AziendaBus");
    driver.findElement(By.id("denominazione")).clear();
    driver.findElement(By.id("denominazione")).sendKeys("Bus srl");
    driver.findElement(By.id("partitaIVA")).clear();
    driver.findElement(By.id("partitaIVA")).sendKeys("08100750010");
    driver.findElement(By.id("telefono")).clear();
    driver.findElement(By.id("telefono")).sendKeys("08257654334");
    driver.findElement(By.id("fax")).clear();
    driver.findElement(By.id("fax")).sendKeys("800909396");
    driver.findElement(By.id("indirizzo")).clear();
    driver.findElement(By.id("indirizzo")).sendKeys("Via Roma n 13");
    driver.findElement(By.id("cap")).clear();
    driver.findElement(By.id("cap")).sendKeys("89700");
    driver.findElement(By.id("citta")).clear();
    driver.findElement(By.id("citta")).sendKeys("Verona");
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testInserimentoLineaVuota() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='bus']")).click();
    driver.findElement(By.xpath("//div[@id='main']/div[3]/h2/a/b")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/div[5]/button/b")).click();
  }

  @Test
  public void testInserimentoLineaDestErrata() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.linkText("INSERISCI RISORSE")).click();
    driver.findElement(By.xpath("//div[@id='main']/div[3]/h2/a/b")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys("NA08");
    driver.findElement(By.id("partenza")).clear();
    driver.findElement(By.id("partenza")).sendKeys("Napoli");
    driver.findElement(By.id("durata")).clear();
    driver.findElement(By.id("durata")).sendKeys("50");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/div[5]/button/b")).click();
  }

  @Test
  public void testInserimentoLineaNomeErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.linkText("INSERISCI RISORSE")).click();
    driver.findElement(By.xpath("//div[@id='main']/div[3]/h2/a/b")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys("N");
    driver.findElement(By.id("partenza")).clear();
    driver.findElement(By.id("partenza")).sendKeys("Napoli");
    driver.findElement(By.id("destinazione")).clear();
    driver.findElement(By.id("destinazione")).sendKeys("Avellino");
    driver.findElement(By.id("durata")).clear();
    driver.findElement(By.id("durata")).sendKeys("50");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/div[5]/button/b")).click();
  }

  @Test
  @Order(2)
  public void testInserimentoLineaOk() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.linkText("INSERISCI RISORSE")).click();
    driver.findElement(By.xpath("//div[@id='main']/div[3]/h2/a/b")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys("NA08");
    driver.findElement(By.id("partenza")).clear();
    driver.findElement(By.id("partenza")).sendKeys("Napoli");
    driver.findElement(By.id("destinazione")).clear();
    driver.findElement(By.id("destinazione")).sendKeys("Avellino");
    driver.findElement(By.id("durata")).clear();
    driver.findElement(By.id("durata")).sendKeys("50");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/div[5]/button/b")).click();
  }


  @Test
  public void testInserimentoConducenteVuoto() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.linkText("INSERISCI RISORSE")).click();
    driver.findElement(By.xpath("//div[@id='main']/div[2]/h2/a/b")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("codiceFiscale")).sendKeys(Keys.ENTER);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
  }

  @Test
  public void testInserimentoConducenteCognomeErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.linkText("INSERISCI RISORSE")).click();
    driver.findElement(By.xpath("//div[@id='main']/div[2]/h2/a/b")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys("Paolo");
    driver.findElement(By.id("codiceFiscale")).clear();
    driver.findElement(By.id("codiceFiscale")).sendKeys("NREPLA80C15H501T");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div[2]/button/b")).click();
  }

  @Test
  public void testInserimentoConducenteCfErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.linkText("INSERISCI RISORSE")).click();
    driver.findElement(By.xpath("//div[@id='main']/div[2]/h2/a/b")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys("Paolo");
    driver.findElement(By.id("cognome")).click();
    driver.findElement(By.id("cognome")).clear();
    driver.findElement(By.id("cognome")).sendKeys("Neri");
    driver.findElement(By.id("codiceFiscale")).clear();
    driver.findElement(By.id("codiceFiscale")).sendKeys("NREPLA80C15H501TT");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div[2]/button/b")).click();
  }

  @Test
  @Order(3)
  public void testInserimentoConducenteOk() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.linkText("INSERISCI RISORSE")).click();
    driver.findElement(By.xpath("//div[@id='main']/div[2]/h2/a/b")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys("Paolo");
    driver.findElement(By.id("cognome")).click();
    driver.findElement(By.id("cognome")).clear();
    driver.findElement(By.id("cognome")).sendKeys("Neri");
    driver.findElement(By.id("codiceFiscale")).clear();
    driver.findElement(By.id("codiceFiscale")).sendKeys("NREPLA80C15H501T");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div[2]/button/b")).click();
  }

  @Test
  public void testInserimentoMezzoVuoto() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='bus']")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/h2/a/b")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/div[4]/button/b")).click();
  }

  @Test
  public void testInserimentoMezzoTargaErrata() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='bus']")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/h2/a/b")).click();
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("55");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.id("targa")).click();
    driver.findElement(By.id("targa")).clear();
    driver.findElement(By.id("targa")).sendKeys("PL090S");
    driver.findElement(By.id("tipo")).clear();
    driver.findElement(By.id("tipo")).sendKeys("Pullman S");
    driver.findElement(By.id("tipo")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testInserimentoMezzoTipoErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='bus']")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/h2/a/b")).click();
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("55");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.id("targa")).click();
    driver.findElement(By.id("targa")).clear();
    driver.findElement(By.id("targa")).sendKeys("PL090SC");
    driver.findElement(By.id("tipo")).clear();
    driver.findElement(By.id("tipo")).sendKeys("P");
    driver.findElement(By.id("tipo")).sendKeys(Keys.ENTER);
  }

  @Test
  @Order(4)
  public void testInserimentoMezzoOk() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='bus']")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/h2/a/b")).click();
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("55");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.id("targa")).click();
    driver.findElement(By.id("targa")).clear();
    driver.findElement(By.id("targa")).sendKeys("PL090SC");
    driver.findElement(By.id("tipo")).clear();
    driver.findElement(By.id("tipo")).sendKeys("Pullman S");
    driver.findElement(By.id("tipo")).sendKeys(Keys.ENTER);
  }


  @Test
  public void testInserimentoDatiCorsaVuoto() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("INSERISCI DATI CORSE")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/form/div[2]/button/b")).click();
  }

  @Test
  public void testInserimentoDatiCorsaLineaErrata() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='people']")).click();
    driver.findElement(By.id("lineaCorsa")).click();
    driver.findElement(By.id("lineaCorsa")).clear();
    driver.findElement(By.id("lineaCorsa")).sendKeys("N");
    driver.findElement(By.id("orarioCorsa")).clear();
    driver.findElement(By.id("orarioCorsa")).sendKeys("09:00:00");
    driver.findElement(By.id("numeroPosti")).clear();
    driver.findElement(By.id("numeroPosti")).sendKeys("50");
    driver.findElement(By.id("passeggeriSaliti")).clear();
    driver.findElement(By.id("passeggeriSaliti")).sendKeys("50");
    driver.findElement(By.id("passeggeriNonSaliti")).clear();
    driver.findElement(By.id("passeggeriNonSaliti")).sendKeys("13");
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Sì");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.name("andata")).click();
    new Select(driver.findElement(By.name("andata"))).selectByVisibleText("Andata");
    driver.findElement(By.name("andata")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/form/div[2]/button/b")).click();
  }

  @Test
  public void testInserimentoDatiCorsaOrarioErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='people']")).click();
    driver.findElement(By.id("lineaCorsa")).click();
    driver.findElement(By.id("lineaCorsa")).clear();
    driver.findElement(By.id("lineaCorsa")).sendKeys("NA08");
    driver.findElement(By.id("orarioCorsa")).clear();
    driver.findElement(By.id("orarioCorsa")).sendKeys("9");
    driver.findElement(By.id("numeroPosti")).clear();
    driver.findElement(By.id("numeroPosti")).sendKeys("50");
    driver.findElement(By.id("passeggeriSaliti")).clear();
    driver.findElement(By.id("passeggeriSaliti")).sendKeys("50");
    driver.findElement(By.id("passeggeriNonSaliti")).clear();
    driver.findElement(By.id("passeggeriNonSaliti")).sendKeys("13");
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Sì");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.name("andata")).click();
    new Select(driver.findElement(By.name("andata"))).selectByVisibleText("Andata");
    driver.findElement(By.name("andata")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/form/div[2]/button/b")).click();
  }

  @Test
  public void testInserimentoDatiCorsaPostiDispErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='people']")).click();
    driver.findElement(By.id("lineaCorsa")).click();
    driver.findElement(By.id("lineaCorsa")).clear();
    driver.findElement(By.id("lineaCorsa")).sendKeys("NA08");
    driver.findElement(By.id("orarioCorsa")).clear();
    driver.findElement(By.id("orarioCorsa")).sendKeys("09:00:00");
    driver.findElement(By.id("numeroPosti")).clear();
    driver.findElement(By.id("numeroPosti")).sendKeys("500");
    driver.findElement(By.id("passeggeriSaliti")).clear();
    driver.findElement(By.id("passeggeriSaliti")).sendKeys("50");
    driver.findElement(By.id("passeggeriNonSaliti")).clear();
    driver.findElement(By.id("passeggeriNonSaliti")).sendKeys("13");
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Sì");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.name("andata")).click();
    new Select(driver.findElement(By.name("andata"))).selectByVisibleText("Andata");
    driver.findElement(By.name("andata")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/form/div[2]/button/b")).click();
  }

  @Test
  public void testInserimentoDatiCorsaSalitiErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='people']")).click();
    driver.findElement(By.id("lineaCorsa")).click();
    driver.findElement(By.id("lineaCorsa")).clear();
    driver.findElement(By.id("lineaCorsa")).sendKeys("NA08");
    driver.findElement(By.id("orarioCorsa")).clear();
    driver.findElement(By.id("orarioCorsa")).sendKeys("09:00:00");
    driver.findElement(By.id("numeroPosti")).clear();
    driver.findElement(By.id("numeroPosti")).sendKeys("50");
    driver.findElement(By.id("passeggeriSaliti")).clear();
    driver.findElement(By.id("passeggeriSaliti")).sendKeys("-");
    driver.findElement(By.id("passeggeriNonSaliti")).clear();
    driver.findElement(By.id("passeggeriNonSaliti")).sendKeys("13");
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Sì");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.name("andata")).click();
    new Select(driver.findElement(By.name("andata"))).selectByVisibleText("Andata");
    driver.findElement(By.name("andata")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/form/div[2]/button/b")).click();
  }

  @Test
  public void testInserimentoDatiCorsaOk() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='people']")).click();
    driver.findElement(By.id("lineaCorsa")).click();
    driver.findElement(By.id("lineaCorsa")).clear();
    driver.findElement(By.id("lineaCorsa")).sendKeys("NA08");
    driver.findElement(By.id("orarioCorsa")).clear();
    driver.findElement(By.id("orarioCorsa")).sendKeys("09:00:00");
    driver.findElement(By.id("numeroPosti")).clear();
    driver.findElement(By.id("numeroPosti")).sendKeys("50");
    driver.findElement(By.id("passeggeriSaliti")).clear();
    driver.findElement(By.id("passeggeriSaliti")).sendKeys("50");
    driver.findElement(By.id("passeggeriNonSaliti")).clear();
    driver.findElement(By.id("passeggeriNonSaliti")).sendKeys("13");
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Sì");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.name("andata")).click();
    new Select(driver.findElement(By.name("andata"))).selectByVisibleText("Andata");
    driver.findElement(By.name("andata")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/form/div[2]/button/b")).click();
  }

  @Test
  public void testGenerazioneProgrammaDataDaErrata() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("//img[@alt='calendar']")).click();
    driver.findElement(By.xpath("//div[@id='about']/div/div/a/h3")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("0002-09-12");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("0020-09-12");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("0202-09-12");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("2021-09-12");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("2021-09-12");
    driver.findElement(By.id("a")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testGenerazioneProgrammaOk() throws Exception {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.linkText("GENERA PROGRAMMA")).click();
    driver.findElement(By.xpath("//div[@id='about']/div/div/a/h3")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("da")).clear();
    driver.findElement(By.id("da")).sendKeys("0002-09-02");
    driver.findElement(By.id("da")).clear();
    driver.findElement(By.id("da")).sendKeys("0020-09-02");
    driver.findElement(By.id("da")).clear();
    driver.findElement(By.id("da")).sendKeys("0202-09-02");
    driver.findElement(By.id("da")).clear();
    driver.findElement(By.id("da")).sendKeys("2021-09-02");
    jsExecutor.executeScript("document.getElementById('da').value = \"2021-09-02\";");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("0002-09-02");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("0020-09-02");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("0202-09-02");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("2022-09-02");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("2022-09-02");
    jsExecutor.executeScript("document.getElementById('da').value = \"2022-09-02\";");
    driver.findElement(By.id("a")).sendKeys(Keys.ENTER);

  }


  @Test
  public void testInserimentoCorsaOrarioErrato() throws Exception {
    driver.get("http://localhost:8080/");
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("GENERA PROGRAMMA")).click();
    driver.findElement(By.xpath("//div[@id='about']/div/div/a/h3")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("da")).clear();
    jsExecutor.executeScript("document.getElementById('da').value = \"2021-06-24\";");
    driver.findElement(By.id("a")).click();
    driver.findElement(By.id("a")).click();
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("2021-06-30");
    jsExecutor.executeScript("document.getElementById('a').value = \"2021-06-24\";");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/button/b")).click();
    driver.findElement(By.id("orario")).click();
    driver.findElement(By.id("orario")).clear();
    driver.findElement(By.id("orario")).sendKeys("9");
    driver.findElement(By.name("linea")).click();
    new Select(driver.findElement(By.name("linea"))).selectByVisibleText("NA08");
    jsExecutor.executeScript("document.getElementsByName(\"conducente\")[0].selectedIndex = 1;");
    jsExecutor.executeScript("document.getElementsByName(\"mezzo\")[0].selectedIndex = 1;");
    driver.findElement(By.name("linea")).click();
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Andata");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.id("orario")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testInserimentoCorsaLineaErrata() throws Exception {
    driver.get("http://localhost:8080/");
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("GENERA PROGRAMMA")).click();
    driver.findElement(By.xpath("//div[@id='about']/div/div/a/h3")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("da")).clear();
    jsExecutor.executeScript("document.getElementById('da').value = \"2021-06-24\";");
    driver.findElement(By.id("a")).click();
    driver.findElement(By.id("a")).click();
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("2021-06-30");
    jsExecutor.executeScript("document.getElementById('a').value = \"2021-06-24\";");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/button/b")).click();
    driver.findElement(By.id("orario")).click();
    driver.findElement(By.id("orario")).clear();
    driver.findElement(By.id("orario")).sendKeys("09:00:00");
    driver.findElement(By.name("linea")).click();
    jsExecutor.executeScript("document.getElementsByName(\"conducente\")[0].selectedIndex = 1;");
    jsExecutor.executeScript("document.getElementsByName(\"mezzo\")[0].selectedIndex = 1;");
    driver.findElement(By.name("linea")).click();
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Andata");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.id("orario")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testInserimentoCorsaMezzoErrato() throws Exception {
    driver.get("http://localhost:8080/");
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("GENERA PROGRAMMA")).click();
    driver.findElement(By.xpath("//div[@id='about']/div/div/a/h3")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("da")).clear();
    jsExecutor.executeScript("document.getElementById('da').value = \"2021-06-24\";");
    driver.findElement(By.id("a")).click();
    driver.findElement(By.id("a")).click();
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("2021-06-30");
    jsExecutor.executeScript("document.getElementById('a').value = \"2021-06-24\";");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/button/b")).click();
    driver.findElement(By.id("orario")).click();
    driver.findElement(By.id("orario")).clear();
    driver.findElement(By.id("orario")).sendKeys("09:00:00");
    driver.findElement(By.name("linea")).click();
    new Select(driver.findElement(By.name("linea"))).selectByVisibleText("NA08");
    jsExecutor.executeScript("document.getElementsByName(\"conducente\")[0].selectedIndex = 1;");
    driver.findElement(By.name("linea")).click();
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Andata");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.id("orario")).sendKeys(Keys.ENTER);
  }

  @Test
  @Order(33)
  public void testInserimentoCorsaOk() throws Exception {
    driver.get("http://localhost:8080/");
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("GENERA PROGRAMMA")).click();
    driver.findElement(By.xpath("//div[@id='about']/div/div/a/h3")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("da")).click();
    driver.findElement(By.id("da")).clear();
    jsExecutor.executeScript("document.getElementById('da').value = \"2021-06-24\";");
    driver.findElement(By.id("a")).click();
    driver.findElement(By.id("a")).click();
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("2021-06-30");
    jsExecutor.executeScript("document.getElementById('a').value = \"2021-06-24\";");
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/button/b")).click();
    driver.findElement(By.id("orario")).click();
    driver.findElement(By.id("orario")).clear();
    driver.findElement(By.id("orario")).sendKeys("09:00:00");
    driver.findElement(By.name("linea")).click();
    new Select(driver.findElement(By.name("linea"))).selectByVisibleText("NA08");
    jsExecutor.executeScript("document.getElementsByName(\"conducente\")[0].selectedIndex = 1;");
    jsExecutor.executeScript("document.getElementsByName(\"mezzo\")[0].selectedIndex = 1;");
    driver.findElement(By.name("linea")).click();
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Andata");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.id("orario")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Termina inserimento")).click();
  }

  /**
   * TearDown.
   */
  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  @SuppressWarnings("unused")
  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  @SuppressWarnings("unused")
  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  @SuppressWarnings("unused")
  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}