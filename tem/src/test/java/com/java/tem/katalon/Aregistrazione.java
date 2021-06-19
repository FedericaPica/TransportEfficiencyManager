package com.java.tem.katalon;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Aregistrazione {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testARegistrazioneVuota() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("citta")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testARegistrazioneUsernameCorto() throws Exception {
    driver.get("http://localhost:8080/register");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Registrazione azienda'])[1]/following::div[2]")).click();
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
  public void testARegistrazioneNomeAziendaErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Registrazione azienda'])[1]/following::div[2]")).click();
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
  public void testARegistrazionePswErrata() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Registrazione azienda'])[1]/following::div[2]")).click();
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
  public void testARegistrazionePivaErrata() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Registrazione azienda'])[1]/following::div[2]")).click();
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
  public void testARegistrazioneTelErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Registrazione azienda'])[1]/following::div[2]")).click();
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
  public void testARegistrazioneFaxErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Registrazione azienda'])[1]/following::div[2]")).click();
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
  public void testARegistrazioneIndirizzoErrato() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Registrazione azienda'])[1]/following::div[2]")).click();
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
  public void testARegistrazioneOk() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Registra la tua azienda")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Registrazione azienda'])[1]/following::div[2]")).click();
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

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

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




