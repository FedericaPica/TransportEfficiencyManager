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

public class InserimentoMezzo {
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
  public void testMezzo() throws Exception {
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
  public void testMezzoTargaCorta() throws Exception {
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
  public void testMezzoTipoCorto() throws Exception {
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
  public void testMezzoOk() throws Exception {
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
