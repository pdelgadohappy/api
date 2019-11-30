package com.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DecimalFormat;
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

public class TraditionalTests {
	private static WebDriver driver;
	private String url = "https://demo.applitools.com/hackathonV2.html";
	
	@BeforeClass
	public static void openBrowser() throws IOException {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}
	
	@AfterClass
	public static void closeBrowser() {
		driver.quit();
	}
	
	@Test
	public void login() {
		driver.get(url);
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("pwd");
		driver.findElement(By.id("log-in")).click();
		assertTrue(driver.findElement(By.className("avatar-w")).isDisplayed());
	}
	
	@Test
	public void loginFailUser() {
		driver.get(url);
		driver.findElement(By.id("password")).sendKeys("pwd");
		driver.findElement(By.id("log-in")).click();
		assertEquals("Username must be present", driver.findElement(By.cssSelector(".alert.alert-warning")).getText());
		assertEquals(url, driver.getCurrentUrl());
	}
	
	@Test
	public void loginFailPwd() {
		driver.get(url);
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("log-in")).click();
		try {
		assertEquals("Password must be present", driver.findElement(By.cssSelector(".alert.alert-warning")).getText());
		}catch(NoSuchElementException e) {
			Assert.fail("Password must be present erro message missing");
		}
		assertEquals(url, driver.getCurrentUrl());
	}
	
	@Test
	public void loginFailBoth() {
		driver.get(url);
		driver.findElement(By.id("log-in")).click();
		assertEquals("Both Username and Password must be present", driver.findElement(By.cssSelector(".alert.alert-warning")).getText());
		assertEquals(url, driver.getCurrentUrl());
	}
	
	@Test
	public void loginElements() {
		driver.get(url);
		assertEquals("Enter your username", driver.findElement(By.id("username")).getAttribute("placeholder"));
		assertEquals("Enter your password", driver.findElement(By.id("password")).getAttribute("placeholder"));
		assertEquals("Login Form", driver.findElement(By.cssSelector(".auth-header")).getText());
		try {
		assertTrue(driver.findElement(By.className("os-icon-user-male-circle")).isDisplayed());
		}catch(NoSuchElementException e) {
			Assert.fail("Username icon missing");
		}
		try {
			assertTrue(driver.findElement(By.className("os-icon-fingerprint")).isDisplayed());
		}catch(NoSuchElementException e) {
			Assert.fail("Password icon missing");
		}
		assertEquals("Remember Me", driver.findElement(By.cssSelector(".form-check-label")).getText());
		assertEquals("Log In", driver.findElement(By.id("log-in")).getText());
		assertTrue(driver.findElement(By.xpath("//img [contains(@src, 'facebook')]")).isDisplayed());
		assertTrue(driver.findElement(By.xpath("//img [contains(@src, 'twitter')]")).isDisplayed());
		assertTrue(driver.findElement(By.xpath("//img [contains(@src, 'linkedin')]")).isDisplayed());
	}
	
	@Test
	public void sortTable() throws ParseException {
		driver.get(url);
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("pwd");
		driver.findElement(By.id("log-in")).click();
		ArrayList<Double> amount = new ArrayList<Double>();
		List<WebElement> table = driver.findElements(By.xpath("//span [contains(text(), 'USD')]"));
		for (int i = 0; i < table.size(); i++) {
			amount.add(this.convert(table.get(i).getText()));
		}
		Collections.sort(amount);
		driver.findElement(By.id("amount")).click();
		table = driver.findElements(By.xpath("//span [contains(text(), 'USD')]"));
		for (int i = 0; i < amount.size(); i++) {
			assertEquals(amount.get(i), this.convert(table.get(i).getText()), 0.01);
		}
	}
	
	@Test
	public void testGraph() {
		//can't access the graph's data in the DOM (Canvas)
	}
	
	@Test
	public void checkAds() {
		driver.get(url+"?showAd=true");
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("pwd");
		driver.findElement(By.id("log-in")).click();
		assertTrue(driver.findElement(By.id("flashSale")).isDisplayed());
		assertTrue(driver.findElement(By.id("flashSale2")).isDisplayed());
	}
	
	/**
	 * 
	 * @param originalValue String that needs to be converted
	 * @return Value converted to Double
	 * @throws ParseException
	 */
	private Double convert(String originalValue) throws ParseException {
		return DecimalFormat.getNumberInstance().parse(originalValue.replace(" USD", "")
				.replace("+", "").trim().replace(" ", "").replace(",", "").replace(".", ",")).doubleValue();
		
	}
}