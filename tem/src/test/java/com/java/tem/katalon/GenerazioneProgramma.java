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

public class GenerazioneProgramma {
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
  public void testGenerazioneProgrammaDataDaFail() throws Exception {
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
    driver.findElement(By.id("a")).sendKeys(Keys.ENTER);
  }

  @Test
  public void testInserimentoCorsaProgrammaLineaFail() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("azienda@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("testtest");
    driver.findElement(By.cssSelector(".form-signin")).submit();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='HOME'])[1]/preceding::p[2]")).click();
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
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("0002-09-02");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("0020-09-02");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("0202-09-02");
    driver.findElement(By.id("a")).clear();
    driver.findElement(By.id("a")).sendKeys("2022-09-02");
    driver.findElement(By.id("a")).sendKeys(Keys.ENTER);
    driver.findElement(By.id("orario")).click();
    driver.findElement(By.id("orario")).clear();
    driver.findElement(By.id("orario")).sendKeys("09:00:00");
    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=conducente | label=Pica]]
    driver.findElement(By.xpath("//option[@value='2']")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=mezzo | label=Minibus]]
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/div[4]/select/option[2]")).click();
    driver.findElement(By.id("traffico-select")).click();
    new Select(driver.findElement(By.id("traffico-select"))).selectByVisibleText("Andata");
    driver.findElement(By.id("traffico-select")).click();
    driver.findElement(By.xpath("//div[@id='main']/div/form/div/div[6]/button/b")).click();
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
