package com.excilys.arnaud.servlets;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DashbordWebTest {
  private WebDriver driver;
  private String baseUrl;

  @BeforeClass
  public static void setUpBeforeClass() {
    System.setProperty("webdriver.chrome.driver", "/home/excilys/chrome/chromedriver");
  }

  @Before
  public void setUp() throws Exception {
    this.driver = new ChromeDriver();
    this.baseUrl = "http://localhost:8080";
    this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
  }

  @After
  public void tearDown() throws Exception {
    this.driver.quit();
  }

  @Test
  public void testChangeNumberPerPage() {
    this.driver.get(this.baseUrl + "/ComputerDatabase/dashboard");
    Assert.assertTrue(
        this.driver.findElement(By.tagName("h1")).getText().contains("Computers found"));


    this.driver.findElement(By.id("a2")).click();
    Assert.assertTrue(this.driver.findElements(By.xpath("//tr")).size() == 51);


    this.driver.findElement(By.id("a3")).click();
    Assert.assertTrue(this.driver.findElements(By.xpath("//tr")).size() == 101);


    this.driver.findElement(By.id("a1")).click();
    Assert.assertTrue(this.driver.findElements(By.xpath("//tr")).size() == 11);
  }
  
  @Test
  public void testChangePage() {
    this.driver.get(this.baseUrl + "/ComputerDatabase/dashboard");
    
    this.driver.findElement(By.id("next")).click();
    Assert.assertTrue(driver.getCurrentUrl().contains("page=2"));
    
    this.driver.findElement(By.id("lastpage")).click();
    Assert.assertTrue(driver.getCurrentUrl().contains("page=100001"));
    
    this.driver.findElement(By.id("prev")).click();
    Assert.assertTrue(driver.getCurrentUrl().contains("page=10000"));
    
    this.driver.findElement(By.id("firstpage")).click();
    Assert.assertTrue(driver.getCurrentUrl().contains("page=1"));
  }
  
  
  @Test
  public void testSearch() {
    this.driver.get(this.baseUrl + "/ComputerDatabase/dashboard");
    
    this.driver.findElement(By.id("searchbox")).sendKeys("apple");
    this.driver.findElement(By.id("searchsubmit")).click();
    Assert.assertTrue(driver.getCurrentUrl().contains("search=apple"));
  }

}


