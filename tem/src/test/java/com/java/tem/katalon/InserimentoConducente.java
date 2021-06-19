package com.java.tem.katalon;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class InserimentoConducente {
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
  public void testInserimentoConducente() throws Exception {
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
  public void testInserimentoConducenteCognomeFail() throws Exception {
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
  public void testInserimentoConducenteCfFail() throws Exception {
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
