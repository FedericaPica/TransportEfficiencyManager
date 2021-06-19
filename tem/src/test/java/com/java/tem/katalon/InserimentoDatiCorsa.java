package com.java.tem.katalon;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class InserimentoDatiCorsa {
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
  public void testInserimentoVuoto() throws Exception {
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
  public void testInserimentoLineaFail() throws Exception {
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
  public void testInserimentoOrarioFail() throws Exception {
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
  public void testInserimentoPostiDispFail() throws Exception {
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
  public void testInserimentoSalitiFail() throws Exception {
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
  public void testInserimentoOk() throws Exception {
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
