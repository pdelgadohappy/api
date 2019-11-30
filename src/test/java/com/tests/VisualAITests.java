package com.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

public class VisualAITests {
	private static EyesRunner runner;
	private static Eyes eyes;
	private static BatchInfo batch;
	private static WebDriver driver;
	private String url = "https://demo.applitools.com/hackathonV2.html";
	
	@BeforeClass
	public static void openBrowser() throws IOException {
		batch = new BatchInfo("Login test");
		runner = new ClassicRunner();
		eyes = new Eyes(runner);
		eyes.setApiKey("ppbXmfStMScPHBd2TLNO8gXZ1zHrAIbtEKu6qg105ujK4110");
		eyes.setBatch(batch);
		eyes.setForceFullPageScreenshot(true);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public static void closeBrowser() {
		driver.quit();
	}
	
	@Test
	public void loginElements() {
		driver.get(url);
		eyes.open(driver, "Login", "Login screen");
		eyes.checkWindow("Login screen");
		eyes.closeAsync();
	}
	
	@Test
	public void login() {
		driver.get(url);
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("pwd");
		driver.findElement(By.id("log-in")).click();
		eyes.open(driver, "Login", "Logged correctly");
		eyes.checkWindow("App main");
		eyes.closeAsync();
	}
	
	@Test
	public void loginFailUser() {
		driver.get(url);
		driver.findElement(By.id("password")).sendKeys("pwd");
		driver.findElement(By.id("log-in")).click();
		eyes.open(driver, "Login", "Login screen fail user");
		eyes.checkWindow("Login screen");
		eyes.closeAsync();
	}
	
	@Test
	public void loginFailPwd() {
		driver.get(url);
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("log-in")).click();
		assertEquals(url, driver.getCurrentUrl());
		eyes.open(driver, "Login", "Login screen fail pwd");
		eyes.checkWindow("Login screen");
		eyes.closeAsync();
	}
	
	@Test
	public void loginFailBoth() {
		driver.get(url);
		driver.findElement(By.id("log-in")).click();
		eyes.open(driver, "Login", "Login screen fail both");
		eyes.checkWindow("Login screen");
		eyes.closeAsync();
	}
//
	@Test
	public void sortTable() throws ParseException {
		driver.get(url);
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("pwd");
		driver.findElement(By.id("log-in")).click();
		driver.findElement(By.id("amount")).click();
		eyes.open(driver, "Login", "Sort table");
		eyes.checkWindow("App main");
		eyes.closeAsync();
	}
	
	@Test
	public void testGraph() {
		driver.get(url);
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("pwd");
		driver.findElement(By.id("log-in")).click();
		driver.findElement(By.id("showExpensesChart")).click();
		eyes.open(driver, "Login", "Initial Chart");
		eyes.checkWindow("Chart screen");
		eyes.closeAsync();
		driver.findElement(By.id("addDataset")).click();
		eyes.open(driver, "Login", "Chart with more data");
		eyes.checkWindow("Chart screen");
		eyes.closeAsync();
	}
	
	@Test
	public void checkAds() {
		driver.get(url+"?showAd=true");
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("pwd");
		driver.findElement(By.id("log-in")).click();
		eyes.open(driver, "Login", "Display Ads");
		eyes.checkWindow("App main with Ads");
		eyes.closeAsync();
	}
	
}